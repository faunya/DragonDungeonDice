package edu.neu.madcourse.team20_finalproject.Dice;

import java.util.Random;

public abstract class Die {

    protected int side;
    protected int imageId;
    protected int gifId;

    public int roll() {
        Random rand = new Random();
        return rand.nextInt(side) + 1;
    }

    public int getSide() {
        return side;
    }

    public int getImageId() {
        return imageId;
    }

    public int getGifId() {
        return gifId;
    }
}
