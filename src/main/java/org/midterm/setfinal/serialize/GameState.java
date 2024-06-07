package org.midterm.setfinal.serialize;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.midterm.setfinal.GameLogic.GameLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

public class GameState implements Serializable {
    private List<Card> deck;
    private List<Card> displayedCards;
    private List<Player> playerList;
    private transient IntegerProperty deckSizeProperty;
    private Date highscoreTimer; //Timer for Highscore
    private long highScoreTotalTimeElaplsed;
    private transient IntegerProperty turnTimerProperty;
    private transient boolean turnActive;
    private String gameMode;
    private List<Card> choosenCards = new ArrayList<>();
    private int choosingPlayer = -1;
    private Boolean currentlyChoosing=false; //true solange ein Spieler am wählen ist
    private int bestOfCounter;



    public GameState() {

    }

    //DisplayedCards
    public List<Card> getDisplayedCards() {return displayedCards;}
    public void setDisplayedCards(List<Card> displayedCards) {this.displayedCards = displayedCards;}

    //Deck
    public List<Card> getDeck() {return deck;}
    public void setDeck(List<Card> deck) {
        this.deck = deck;
        initializeDeckSizeProperty();
    }

    //highScoreTotalTimeElaplsed
    public long getHighScoreTotalTimeElaplsed(){
        return highScoreTotalTimeElaplsed;
    }
    public void setHighScoreTotalTimeElaplsed(long totalTimeElaplsed){this.highScoreTotalTimeElaplsed = totalTimeElaplsed;}
    public void calculateHighScoreTotalTimeElaplsed(){
        highScoreTotalTimeElaplsed += ((new Date().getTime()-highscoreTimer.getTime())/1000);
        System.out.println("GameState: totalTimeElaplsed: "+highScoreTotalTimeElaplsed);
        highscoreTimer = new Date();
    }

    //TurnTimer
    public IntegerProperty getTurnTimerProperty(){
        if(turnTimerProperty==null){
            turnTimerProperty = new SimpleIntegerProperty();
        }
            return turnTimerProperty;
    }

    public void setTurnTimerProperty(int turnTimerProperty) {
        if (this.turnTimerProperty == null)
            this.turnTimerProperty = new SimpleIntegerProperty(turnTimerProperty);
        this.turnTimerProperty.set(turnTimerProperty);
    }

    //TurnActive
    public boolean getTurnActive() {
        return turnActive;
    }

    public void setTurnActive(boolean turnActive) {
        this.turnActive = turnActive;
    }

    //PlayerList
    public List<Player> getPlayerList() {return playerList;}
    public void setPlayerList(List<Player> playerList) {this.playerList = playerList;}

    //HighScoreTimer
    public Date getHighscoreTimer() {return highscoreTimer;}
    public void setHighscoreTimer(Date highscoreTimer) {this.highscoreTimer = highscoreTimer;}

    //Gamemode
    public String getGameMode() {return gameMode;}
    public void setGameMode(String gameMode) {this.gameMode = gameMode;}

    //ChoosenCards
    public List<Card> getChoosenCards() {return choosenCards;}
    public void setChoosenCards(List<Card> choosenCards) {this.choosenCards = choosenCards;}

    //ChoosingPlayer
    public int getChoosingPlayer() {return choosingPlayer;}
    public void setChoosingPlayer(int choosingPlayer) {this.choosingPlayer = choosingPlayer;}

    //Currently choosing
    public Boolean getCurrentlyChoosing() {return currentlyChoosing;}
    public void setCurrentlyChoosing(Boolean currentlyChoosing) {this.currentlyChoosing = currentlyChoosing;}

    //BestOfCounter
    public int getBestOfCounter() {return bestOfCounter;}
    public void setBestOfCounter(int bestOfCounter) {this.bestOfCounter = bestOfCounter;}

    //DeckSizeProperty
    public void setDeckSizeProperty(int deckSizeProperty) {
        initializeDeckSizeProperty();
        this.deckSizeProperty.set(deckSizeProperty);
    }

    private void initializeDeckSizeProperty() {
        if (this.deckSizeProperty == null) {
            this.deckSizeProperty = new SimpleIntegerProperty(deck.size());
        }
    }
    public IntegerProperty getDeckSizeProperty() {
        initializeDeckSizeProperty();
        return deckSizeProperty;
    }


}
