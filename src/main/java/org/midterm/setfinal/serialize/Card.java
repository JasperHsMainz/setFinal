package org.midterm.setfinal.serialize;

import org.midterm.setfinal.enums.Amount;
import org.midterm.setfinal.enums.Colour;
import org.midterm.setfinal.enums.Shading;
import org.midterm.setfinal.enums.Shape;


public class Card implements java.io.Serializable{
    private final Shape shape;
    private final Amount amount;
    private final Colour colour;
    private final Shading shading;
    private String imgLink;
    private String imagePath = "/cardImages/";

    public Card(Amount amount, Colour colour, Shape shape, Shading shading){
        this.shape = shape;
        this.amount = amount;
        this.colour = colour;
        this.shading = shading;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(imagePath).append(amount).append(colour).append(shape).append(shading).append(".png");
        imgLink = stringBuilder.toString();
    }

    //Getters
    public Shape getShape() {
        return shape;
    }

    public Amount getAmount() {
        return amount;
    }

    public Colour getColour() {
        return colour;
    }

    public Shading getShading() {
        return shading;
    }

    public String getImgLink() {
        return imgLink;
    }
}
