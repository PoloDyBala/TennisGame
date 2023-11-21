package PoolGame.items;

import PoolGame.Movable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Ball implements Movable{
    private String colour;
    private double mass;
    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;
    private Circle shape = new Circle();
    public Ball(String colour, double mass, double xPos, double yPos, double xVel, double yVel) {
        this.colour = colour;
        this.mass = mass;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.shape.setCenterX(xPos);
        this.shape.setCenterY(yPos);
        this.shape.setRadius(15.0);
        this.shape.setFill(Color.valueOf(this.colour));
    }

    @Override
    public double getXPos() {
        return xPos;
    }

    @Override
    public double getYPos() {
        return yPos;
    }

    @Override
    public double getXVel() {
        return xVel;
    }

    @Override
    public double getYVel() {
        return yVel;
    }

    @Override
    public void setXPos(double xPos) {
        this.shape.setCenterX(xPos);
        this.xPos = xPos;
    }

    @Override
    public void setYPos(double yPos) {
        this.shape.setCenterY(yPos);
        this.yPos = yPos;
    }

    @Override
    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    @Override
    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    @Override
    public void move() {

    }


    public String getColour() {
        return colour;
    }

    public double getMass() {
        return mass;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(double xPos, double yPos) {
        this.shape.setCenterX(xPos);
        this.shape.setCenterY(yPos);
    }

}
