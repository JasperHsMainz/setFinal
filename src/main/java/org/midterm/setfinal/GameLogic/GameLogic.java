package org.midterm.setfinal.GameLogic;

import javafx.scene.input.KeyCode;
import org.midterm.setfinal.GameApplication;
import org.midterm.setfinal.enums.Amount;
import org.midterm.setfinal.enums.Colour;
import org.midterm.setfinal.enums.Shading;
import org.midterm.setfinal.enums.Shape;
import org.midterm.setfinal.gamePackage.GameController;
import org.midterm.setfinal.serialize.Card;
import org.midterm.setfinal.serialize.Player;
import org.midterm.setfinal.winning.WinningWindowController;

import java.io.IOException;
import java.util.*;

public class GameLogic {
    //Choosing
    public static void choosingStart(KeyCode playerKey){ //Called when player presses  their Key
        if (!GameApplication.getSerializeObject().getGameState().getCurrentlyChoosing()) {
            List<Player> playerList = GameApplication.getSerializeObject().getGameState().getPlayerList();
            for (int i = 0; i < playerList.size(); i++) {
                if (playerKey == playerList.get(i).getUserKey()) {
                    GameApplication.getSerializeObject().getGameState().setChoosingPlayer(i);
                    GameApplication.getSerializeObject().getGameState().setChoosenCards(new ArrayList<>());
                    GameApplication.getSerializeObject().getGameState().setCurrentlyChoosing(true);
                    System.out.println("New Choosing Player:" + i);
                    GameApplication.getSerializeObject().getGameState().setTurnActive(true);
                    GameApplication.getSerializeObject().getGameState().setTurnTimerProperty(5);
                    TurnTimerThread thread = new TurnTimerThread();     //Creates TimerThread
                    thread.start();                                     //Starts TimerThread
                    GameController.showTimerLabel();                    //Show TimerThreadLabel
                    break;
                }
            }
        }else
            System.out.println("ChoosingStart: already choosing");
    }

    public static void choosingCardChosen(int indexOfCard){ //Wird jedes Mal aufgerufen, wenn eine Karte gedrückt wird, während currentlyChoosing = true ist
        indexOfCard--;
        List<Card> displayedCards = GameApplication.getSerializeObject().getGameState().getDisplayedCards();
        if (indexOfCard < 0 || indexOfCard >= displayedCards.size()) {
            System.out.println("Ungültiger Kartenindex: " + indexOfCard);
            return;
        }

        Card card = displayedCards.get(indexOfCard);
        if (card == null) {
            System.out.println("Karte am Index " + indexOfCard + " ist null");
            return;
        }

        int idxOfDoubledCard = cardAlreadySelected(card);
        if (GameApplication.getSerializeObject().getGameState().getCurrentlyChoosing()) {
            if (idxOfDoubledCard == -1) { // Taste noch nicht ausgewählt
                if (GameApplication.getSerializeObject().getGameState().getChoosenCards().size() < 3) {
                    GameApplication.getSerializeObject().getGameState().getChoosenCards().add(card);
                    System.out.println("Hinzugefügt: " + card.getImgLink());
                    printChosenCards();
                    GameController.highlightButtonX(indexOfCard);
                    if (GameApplication.getSerializeObject().getGameState().getChoosenCards().size() == 3) {
                        // submitSetButton aktivieren und anzeigen
                        GameController.showConfirmSetButton();
                    }
                }
            } else {
                GameApplication.getSerializeObject().getGameState().getChoosenCards().remove(idxOfDoubledCard);
                GameController.dehighlightButtonX(indexOfCard);
                System.out.println("Entfernt: " + displayedCards.get(indexOfCard).getImgLink());
                printChosenCards();
                if (GameApplication.getSerializeObject().getGameState().getChoosenCards().size() < 3) {
                    // submitSetButton deaktivieren und ausblenden
                    GameController.hideConfirmSetButton();
                }
            }
        } else {
            System.out.println("Kein Spieler ausgewählt");
        }
    }

