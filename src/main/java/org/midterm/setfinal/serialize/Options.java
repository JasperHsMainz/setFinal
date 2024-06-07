package org.midterm.setfinal.serialize;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Options implements Serializable {
    private Boolean listenToBackroundMusic = true;
    private Boolean listenToButtonSounds = true;
    private Integer currentSongIndex = 1;
    private String currentSongTitle = "Vivaldi Winter";
    private double volume = 50;
    private transient StringProperty songTitle = new SimpleStringProperty(currentSongTitle);

    public Options() {}


    // getter/setter

    public Boolean getListenToButtonSounds() {
        return listenToButtonSounds;
    }

    public Boolean getListenToBackroundMusic() {
        return listenToBackroundMusic;
    }

    public double getVolume() {
        return volume;
    }

    public String getCurrentSongTitle() {
        return currentSongTitle;
    }
    public StringProperty currentSongProperty(){
        if (songTitle == null) {
            songTitle = new SimpleStringProperty(currentSongTitle);
        }
        return songTitle;
    }
    public Integer getCurrentSongIndex() {
        return currentSongIndex;
    }

    public void setListenToButtonSounds(Boolean listenToButtonSounds) {
        this.listenToButtonSounds = listenToButtonSounds;
    }

    public void setListenToBackroundMusic(Boolean listenToBackroundMusic) {
        this.listenToBackroundMusic = listenToBackroundMusic;
    }

    public void setCurrentSongIndex(Integer currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }

    public void setCurrentSongTitle(String currentSongTitle) {
        this.currentSongTitle = currentSongTitle;
        if (songTitle != null) {
            songTitle.set(currentSongTitle);
        }
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
