package com.gamelogic.logic;

public class DrawCoordinate {
    private int currX;
    private int currY;
    private int prevX;
    private int prevY;
    private String color;
    private int lineWidth;

    public DrawCoordinate(int currX, int currY, int prevX, int prevY, String color, int lineWidth)
    {
        this.currX = currX;
        this.currY = currY;
        this.prevX = prevX;
        this.prevY = prevY;
        this.color = color;
        this.lineWidth = lineWidth;
    }

    public int getCurrX() {
        return currX;
    }

    public void setCurrX(int currX) {
        this.currX = currX;
    }

    public int getCurrY() {
        return currY;
    }

    public void setCurrY(int currY) {
        this.currY = currY;
    }

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }
}
