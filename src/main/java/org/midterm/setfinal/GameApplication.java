package org.midterm.setfinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.midterm.setfinal.GameLogic.SoundPlayer;
import org.midterm.setfinal.serialize.DAOSerialize;
import org.midterm.setfinal.serialize.SerializeObject;

import java.io.IOException;

public class GameApplication extends Application {
    private static final String fxmlPath = "/org/midterm/setfinal/fxml/menu.fxml";
    private static SerializeObject serializeObject;


    @Override
    public void start(Stage stage) throws IOException {
        fetchSerializeObject();
        SoundPlayer.playMusic();
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SET");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/setGameIcon.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    private void fetchSerializeObject(){
        serializeObject = DAOSerialize.readSerialized();
        if (serializeObject == null) {
            serializeObject = new SerializeObject();
            DAOSerialize.writeSerialized(serializeObject);
        }
        if (serializeObject.getGameState().getDeck()==null) {
            serializeObject.generateAll();
            DAOSerialize.writeSerialized(serializeObject);
        }
    }
    public static SerializeObject getSerializeObject() {
        return serializeObject;
    }
    public static String getFxmlPath() {
        return fxmlPath;
    }
}