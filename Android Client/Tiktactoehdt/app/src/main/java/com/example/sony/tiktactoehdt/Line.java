package com.example.sony.tiktactoehdt;

/**
 * Created by SONY on 3/15/2018.
 */

public class Line {
    private int startX;
    private int starY;
    private int stopX;
    private int stopY;

    public Line(int startX, int starY, int stopX, int stopY) {
        this.startX = startX;
        this.starY = starY;
        this.stopX = stopX;
        this.stopY = stopY;
    }

    public Line() {
    }

    public int getStartX() {
        return startX;
    }

    public int getStarY() {
        return starY;
    }

    public int getStopX() {
        return stopX;
    }

    public int getStopY() {
        return stopY;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStarY(int starY) {
        this.starY = starY;
    }

    public void setStopX(int stopX) {
        this.stopX = stopX;
    }

    public void setStopY(int stopY) {
        this.stopY = stopY;
    }
}