    public static void choosingComplete() throws IOException { //Called once by submitSetButton
        System.out.println("choosingCompleteCalled");
        if (GameApplication.getSerializeObject().getGameState().getCurrentlyChoosing()&&GameApplication.getSerializeObject().getGameState().getChoosenCards().size()==3){
            GameApplication.getSerializeObject().getGameState().setTurnActive(false);
            GameController.hideTimerLabel();
            if (GameLogic.checkIfSet(GameApplication.getSerializeObject().getGameState().getChoosenCards())){
                SoundPlayer.wowSound();
                GameApplication.getSerializeObject().getGameState().setBestOfCounter(GameApplication.getSerializeObject().getGameState().getBestOfCounter()+1);
                System.out.println("ChoosingComplete: bestOfCounter increased to: "+GameApplication.getSerializeObject().getGameState().getBestOfCounter());
                System.out.println("SetCheck: true");
                increasePlayersScore(GameApplication.getSerializeObject().getGameState().getChoosingPlayer());
                removeCards();
                if(GameApplication.getSerializeObject().getGameState().getDeck().size()>=3) {
                    drawTo12();
                    GameController.assignImagesToButtons();
                }else {
                    reshuffleDisplayedCards();
                    GameController.assignImagesToButtons();
                }
            }else {
                decreasePlayerScore(GameApplication.getSerializeObject().getGameState().getChoosingPlayer());
                GameApplication.getSerializeObject().getGameState().setBestOfCounter(GameApplication.getSerializeObject().getGameState().getBestOfCounter()-1);
                System.out.println("ChoosingComplete: bestOfCounter reduced to: "+GameApplication.getSerializeObject().getGameState().getBestOfCounter());
                System.out.println("SetCheck: false");
            }
            GameController.hideConfirmSetButton();
            GameController.dehighlightAll();
            GameApplication.getSerializeObject().getGameState().setCurrentlyChoosing(false);
            GameApplication.getSerializeObject().getGameState().setChoosingPlayer(-1);
            testForEnd();
            //Test if the gameMode end scenario is reached
        }else{
            System.out.println("ChoosingComplete Error: "+GameApplication.getSerializeObject().getGameState().getChoosenCards());
        }

    }

    public static boolean cheat(){
        //find and mark a set on the board
        if (!GameApplication.getSerializeObject().getGameState().getCurrentlyChoosing())
            return false;
        System.out.println("CHEATER");
        boolean setPossible = false;
        List<Card> remainingCards = new ArrayList<>();
        for (int i = 0; i < GameApplication.getSerializeObject().getGameState().getDisplayedCards().size(); i++) { //get all displayed Cards
            remainingCards.add(GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i));
        }


