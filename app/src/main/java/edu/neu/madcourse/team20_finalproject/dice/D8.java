package edu.neu.madcourse.team20_finalproject.dice;

import edu.neu.madcourse.team20_finalproject.R;

public class D8 extends Die {

    public D8() {
        this.side = 8;
        this.imgId = R.drawable.d8;
        this.rotateImgId = R.drawable.d8_rotate;
    }

    @Override
    public String toString() {
        return "D8";
    }
}
