package org.midterm.setfinal.serialize;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

public class Player implements java.io.Serializable{
    private int score; //Players current score
    private KeyCode userKey;//Players selected Key
    private transient IntegerProperty scoreProperty;
    private transient StringProperty playerNameProperty;
    private long highScoreTime;



    public Player(int score, KeyCode userKey) {
        this.score = score;
        this.userKey = userKey;
        scoreProperty = new SimpleIntegerProperty(score);
        playerNameProperty = new SimpleStringProperty();
    }

    //Setters
    public void setScore(int score) {
        this.score = score;
        scoreProperty.set(score);
    }

    public void setPlayerNameProperty(String playerNameProperty) {
        this.playerNameProperty = new SimpleStringProperty(playerNameProperty);
    }

    public void setUserKey(KeyCode userKey) {
        this.userKey = userKey;
    }
    public void setHighScoreTime(long highScoreTime) {
        this.highScoreTime = highScoreTime;
    }

    //Getters
    public int getScore() {
        return score;
    }
    public long getTime(){ return highScoreTime; }
    public String getPlayerNameProperty() {

        return playerNameProperty.get();
    }

    public IntegerProperty getScoreProperty() {
        scoreProperty = new SimpleIntegerProperty(score);
        return scoreProperty;
    }

    public KeyCode getUserKey() {
        return userKey;
    }
}
