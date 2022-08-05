package edu.neu.madcourse.team20_finalproject.dice;

import edu.neu.madcourse.team20_finalproject.R;

public class D10 extends Die {

    public D10() {
        this.side = 10;
        this.imgId = R.drawable.d10;
        this.rotateImgId = R.drawable.d10_rotate;
    }

    @Override
    public String toString() {
        return "D10";
    }
}
