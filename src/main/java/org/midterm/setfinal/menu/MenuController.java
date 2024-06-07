package org.midterm.setfinal.menu;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.midterm.setfinal.GameApplication;
import org.midterm.setfinal.GameLogic.GameLogic;
import org.midterm.setfinal.GameLogic.SoundPlayer;
import org.midterm.setfinal.gamePackage.GameController;
import org.midterm.setfinal.serialize.DAOSerialize;
import org.midterm.setfinal.serialize.GameState;

import java.io.IOException;


public class MenuController {

    @FXML
    private Pane imagePaneRightSite,setLogoPaneLeftSite;
    @FXML
    private AnchorPane groundPane,leaderBoardPane,newGamePane,settingsPane;
    @FXML
    private ScrollPane rulesGroundPane;
    @FXML
    private Button newGameButton,loadGameButton,leaderBoardButton,quitButton,settingsButton,rulesButton,newGameSubmitButton;
    @FXML
    private Label classicNameFirst,classicNameSecond,classicNameThird,OneDNameFirst,OneDNameSecond,OneDNameThird,bestOf5ScoreFirst,bestOf5ScoreSecond,bestOf5ScoreThird,
            bestOf9TimeFirst,bestOf9TimeSecond,bestOf9TimeThird,classicScoreFirst,classicTimeFirst,
            classicScoreSecond,classicScoreThird,OneDScoreFirst,oneDeckTimeFirst,Besto5NameFirst,OneDScoreSecond,
            oneDeckTimeSecond,Besto5NameSecond,OneDScoreThird,oneDeckTimeThird,Besto5NameThird,bestOf5TimeFirst,bestOf9NameFirst,
            bestOf9ScoreFirst,bestOf5TimeSecond,bestOf9NameSecond,bestOf9ScoreSecond,bestOf5TimeThird,bestOf9NameThird,bestOf9ScoreThird,
            amountOfPLayerLabel,gameModeLabel,infoLabel,currentSongLabel,classicTimeSecond,classicTimeThird;
    @FXML
    private RadioButton playerAmount1,playerAmount2,playerAmount3,playerAmount4,classicModeRButton,oneDeckRButton,bestOf5Button,bestOf9Button,
            vivaldiWinterButton,BachSonataButton,vivaldiSummerButton,vivaldiSpringButton;
    @FXML
    private CheckBox musicCheckBox,soundCheckBox;
    @FXML
    private Slider songSlider;

    
    private static Boolean playerAmountIsSelected = false;
    private static Boolean gameModeIsSelected = false;


    public static Boolean getGameModeIsSelected() {
        return gameModeIsSelected;
    }

    public static Boolean getPlayerAmountIsSelected() {
        return playerAmountIsSelected;
    }

    @FXML
    public void initialize() {
        bindOptions();
        bindClassicLeaderBoard();
        bindOneDeckLeaderBoard();
        bindBestOf5leaderBoard();
        bindBestOf9LeaderBoard();
        bindSongSlider();
    }

