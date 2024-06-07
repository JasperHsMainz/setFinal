package org.midterm.setfinal.GameLogic;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import org.midterm.setfinal.GameApplication;import org.midterm.setfinal.enums.Amount;import org.midterm.setfinal.enums.Colour;
import org.midterm.setfinal.enums.Shading;
import org.midterm.setfinal.enums.Shape;
import org.midterm.setfinal.serialize.Card;

import java.io.IOException;
import java.util.ArrayList;import java.util.List;

public class TurnTimerThread extends Thread{
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final int currentTime = 5-i;
            if (!GameApplication.getSerializeObject().getGameState().getTurnActive()) {
                break;
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    GameApplication.getSerializeObject().getGameState().setTurnTimerProperty(currentTime);
                }
            });
        }


        if (GameApplication.getSerializeObject().getGameState().getTurnActive()) {
            System.out.println("turnTimerWait: TurnTimer ausgelaufen");
            List<Card> tempFailList = new ArrayList<>(); //Creates false setList to fail the Player
            tempFailList.add(new Card(Amount.one,Colour.blue,org.midterm.setfinal.enums.Shape.diamond,Shading.empty));
            tempFailList.add(new Card(Amount.one,Colour.blue, Shape.diamond, Shading.empty));
            tempFailList.add(new Card(Amount.one,Colour.blue,Shape.diamond,Shading.full));
            Platform.runLater(()-> { //IMPORTANT!! Prevents JavaFx ThreadErrors
                GameApplication.getSerializeObject().getGameState().setChoosenCards(tempFailList);
                try {
                    GameLogic.choosingComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
