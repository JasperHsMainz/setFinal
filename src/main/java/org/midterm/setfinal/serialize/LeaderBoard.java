package org.midterm.setfinal.serialize;

import org.midterm.setfinal.GameApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoard implements Serializable {
    List<LeaderBoardPlayer> classicPlayers = new ArrayList<>();
    List<LeaderBoardPlayer> oneDeckPlayers = new ArrayList<>();
    List<LeaderBoardPlayer> bestOf5Players = new ArrayList<>();
    List<LeaderBoardPlayer> bestOf9Players = new ArrayList<>();



    public LeaderBoard() {
        classicPlayers = fillWithExamplePlayer();
        oneDeckPlayers = fillWithExamplePlayer();
        bestOf5Players = fillWithExamplePlayer();
        bestOf9Players = fillWithExamplePlayer();
    }

    private List<LeaderBoardPlayer> fillWithExamplePlayer() {
        List<LeaderBoardPlayer> examplePlayers = new ArrayList<>();
        examplePlayers.add(new LeaderBoardPlayer("Player1", 0, 0));
        examplePlayers.add(new LeaderBoardPlayer("Player2", 0, 0));
        examplePlayers.add(new LeaderBoardPlayer("Player3", 0, 0));
        return examplePlayers;
    }

    //Add to leaderBoard methodes
    public  void addToLeaderBoard(Player player,String gameMode) {
        switch (gameMode) {
            case "classic":
                System.out.println("Adding to Classic");
                addToCorrectPosition(player,classicPlayers);
                break;
            case "oneDeck":
                System.out.println("Adding to oneDeck");
                addToCorrectPosition(player,oneDeckPlayers);
                break;
            case "bestOf5":
                System.out.println("Adding to bestOf5");
                addToCorrectPosition(player,bestOf5Players);
                break;
            case "bestOf9":
                System.out.println("Adding to bestOf9");
                addToCorrectPosition(player,bestOf9Players);
                break;
            default:
                System.out.println("Invalid gameMode in addToLeaderBoard");
                break;
        }
        DAOSerialize.writeSerialized(GameApplication.getSerializeObject());
    }

    private void addToCorrectPosition(Player newPlayer,List<LeaderBoardPlayer> playersList) {
        LeaderBoardPlayer player = toLeaderBoardPlayer(newPlayer);
        int leaderBoardSize = playersList.size();
        if (playersList.size()==0){
            System.out.println("FirstPlayer");
            playersList.add(player);
            return;
        }
        int i ;
        for (i = 0; i < playersList.size()&&i<leaderBoardSize; i++) {
            if (playersList.get(i).getScore()<player.getScore()) {
                if (i+1<leaderBoardSize) {
                    System.out.println("Player added to spot "+i);
                    playersList.add(i, player);
                    if (playersList.size()>leaderBoardSize)
                        playersList.removeLast();
                    return;
                }
                System.out.println("Player didn't make it to the Board D:");
                return;
            } else if (playersList.get(i).getScore()==player.getScore()) {
                if (playersList.get(i).getTime()>player.getTime()) {
                    if (i+1<leaderBoardSize) {
                        System.out.println("Player added to spot "+i);
                        playersList.add(i, player);
                        if (playersList.size()>leaderBoardSize)
                            playersList.removeLast();
                        return;
                    }
                    System.out.println("Player didn't make it to the Board D:");
                    return;
                }
            }
        }
        if (i<leaderBoardSize) {
            System.out.println("Player added to spot "+i);
            playersList.add(player);
        }
    }

    //Converter
    private LeaderBoardPlayer toLeaderBoardPlayer(Player player) {
        return new LeaderBoardPlayer(player.getPlayerNameProperty().toString(),player.getScore(),GameApplication.getSerializeObject().getGameState().getHighScoreTotalTimeElaplsed());}

    public List<LeaderBoardPlayer> getBestOf5Players() {
        return bestOf5Players;
    }

    public List<LeaderBoardPlayer> getBestOf9Players() {
        return bestOf9Players;
    }

    public List<LeaderBoardPlayer> getClassicPlayers() {
        return classicPlayers;
    }

    public List<LeaderBoardPlayer> getOneDeckPlayers() {
        return oneDeckPlayers;
    }
}