        outerLoop:for (int idxA = 0; idxA < remainingCards.size()-2; idxA++) {//starts at o
            for (int idxB = idxA+1; idxB < remainingCards.size()-1; idxB++) { //starts at idxA+1 so 1
                for (int idxC = idxB+1; idxC < remainingCards.size(); idxC++) { //starts at idxB+1 so idxA+2 so 2
                    List<Card> checkSetList = new ArrayList<>();//create tempList
                    checkSetList.add(remainingCards.get(idxA));//add cards to tempList
                    checkSetList.add(remainingCards.get(idxB));
                    checkSetList.add(remainingCards.get(idxC));
                    if (!(checkSetList.get(0)==null || checkSetList.get(1)==null || checkSetList.get(2)==null)) {
                        if (GameLogic.checkIfSet(checkSetList)) { //check TempList for set
                            setPossible = true;
                            System.out.println("CHEAT: setFound");
                            System.out.println("Set " + idxA + "|" + idxB + "|" + idxC);
                            System.out.println("A:" + remainingCards.get(idxA).getImgLink() + " B:" + remainingCards.get(idxB).getImgLink() + " C:" + remainingCards.get(idxC).getImgLink());
                            choosingCardChosen(idxA + 1); //TODO +1 neccecary?
                            choosingCardChosen(idxB + 1);
                            choosingCardChosen(idxC + 1);
                            break outerLoop; //break out once one has been found
                        }
                    }
                }
            }
        }
        return setPossible;
    }

    //GameEnd Methodes
    public static void testForEnd() throws IOException { // Methode wird nach einem gefundenen Set aufgerufen
        switch (GameApplication.getSerializeObject().getGameState().getGameMode()) {
            case "classic":
                if (endClassic()) {
                    System.out.println("Classic End Reached");
                    GameApplication.getSerializeObject().getGameState().calculateHighScoreTotalTimeElaplsed();
                    GameController.openWinnerWindowForClassicMode();
                }
                break;
            case "bestOf5":
                if (endBestOf5()) {
                    System.out.println("BestOf5 End Reached");
                    GameApplication.getSerializeObject().getGameState().calculateHighScoreTotalTimeElaplsed();
                    GameController.openWinnerWindow();
                }
                break;
            case "bestOf9":
                if (endBestOf9()) {
                    System.out.println("BestOf9 End Reached");
                    GameApplication.getSerializeObject().getGameState().calculateHighScoreTotalTimeElaplsed();
                    GameController.openWinnerWindow();
                }
                break;
            case "oneDeck":
                if (endOneDeck()) {
                    System.out.println("oneDeck End Reached");
                    GameApplication.getSerializeObject().getGameState().calculateHighScoreTotalTimeElaplsed();
                    GameController.openWinnerWindow();
                }
                break;
        }
    }

    public static boolean endClassic() throws IOException {
        //find if Classic end was reached //TODO
        if (GameApplication.getSerializeObject().getGameState().getDeck().size()<8) {
            if (setPossible() != true)
            //askPlayer
            return true;
        }
        return false;
    }

    public static boolean endBestOf5(){
        //if (GameApplication.getSerializeObject().getGameState().getBestOfCounter() < 5) {GameApplication.getSerializeObject().getGameState().setBestOfCounter(GameApplication.getSerializeObject().getGameState().getBestOfCounter()+1);}
        return GameApplication.getSerializeObject().getGameState().getBestOfCounter() == 5;
    }

    public static boolean endBestOf9(){
        //if (GameApplication.getSerializeObject().getGameState().getBestOfCounter() < 9) {GameApplication.getSerializeObject().getGameState().setBestOfCounter(GameApplication.getSerializeObject().getGameState().getBestOfCounter()+1);}
        return GameApplication.getSerializeObject().getGameState().getBestOfCounter() == 9;
    }



    public static boolean endOneDeck(){
        if (GameApplication.getSerializeObject().getGameState().getDeck().size()<8) {
            if (setPossible() != true)
                return true;
        }
        return false;
    }

    public static boolean setPossible(){
        //test if a set can still be found
        System.out.println("setPossibleCalled");
        Boolean setPossible = false;
        List<Card> remainingCards = new ArrayList<>();
        for (int i = 0; i < GameApplication.getSerializeObject().getGameState().getDisplayedCards().size(); i++) { //get all displayed Cards
            if (GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i)!=null)
                remainingCards.add(GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i));
        }
        for (int i = 0; i < GameApplication.getSerializeObject().getGameState().getDeck().size(); i++) { //get all deck Cards
            Card card = GameApplication.getSerializeObject().getGameState().getDeck().get(i);
            remainingCards.add(card);
        }

        outerLoop:for (int idxA = 0; idxA < remainingCards.size()-2; idxA++) {//starts at o

            for (int idxB = idxA+1; idxB < remainingCards.size()-1; idxB++) {
                for (int idxC = idxB+1; idxC < remainingCards.size(); idxC++) {
                    List<Card> checkSetList = new ArrayList<>();//create tempList
                    checkSetList.add(remainingCards.get(idxA));//add cards to tempList
                    checkSetList.add(remainingCards.get(idxB));
                    checkSetList.add(remainingCards.get(idxC));
                    if (GameLogic.checkIfSet(checkSetList)){ //check TempList for set
                        setPossible = true; //break out once one has been found
                        System.out.println("setPossible: setFound");
                        System.out.println("Set "+idxA+"|"+idxB+"|"+idxC);
                        System.out.println("A:"+remainingCards.get(idxA).getImgLink()+" B:"+remainingCards.get(idxB).getImgLink()+" C:"+remainingCards.get(idxC).getImgLink());
                        break outerLoop;
                    }
                }
            }
        }
        return setPossible;
    }

    public static void reshuffelDeck() {
        displayedCardsToNull();
        GameApplication.getSerializeObject().getGameState().setDeck(new ArrayList<>());
        GameApplication.getSerializeObject().getGameState().setDeck(generateDeck());
        drawTo12();

        GameApplication.getSerializeObject().prepForLoad();
    }

    public static Player getWinningPlayer(){
        List<Player> playerList = GameApplication.getSerializeObject().getGameState().getPlayerList();
        int maxScore = 0;
        int ixdOfWinner = 0;
        //TODO Zeit Einbauen?

        for (int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).getScore() > maxScore){
                maxScore = playerList.get(i).getScore();
                ixdOfWinner = i;
            }
        }
        return playerList.get(ixdOfWinner);
    }



    //Card Methodes
    public static void drawTo12(){
        System.out.println("drawTo12 called");
        if (GameApplication.getSerializeObject().getGameState().getDisplayedCards().isEmpty()) {
            displayedCardsToNull();
        }
        else
            reshuffleDisplayedCards();
        for (int i = 0; i < 12; i++) {
            if (GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i)==null){
                GameApplication.getSerializeObject().getGameState().getDisplayedCards().set(i,GameApplication.getSerializeObject().getGameState().getDeck().getFirst());
                GameApplication.getSerializeObject().getGameState().getDeck().removeFirst();
                GameApplication.getSerializeObject().getGameState().setDeckSizeProperty(GameApplication.getSerializeObject().getGameState().getDeck().size());
            }
        }
        GameController.assignImagesToButtons();
        //printDisplayedCards();
    }
    public static void draw3Extra(){//Called by drawButton
        System.out.println("draw3Extra called");
        int counter = 0;
        for (int i = 0; i < 18; i++) {
            if (GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i)==null){
                GameApplication.getSerializeObject().getGameState().getDisplayedCards().set(i,GameApplication.getSerializeObject().getGameState().getDeck().getFirst());
                GameApplication.getSerializeObject().getGameState().getDeck().removeFirst();
                GameApplication.getSerializeObject().getGameState().getDeckSizeProperty().set(GameApplication.getSerializeObject().getGameState().getDeck().size());
                counter++;
            }
            if (counter == 3)
                break;
        }
        GameController.assignImagesToButtons();//refreshes Screen
        //printDisplayedCards();
    }

    public static void reshuffleDisplayedCards(){//Called each Time drawTo12() is called
        for (int i = 0; i < 6; i++) {
            if (GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(17-i)!=null){
                for (int j = 0; j < 15; j++) {
                    if (GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(j)==null){
                        GameApplication.getSerializeObject().getGameState().getDisplayedCards().set(j,GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(17-i));
                        GameApplication.getSerializeObject().getGameState().getDisplayedCards().set(17-i,null);
                        System.out.println("Shuffled card "+(17-i)+" to spot "+j);
                        break;
                    }
                }
            }
        }
        printDisplayedCards();
    }

    public static void removeCards(){
        System.out.println("removeCards called");
        int cCSize = GameApplication.getSerializeObject().getGameState().getChoosenCards().size();
        outerLoop:for (int j=0;j<cCSize;j++) {
            innerLoop:for(int i = 0; i < GameApplication.getSerializeObject().getGameState().getDisplayedCards().size(); i++){
                //System.out.println("choosenNR:"+j+", choosenSize:"+choosenCards.size()+", choosenCartAtIdx:"+choosenCards.getFirst().getImgLink());
                if (GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i)!=null){
                    if (GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i).equals(GameApplication.getSerializeObject().getGameState().getChoosenCards().getFirst())){
                        GameApplication.getSerializeObject().getGameState().getDisplayedCards().set(i,null);
                        GameApplication.getSerializeObject().getGameState().getChoosenCards().removeFirst();
                        break innerLoop;
                    }
                }
            }
        }
        //printChosenCards();
        printDisplayedCards();
    }
    public static int cardAlreadySelected(Card card){
        for (int i = 0; i < GameApplication.getSerializeObject().getGameState().getChoosenCards().size(); i++) {
            if (GameApplication.getSerializeObject().getGameState().getChoosenCards().get(i).equals(card))
                return i;
        }
        return -1;
    }
    public static void displayedCardsToNull() {
        GameApplication.getSerializeObject().getGameState().setDisplayedCards(new ArrayList<>());
        for (int i = 0; i < 18; i++) {
            GameApplication.getSerializeObject().getGameState().getDisplayedCards().add(null);
        }
        System.out.println("displayedCardsToNull");
    }


    //Player Score
    public static void increasePlayersScore(int player){
        GameApplication.getSerializeObject().getGameState().getPlayerList().get(player).setScore(GameApplication.getSerializeObject().getGameState().getPlayerList().get(player).getScore()+1);
        printPlayerScores();//test
    }
    public static void decreasePlayerScore(int player){
        GameApplication.getSerializeObject().getGameState().getPlayerList().get(player).setScore(GameApplication.getSerializeObject().getGameState().getPlayerList().get(player).getScore()-1);
        printPlayerScores();//test
    }


    //StartUp Methodes
    public static void createXPlayers(int amountOfPlayers){
        GameApplication.getSerializeObject().getGameState().setPlayerList(new ArrayList<>());
        List<Player> tempPlayerList = new ArrayList<>();
        switch (amountOfPlayers) {
            case 4:
                tempPlayerList.add(new Player(0, KeyCode.getKeyCode("M")));
                //GameApplication.getSerializeObject().getGameState().getPlayerList().add(new Player(0, KeyCode.getKeyCode("M"))); //default Key for player 4
            case 3:
                tempPlayerList.add(new Player(0, KeyCode.getKeyCode("Y")));
                //GameApplication.getSerializeObject().getGameState().getPlayerList().add(new Player(0, KeyCode.getKeyCode("Y"))); //default Key for player 3
            case 2:
                tempPlayerList.add(new Player(0, KeyCode.getKeyCode("P")));
                //GameApplication.getSerializeObject().getGameState().getPlayerList().add(new Player(0, KeyCode.getKeyCode("P"))); //default Key for player 2
            case 1:
                tempPlayerList.add(new Player(0, KeyCode.getKeyCode("Q")));
                //GameApplication.getSerializeObject().getGameState().getPlayerList().add(new Player(0, KeyCode.getKeyCode("Q"))); //default Key for player 1
                break;
        }
        Collections.reverse(tempPlayerList);
        GameApplication.getSerializeObject().getGameState().setPlayerList(tempPlayerList);
    }



    //return methods
    public static List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>();
        for (Amount amount : Amount.values()) {
            for (Colour colour : Colour.values()) {
                for (Shading shading : Shading.values()) {
                    for (Shape shape : Shape.values()) {
                        deck.add(new Card(amount, colour, shape, shading));
                    }
                }
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    public static boolean checkIfSet(List<Card> cardList){

        HashSet<Shape> shapeSet = new HashSet<>();
        HashSet<Amount> amountSet = new HashSet<>();
        HashSet<Shading> shadingSet = new HashSet<>();
        HashSet<Colour> colourSet = new HashSet<>();
        Card c;

        for (int i=0; i<3; i++){
            c = cardList.get(i);
            shapeSet.add(c.getShape());
            amountSet.add(c.getAmount());
            shadingSet.add(c.getShading());
            colourSet.add(c.getColour());
        }
        return shapeSet.size() != 2 && amountSet.size() != 2 && shadingSet.size() != 2 && colourSet.size() != 2;
    }


    //Print Methodes
    public static void printPlayerScores(){
        System.out.println("PlayerScores:");
        for (int i = 0; i < GameApplication.getSerializeObject().getGameState().getPlayerList().size(); i++) {
            System.out.println("Player "+i+": "+GameApplication.getSerializeObject().getGameState().getPlayerList().get(i).getScore());
        }
    }

    public static void printChosenCards(){
        System.out.println("Currently Chosen Cards:");
        for (int i = 0; i < GameApplication.getSerializeObject().getGameState().getChoosenCards().size(); i++) {
            System.out.println((i+1)+".: "+GameApplication.getSerializeObject().getGameState().getChoosenCards().get(i).getImgLink());
        }
    }

    public static void printDisplayedCards(){

        System.out.println("Currently Displayed Cards:");
        for (int i = 0; i < GameApplication.getSerializeObject().getGameState().getDisplayedCards().size(); i++) {
            if (!(GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i)==null))
                System.out.println((i+1)+".: "+GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i).getImgLink());
            else
                System.out.println((i+1)+".: "+"NULL");
        }
    }
}
