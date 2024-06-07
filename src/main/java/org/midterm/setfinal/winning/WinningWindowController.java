package org.midterm.setfinal.winning;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.midterm.setfinal.GameApplication;
import org.midterm.setfinal.GameLogic.GameLogic;
import org.midterm.setfinal.GameLogic.SoundPlayer;
import org.midterm.setfinal.gamePackage.GameController;
import org.midterm.setfinal.menu.MenuController;
import org.midterm.setfinal.serialize.DAOSerialize;
import org.midterm.setfinal.serialize.Player;

import java.io.IOException;

public class WinningWindowController {
    @FXML
    private  AnchorPane classicAskPane;

    @FXML
    private Label youWonLabel;
    @FXML
    private TextField winnerNamePassword;
    @FXML
    private Button submitWinningWindow, exitGame,continuePlayingButton;

    private static Stage stage;

    @FXML
    public  void openWinnerWindow(Stage currentStage) throws IOException {

        FXMLLoader fxmlLoader2 = new FXMLLoader(WinningWindowController.class.getResource("/org/midterm/setfinal/fxml/winnerWindow.fxml"));
        Scene scene = new Scene(fxmlLoader2.load());
        stage = currentStage;
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize() {
        testForClassicEnd();
    }

    private  void testForClassicEnd(){
        if (GameApplication.getSerializeObject().getGameState().getGameMode() == "classic"){
            this.classicAskPane.setVisible(true);
        }
    }

    // setOnAction methods

    @FXML
    private void continuePlayingButtonSetOnAction(ActionEvent event) throws IOException {

        GameLogic.reshuffelDeck();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/midterm/setfinal/fxml/game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        GameController.assignImagesToButtons();
        MenuController.addUserKeyListener(scene);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/setGameIcon.png")));
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.setMinHeight(720);
        stage.setMinWidth(1080);
        stage.show();
    }


    @FXML
    private void exitGameSetOnAction() {
        classicAskPane.setVisible(false);
    }

    @FXML
    public void submitWinningWindowSetOnAction() throws IOException {
        SoundPlayer.buttonClickSound();
        if (winnerNamePassword == null) {
            System.out.println("winnerNamePassword ist null");
        } else {
            Player player = GameLogic.getWinningPlayer();
            String gameMode = GameApplication.getSerializeObject().getGameState().getGameMode();
            String playerName = winnerNamePassword.getText();
            player.setPlayerNameProperty(playerName);

            GameApplication.getSerializeObject().getLeaderBoard().addToLeaderBoard(player, gameMode);
            System.out.println("Zum Leaderboard hinzugefügt: " + playerName);
            GameApplication.getSerializeObject().generateGameState();
            DAOSerialize.writeSerialized(GameApplication.getSerializeObject());
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(GameApplication.getFxmlPath()));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }
}

