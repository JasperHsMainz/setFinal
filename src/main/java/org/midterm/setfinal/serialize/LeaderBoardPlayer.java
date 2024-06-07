package org.midterm.setfinal.serialize;

import javafx.beans.property.*;

import java.io.Serializable;

public class LeaderBoardPlayer implements Serializable {

    private String name;
    private int score;
    private long time;
    private transient StringProperty timeProperty;

    public LeaderBoardPlayer(String name, int score, long time) {
        this.name = name;
        this.score = score;
        this.time = time;
        this.timeProperty = new SimpleStringProperty(toTimeString());
    }


    //Time
    private String toTimeString(){ //TODO

        String timeString = ((int)(time/60))+":"+((time-time/60)%60);
        System.out.println("LeaderBoardPlayer Time: "+timeString);
        return timeString;
    }
    public StringProperty getTimeProperty() {
        timeProperty = new SimpleStringProperty(toTimeString());
        return timeProperty;
    }


    // getter/setter
    public int getScore() {
        return score;
    }
    public long getTime() {
        return time;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public IntegerProperty scoreProperty() {
        return new SimpleIntegerProperty(score);
    }
    public LongProperty timeProperty() {
        return new SimpleLongProperty(time);
    }
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }
}
