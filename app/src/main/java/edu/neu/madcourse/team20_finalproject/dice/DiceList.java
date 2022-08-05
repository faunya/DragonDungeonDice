package edu.neu.madcourse.team20_finalproject.dice;

public class DiceList {
    private Die[] diceList;
    private static final int SIZE = 6;
    private int currDie;

    public DiceList() {
        diceList = new Die[]{new D4(), new D6(), new D8(), new D10(), new D12(), new D20()};
        currDie = 1;
    }

    public Die getDie(int position) {
        if (position >= 0 && position < SIZE) {
            currDie = position;
        }
        return diceList[currDie];
    }

    public Die getPrevious() {
        currDie = (currDie + SIZE - 1) % SIZE;
        return diceList[currDie];
    }

    public Die getNext() {
        currDie = (currDie + SIZE + 1) % SIZE;
        return diceList[currDie];
    }

    public int getCurrDie() {
        return currDie;
    }
}