    private void bindSongSlider(){

        songSlider.setValue(GameApplication.getSerializeObject().getOptions().getVolume());
        songSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                SoundPlayer.getMusicPlayer().setVolume(songSlider.getValue() * 0.01);
                GameApplication.getSerializeObject().getOptions().setVolume(songSlider.getValue());
            }
        });
    }



    private void bindBestOf9LeaderBoard(){
        bestOf9NameFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf9Players().get(0).nameProperty());
        bestOf9NameSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf9Players().get(1).nameProperty());
        bestOf9NameThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf9Players().get(2).nameProperty());
        bestOf9ScoreFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf9Players().get(0).scoreProperty().asString());
        bestOf9ScoreSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf9Players().get(1).scoreProperty().asString());
        bestOf9ScoreThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf9Players().get(2).scoreProperty().asString());
        bestOf9TimeFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf9Players().get(0).getTimeProperty());
        bestOf9TimeSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf9Players().get(1).getTimeProperty());
        bestOf9TimeThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf9Players().get(2).getTimeProperty());
    }

    private void bindBestOf5leaderBoard(){
        Besto5NameFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf5Players().get(0).nameProperty());
        Besto5NameSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf5Players().get(1).nameProperty());
        Besto5NameThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf5Players().get(2).nameProperty());
        bestOf5ScoreFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf5Players().get(0).scoreProperty().asString());
        bestOf5ScoreSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf5Players().get(1).scoreProperty().asString());
        bestOf5ScoreThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf5Players().get(2).scoreProperty().asString());
        bestOf5TimeFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf5Players().get(0).getTimeProperty());
        bestOf5TimeSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf5Players().get(1).getTimeProperty());
        bestOf5TimeThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getBestOf5Players().get(2).getTimeProperty());
    }

    private void bindOneDeckLeaderBoard(){
        OneDNameFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getOneDeckPlayers().get(0).nameProperty());
        OneDNameSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getOneDeckPlayers().get(1).nameProperty());
        OneDNameThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getOneDeckPlayers().get(2).nameProperty());
        OneDScoreFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getOneDeckPlayers().get(0).scoreProperty().asString());
        OneDScoreSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getOneDeckPlayers().get(1).scoreProperty().asString());
        OneDScoreThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getOneDeckPlayers().get(2).scoreProperty().asString());
        oneDeckTimeFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getOneDeckPlayers().get(0).getTimeProperty());
        oneDeckTimeSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getOneDeckPlayers().get(1).getTimeProperty());
        oneDeckTimeThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getOneDeckPlayers().get(2).getTimeProperty());
    }


    private void bindClassicLeaderBoard(){

        classicNameFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getClassicPlayers().get(0).nameProperty());
        classicNameSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getClassicPlayers().get(1).nameProperty());
        classicNameThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getClassicPlayers().get(2).nameProperty());

        classicScoreFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getClassicPlayers().get(0).scoreProperty().asString());
        classicScoreSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getClassicPlayers().get(1).scoreProperty().asString());
        classicScoreThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getClassicPlayers().get(2).scoreProperty().asString());

        classicTimeFirst.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getClassicPlayers().get(0).getTimeProperty());
        classicTimeSecond.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getClassicPlayers().get(1).getTimeProperty());
        classicTimeThird.textProperty().bind(GameApplication.getSerializeObject().getLeaderBoard().getClassicPlayers().get(2).getTimeProperty());
    }


        // setOn Action Methods


    @FXML
    private void quitButtonSetOnAction(){
        SoundPlayer.buttonClickSound();
        DAOSerialize.writeSerialized(GameApplication.getSerializeObject());
        System.exit(0);
    }

    private void bindOptions(){
        currentSongLabel.setText(GameApplication.getSerializeObject().getOptions().getCurrentSongTitle());
        if (!GameApplication.getSerializeObject().getOptions().getListenToBackroundMusic()){
            musicCheckBox.setSelected(false);
        }
        if (!GameApplication.getSerializeObject().getOptions().getListenToButtonSounds()){
            soundCheckBox.setSelected(false);
        }
        if (GameApplication.getSerializeObject().getOptions().getCurrentSongIndex()==0){
            vivaldiWinterButton.setSelected(true);
        }
        if (GameApplication.getSerializeObject().getOptions().getCurrentSongIndex()==1){
            BachSonataButton.setSelected(true);
        }
        if (GameApplication.getSerializeObject().getOptions().getCurrentSongIndex()==2){
            vivaldiSummerButton.setSelected(true);
        }
        if (GameApplication.getSerializeObject().getOptions().getCurrentSongIndex()==3){
            vivaldiSpringButton.setSelected(true);
        }
        currentSongLabel.textProperty().bind(GameApplication.getSerializeObject().getOptions().currentSongProperty());
    }

    @FXML
    private void chooseMusicSetOnAction(){
        int idx = GameApplication.getSerializeObject().getOptions().getCurrentSongIndex();
        if (vivaldiWinterButton.isSelected()){
            idx = 0;
            GameApplication.getSerializeObject().getOptions().setCurrentSongTitle("Vivaldi Winter");
        } else if (BachSonataButton.isSelected()){
            idx = 1;
            GameApplication.getSerializeObject().getOptions().setCurrentSongTitle("Bach Sonata");
        } else if (vivaldiSummerButton.isSelected()) {
            idx = 2;
            GameApplication.getSerializeObject().getOptions().setCurrentSongTitle("Vivaldi Summer");
        } else if (vivaldiSpringButton.isSelected()) {
            idx = 3;
            GameApplication.getSerializeObject().getOptions().setCurrentSongTitle("Vivaldi Spring");
        }
        SoundPlayer.chooseBackroundMusic(idx);
    }

    @FXML
    private void musicButtonSetOnAction(){
        if (musicCheckBox.isSelected()) {
            GameApplication.getSerializeObject().getOptions().setListenToBackroundMusic(true);
            SoundPlayer.playMusic();
        }else if(!musicCheckBox.isSelected()){
            GameApplication.getSerializeObject().getOptions().setListenToBackroundMusic(false);
            SoundPlayer.stopMusic();
        }
    }

    @FXML
    private void soundsButtonSetOnAction(){
        if (soundCheckBox.isSelected()) {
            GameApplication.getSerializeObject().getOptions().setListenToButtonSounds(true);

        } else if (!soundCheckBox.isSelected()) {
            GameApplication.getSerializeObject().getOptions().setListenToButtonSounds(false);
            SoundPlayer.stopSoundEffect();
        }
    }
    @FXML
    private void rulesButtonSetOnAction(){
        SoundPlayer.buttonClickSound();
        rulesGroundPane.setVisible(true);
        leaderBoardPane.setVisible(false);
        settingsPane.setVisible(false);
        newGamePane.setVisible(false);
    }

    @FXML
    private void leaderBoardButtonAction() {
        SoundPlayer.buttonClickSound();
        leaderBoardPane.setVisible(true);
        settingsPane.setVisible(false);
        newGamePane.setVisible(false);
        rulesGroundPane.setVisible(false);
    }
    @FXML
    private void settingsButtonAction() {
        SoundPlayer.buttonClickSound();
        settingsPane.setVisible(true);
        newGamePane.setVisible(false);
        leaderBoardPane.setVisible(false);
        rulesGroundPane.setVisible(false);
    }


    @FXML
    private void newGameButtonAction(ActionEvent event) {
        SoundPlayer.buttonClickSound();
        newGamePane.setVisible(true);
        leaderBoardPane.setVisible(false);
        settingsPane.setVisible(false);
        rulesGroundPane.setVisible(false);
    }

    @FXML
    private void newGameSubmitButtonSetOnaction(ActionEvent event) {
        SoundPlayer.buttonClickSound();
        System.out.println("newGameSubmitButtonSetOnaction called");
        Button b = (Button) event.getSource();
        if (playerAmountIsSelected== true && gameModeIsSelected == true){
            playerAmountIsSelected = false;
            gameModeIsSelected = false;
            if (b.getId() == newGameSubmitButton.getId())

                GameApplication.getSerializeObject().generateGameState(selectAmountOfPlayer(),selectGameMode());
                DAOSerialize.writeSerialized(GameApplication.getSerializeObject());
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/midterm/setfinal/fxml/game.fxml"));
                    System.out.println("FXML gameFile loaded");
                    Scene scene = new Scene(loader.load());
                    Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);
                    currentStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/setGameIcon.png")));
                    currentStage.setResizable(true);
                    currentStage.setFullScreen(true);


                    currentStage.setTitle("SET");
                    GameApplication.getSerializeObject().clearChoosingVariables(); //Clears alls choosing Variables
                    System.out.println("newGameSubmitButtonSetOnaction playerListSize = " + GameApplication.getSerializeObject().getGameState().getPlayerList().size());
                    GameController.assignImagesToButtons();
                    addUserKeyListener(scene);
                    currentStage.show();
                } catch (IOException e) {
                    System.out.println("newGameSubmitButtonSetOnaction failed");
                    e.printStackTrace();
                }
        }
    }

    @FXML
    private void loadGameButtonSetOnAction(ActionEvent event){
        SoundPlayer.buttonClickSound();
        System.out.println("LoadGameButtonSetOnAction called");
        Button b = (Button) event.getSource();

        GameApplication.getSerializeObject().prepForLoad();

            if (b.getId() == loadGameButton.getId())
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/midterm/setfinal/fxml/game.fxml"));
                    System.out.println("FXML gameFile loaded");
                    Scene scene = new Scene(loader.load());
                    Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);
                    currentStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/setGameIcon.png")));
                    currentStage.setResizable(true);
                    currentStage.setFullScreen(true);

                    currentStage.setTitle("SET");
                    GameApplication.getSerializeObject().clearChoosingVariables();
                    GameController.assignImagesToButtons();
                    addUserKeyListener(scene);
                    currentStage.show();
                } catch (IOException e) {
                    System.out.println("loadGameButtonSetOnAction failed");
                    e.printStackTrace();
                }
    }

    public static void addUserKeyListener(Scene gameScene) {
        //Player pressed their Key
        gameScene.setOnKeyPressed((EventHandler<KeyEvent>) keyEvent -> {
            System.out.println("user pressed key:"+keyEvent.getCode());
            if (keyEvent.getCode() == KeyCode.getKeyCode("C")) {
                if(!GameLogic.cheat())
                    System.out.println("Cheat Failed");
            } else
                GameLogic.choosingStart(keyEvent.getCode());
        });
    }

    @FXML
    private String selectGameMode(){
        SoundPlayer.buttonClickSound();
        String gameMode = "";
        
        if (classicModeRButton.isSelected()) {
            gameMode = "classic";
            gameModeIsSelected = true;
        } else if (oneDeckRButton.isSelected()) {
            gameMode = "oneDeck";
            gameModeIsSelected = true;
        } else if (bestOf5Button.isSelected()) {
            gameMode = "bestOf5";
            gameModeIsSelected = true;
        } else if (bestOf9Button.isSelected()) {
            gameMode = "bestOf9";
            gameModeIsSelected = true;
        }
        return gameMode;
    }

    @FXML
    private int selectAmountOfPlayer(){
        SoundPlayer.buttonClickSound();
        int amountOfPlayer = 0;

        if (playerAmount1.isSelected()) {
            amountOfPlayer = 1;
            playerAmountIsSelected = true;
        } else if (playerAmount2.isSelected()) {
            amountOfPlayer = 2;
            playerAmountIsSelected = true;
        }  else if (playerAmount3.isSelected()) {
            amountOfPlayer = 3;
            playerAmountIsSelected = true;
        } else if (playerAmount4.isSelected()) {
            amountOfPlayer = 4;
            playerAmountIsSelected = true;
        }
        return amountOfPlayer;
    }

    // hoverMethods

    @FXML
    private void LeaderboardMouseEntered(){
        infoLabel.setText("Leaderboard");
        infoLabel.setVisible(true);
    }
    @FXML
    private void settingsMouseEntered(){
        infoLabel.setText("Settings");
        infoLabel.setVisible(true);
    }
    @FXML
    private void newGameMouseEntered(){
        infoLabel.setText("New Game");
        infoLabel.setVisible(true);
    }
    @FXML
   private void rulesMouseEntered(){
        infoLabel.setText("Rules");
        infoLabel.setVisible(true);
    }
    @FXML
    private void quitMouseEntered(){
        infoLabel.setText("Quit");
        infoLabel.setVisible(true);
    }
    @FXML
    private void loadgameMouseEntered(){
        infoLabel.setText("Load Game");
        infoLabel.setVisible(true);
    }
    @FXML
    private void quitMouseExited(){
        infoLabel.setVisible(false);
    }
    @FXML
    private void loadgameMouseExited(){
        infoLabel.setVisible(false);
    }
    @FXML
    private void newGameMouseExited(){
        infoLabel.setVisible(false);
    }
    @FXML
    private void leaderboardMouseExited(){
        infoLabel.setVisible(false);
    }
    @FXML
    private void rulesMouseExited(){
        infoLabel.setVisible(false);
    }
    @FXML
    private void settingsMouseExited(){
        infoLabel.setVisible(false);
    }
}
