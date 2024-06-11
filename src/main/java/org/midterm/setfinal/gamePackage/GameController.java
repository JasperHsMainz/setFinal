package org.midterm.setfinal.gamePackage;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.midterm.setfinal.GameApplication;
import org.midterm.setfinal.GameLogic.GameLogic;
import org.midterm.setfinal.GameLogic.SoundPlayer;
import org.midterm.setfinal.serialize.Card;
import org.midterm.setfinal.serialize.DAOSerialize;
import org.midterm.setfinal.winning.WinningWindowController;

import java.io.IOException;



public class GameController {
    @FXML
    private GridPane mainGrid;

@FXML
private Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14,button15,button16,button17,button18,
        saveExitButton,menuButton,drawcardButton,submitSetButton;
@FXML
private Label remainingCardsDeckLabel,playerName1,playerName2,playerName3,playerName4,timerLabel;

private static Label[] playerLabels;
private static Button[] playGroundButtons;
private static Button staticSubmitSetButton;
private static Label[] staticTimerLabel;


    @FXML
    public void initialize() {
        staticSubmitSetButton = submitSetButton;
        playGroundButtons = new Button[]{button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14,button15,button16,button17,button18,};
        playerLabels = new Label[]{playerName1,playerName2,playerName3,playerName4};
        staticTimerLabel = new Label[]{timerLabel};

    bindGameSceneLabels();
        Scene scene = mainGrid.getScene();
        if (scene != null) {
            setupDynamicSizing(scene);
        } else {
            mainGrid.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    setupDynamicSizing(newScene);
                }
            });
        }
    }
    public void bindGameSceneLabels() {
        try {
            for (int i = 0; i < GameApplication.getSerializeObject().getGameState().getPlayerList().size(); i++) {
                playerLabels[i].textProperty().bind(
                        Bindings.concat("Player " + (i + 1) + ": ", GameApplication.getSerializeObject().getGameState().getPlayerList().get(i).getScoreProperty().asString(),
                                "\n userKey :", GameApplication.getSerializeObject().getGameState().getPlayerList().get(i).getUserKey())
                );
                timerLabel.textProperty().bind(GameApplication.getSerializeObject().getGameState().getTurnTimerProperty().asString());
            }
        }catch (Exception e){
            System.out.println("bindPlayerLabels NullPointerException!!!!!!!");
        }
    }

    public static void assignImagesToButtons(){
        if (playGroundButtons != null) {
            for (int i = 0; i < playGroundButtons.length; i++) {
                Button playGroundButton = playGroundButtons[i];
                Card card = GameApplication.getSerializeObject().getGameState().getDisplayedCards().get(i);
                if (card != null) {
                    javafx.scene.image.Image image = new Image(GameController.class.getResourceAsStream(card.getImgLink()));
                    BackgroundImage backgroundImage = new BackgroundImage(image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            new BackgroundSize(100, 100, true, true, true, false));
                    Background background = new Background(backgroundImage);
                    playGroundButton.setBackground(background);
                    playGroundButton.setDisable(false);
                } else {
                    playGroundButton.setDisable(true);
                    playGroundButton.setBackground(null);
                }
            }
        }
    }


    public static void openWinnerWindow() throws IOException {
        WinningWindowController controller = new WinningWindowController();
        controller.openWinnerWindow( (Stage) staticSubmitSetButton.getScene().getWindow());
    }


    public static void openWinnerWindowForClassicMode() throws IOException {
        WinningWindowController controller = new WinningWindowController();
        controller.openWinnerWindow((Stage) staticSubmitSetButton.getScene().getWindow());
    }

    private void setupDynamicSizing(Scene scene) {
        // bind width and height
        playerName1.prefWidthProperty().bind(scene.widthProperty().multiply(0.1));
        playerName1.prefHeightProperty().bind(scene.heightProperty().multiply(0.1));

        playerName2.prefWidthProperty().bind(scene.widthProperty().multiply(0.1));
        playerName2.prefHeightProperty().bind(scene.heightProperty().multiply(0.1));
        //
        playerName3.prefWidthProperty().bind(scene.widthProperty().multiply(0.1));
        playerName3.prefHeightProperty().bind(scene.heightProperty().multiply(0.1));
        //
        playerName4.prefWidthProperty().bind(scene.widthProperty().multiply(0.1));
        playerName4.prefHeightProperty().bind(scene.heightProperty().multiply(0.1));
        //
        remainingCardsDeckLabel.prefWidthProperty().bind(scene.widthProperty().multiply(0.1));
        remainingCardsDeckLabel.prefHeightProperty().bind(scene.heightProperty().multiply(0.05));

        submitSetButton.prefHeightProperty().bind(saveExitButton.heightProperty());
        submitSetButton.prefWidthProperty().bind(saveExitButton.widthProperty());

        // bind Font-size

        timerLabel.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", scene.widthProperty().add(scene.heightProperty()).divide(20).asString(), ";"
        ));

        remainingCardsDeckLabel.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", scene.widthProperty().add(scene.heightProperty()).divide(20).asString(), ";"
        ));

        playerName1.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", scene.widthProperty().add(scene.heightProperty()).divide(100).asString(), ";"
        ));
        playerName2.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", scene.widthProperty().add(scene.heightProperty()).divide(100).asString(), ";"
        ));
        playerName3.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", scene.widthProperty().add(scene.heightProperty()).divide(100).asString(), ";"
        ));
        playerName4.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", scene.widthProperty().add(scene.heightProperty()).divide(100).asString(), ";"
        ));

        drawcardButton.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", scene.widthProperty().add(scene.heightProperty()).divide(100).asString(), ";"
        ));
        menuButton.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", scene.widthProperty().add(scene.heightProperty()).divide(100).asString(), ";"
        ));
        saveExitButton.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", scene.widthProperty().add(scene.heightProperty()).divide(100).asString(), ";"
        ));
    }


    // setOnAction Methods

    @FXML
    private void drawcardButtonSetOnAction() {
        SoundPlayer.buttonClickSound();
        GameLogic.draw3Extra();
        assignImagesToButtons();
    }


    @FXML
    private void confirmSetSubmitButtonSetOnAction() throws IOException {
        SoundPlayer.buttonClickSound();
        GameLogic.choosingComplete();
    }

    public static void showConfirmSetButton(){
        staticSubmitSetButton.setVisible(true);
    }
    public static void hideConfirmSetButton(){
        staticSubmitSetButton.setVisible(false);
    }
    public static void highlightButtonX(int buttonNr){
        playGroundButtons[buttonNr].setOpacity(0.5);
    }
    public static void dehighlightButtonX(int buttonNr){
        playGroundButtons[buttonNr].setOpacity(1);
    }
    public static void dehighlightAll(){for (int i = 0; i < playGroundButtons.length; i++) {dehighlightButtonX(i);}}
    public static void showTimerLabel(){
        staticTimerLabel[0].setVisible(true);
    }
    public static void hideTimerLabel(){
        staticTimerLabel[0].setVisible(false);
    }

    @FXML
    private void cardActionSetOnAction(Event event) {
        SoundPlayer.buttonClickSound();
        Button button = (Button) event.getSource();
        int idx = Integer.parseInt(button.getId().substring(6));
        System.out.println("Button "+idx+" pressed");
        GameLogic.choosingCardChosen(idx);
    }

    @FXML
    private void saveAndExitButtonSetOnAction(){
        SoundPlayer.buttonClickSound();
        GameApplication.getSerializeObject().getGameState().calculateHighScoreTotalTimeElaplsed(); //Calculates current highscoreTime for serialization
        //GameApplication.getSerializeObject().clearChoosingVariables(); //Clears choosing and stops TurnTimerThread
        DAOSerialize.writeSerialized(GameApplication.getSerializeObject());
        System.exit(0);
    }

    @FXML
    private void menuButtonSetOnAction(ActionEvent event) throws IOException {
        SoundPlayer.buttonClickSound();
        GameApplication.getSerializeObject().getGameState().calculateHighScoreTotalTimeElaplsed(); //Calculates current highscoreTime for serialization
        GameApplication.getSerializeObject().clearChoosingVariables(); //Clears choosing and stops TurnTimerThread
        DAOSerialize.writeSerialized(GameApplication.getSerializeObject());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/midterm/setfinal/fxml/menu.fxml"));
        Scene gameScene = new Scene(fxmlLoader.load());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        currentStage.setScene(gameScene);
        currentStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/setGameIcon.png")));
        currentStage.setWidth(1080);
        currentStage.setHeight(760);
        currentStage.setResizable(false);
        currentStage.setTitle("SET");
        currentStage.show();
    }

    // hover Methdos
@FXML
private void remainingCardsDeckLabelMouseEntered(){
        remainingCardsDeckLabel.setText(String.valueOf(GameApplication.getSerializeObject().getGameState().getDeck().size()));
        remainingCardsDeckLabel.setVisible(true);
}
@FXML
private void setRemainingCardsDeckLabelMouseExit(){
        remainingCardsDeckLabel.setVisible(false);
    }
}
