package org.midterm.setfinal.GameLogic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.midterm.setfinal.GameApplication;


import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SoundPlayer {

    private static MediaPlayer musicPlayer; // MediaPlayer für Hintergrundmusik
    private static MediaPlayer soundEffectPlayer; // MediaPlayer für Soundeffekte
    private static File soundDirectory; // Verzeichnis für Hintergrundmusik
    private static File soundEffectDirectory; // Neues Verzeichnis für Soundeffekte
    private static final List<File> songs = new ArrayList<>(); // Liste der Songs

    static {
        try {
            // Initialisiere das Verzeichnis für Hintergrundmusik
            soundDirectory = new File(SoundPlayer.class.getResource("/sounds/").toURI());
            // Initialisiere das Verzeichnis für Soundeffekte
            soundEffectDirectory = new File(SoundPlayer.class.getResource("/soundEffekte/").toURI());
            loadSongs(); // Lädt alle verfügbaren Songs
        } catch (URISyntaxException e) {
            e.printStackTrace();
            soundDirectory = null;
            soundEffectDirectory = null;
        }
    }

    private SoundPlayer() {
    }

    private static void loadSongs() throws URISyntaxException {
        File[] files = soundDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                songs.add(file);
            }
        }
    }

    public static void playMusic(){
        if (GameApplication.getSerializeObject().getOptions().getListenToBackroundMusic()){
                if (musicPlayer != null) {
                    musicPlayer.stop();
                }
                Media media = new Media(songs.get(GameApplication.getSerializeObject().getOptions().getCurrentSongIndex()).toURI().toString());
                musicPlayer = new MediaPlayer(media);
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                musicPlayer.play();
        }
    }

    public static void chooseBackroundMusic(int songIndex) {

        GameApplication.getSerializeObject().getOptions().setCurrentSongIndex(songIndex);

    if (GameApplication.getSerializeObject().getOptions().getListenToBackroundMusic()){
        if (songIndex >= 0 && songIndex < songs.size()) {
            if (musicPlayer != null) {
                musicPlayer.stop();
            }
            Media media = new Media(songs.get(GameApplication.getSerializeObject().getOptions().getCurrentSongIndex()).toURI().toString());
            musicPlayer = new MediaPlayer(media);
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.play();
        }
    }

    }

    public static void playSoundEffect(String soundFileName) {

            File soundFile = new File(soundEffectDirectory, soundFileName);
            if (soundEffectPlayer != null) {
                soundEffectPlayer.stop();
            }
            Media media = new Media(soundFile.toURI().toString());
            soundEffectPlayer = new MediaPlayer(media);
            soundEffectPlayer.play();
    }

    public static void buttonClickSound() {
        if (GameApplication.getSerializeObject().getOptions().getListenToButtonSounds()) {
            playSoundEffect("button_click_sound.mp3");
        }
    }

    public static void wowSound() {
        if (GameApplication.getSerializeObject().getOptions().getListenToButtonSounds()) {
            playSoundEffect("wow.mp3");
        }
    }
    public static void stopMusic() {
        musicPlayer.stop();
        GameApplication.getSerializeObject().getOptions().setListenToBackroundMusic(false);
    }

    public static void stopSoundEffect() {
        soundEffectPlayer.stop();
        GameApplication.getSerializeObject().getOptions().setListenToButtonSounds(false);
    }

    // getter/setter


    public static MediaPlayer getMusicPlayer() {
        return musicPlayer;
    }
}
