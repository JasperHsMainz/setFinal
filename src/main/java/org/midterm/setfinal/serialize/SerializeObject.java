package org.midterm.setfinal.serialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.midterm.setfinal.GameLogic.GameLogic;

public class SerializeObject implements Serializable {
    private GameState gameState;
    private Options options;
    private LeaderBoard leaderBoard;


    public SerializeObject() {
        options = new Options();
        leaderBoard = new LeaderBoard();
        gameState = new GameState();
        System.out.println("SerializeObject: Initialized");
    }

    public void clearChoosingVariables(){
        gameState.setChoosingPlayer(-1);
        gameState.setChoosenCards(new ArrayList<>());
        gameState.setCurrentlyChoosing(false);
        gameState.setTurnTimerProperty(0);
        gameState.setTurnActive(false);
    }

    //Generate
    public void generateAll(){ //Wird nach SerializedObject() in GameApplication.fetch() aufgerufen um default werte einzusetzen
        generateGameState();
        //options.generateOptions();
        //leaderBoard.generateLeaderBoard();
        System.out.println("SerializeObject: Generated");
    }

    //Generate
    public void generateGameState(int playerCount, String gameMode){
        setGameState(new GameState());
        //Initialize Deck
        gameState.setDeck(GameLogic.generateDeck());
        gameState.setDeckSizeProperty(gameState.getDeck().size());

        //Initialize Lists
        gameState.setDisplayedCards(new ArrayList<>());
        gameState.setPlayerList(new ArrayList<>());
        gameState.setChoosenCards(new ArrayList<>());

        //Fill lists with defaults
        GameLogic.drawTo12();
        GameLogic.createXPlayers(playerCount);

        //Fill rest with Defaults
        gameState.setHighscoreTimer(new Date());
        gameState.setGameMode(gameMode);
        gameState.setCurrentlyChoosing(false);
        gameState.setChoosingPlayer(-1);
        gameState.setBestOfCounter(0);
        gameState.setHighScoreTotalTimeElaplsed(0);
        gameState.setTurnTimerProperty(0);
        gameState.setTurnActive(false);

        System.out.println("GameState: Generated");
    }
    public void generateGameState() {
        generateGameState(4,"bestOf5");
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    //Load
    public void prepForLoad() {
        gameState.setHighscoreTimer(new Date());
        clearChoosingVariables();
        gameState.setTurnTimerProperty(0);
        gameState.setTurnActive(false);
    }

    //Getters
    public GameState getGameState() {
        return gameState;
    }

    public Options getOptions() {
        return options;
    }

    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }




}
