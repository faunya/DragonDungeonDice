package edu.neu.madcourse.team20_finalproject.dice;

import java.util.Random;

public abstract class Die {
    protected int side;
    protected int imgId;
    protected int rotateImgId;

    public int roll() {
        Random rand = new Random();
        return rand.nextInt(side) + 1;
    }

    public int getSide() {
        return side;
    }

    public int getImgId() {
        return imgId;
    }

    public int getRotateImgId() {
        return rotateImgId;
    }
}
