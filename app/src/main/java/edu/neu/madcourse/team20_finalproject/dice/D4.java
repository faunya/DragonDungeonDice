package edu.neu.madcourse.team20_finalproject.dice;

import edu.neu.madcourse.team20_finalproject.R;

public class D4 extends Die {

    public D4() {
        this.side = 4;
        this.imgId = R.drawable.d4;
        this.rotateImgId = R.drawable.d4_rotate;
    }

    @Override
    public String toString() {
        return "D4";
    }
}
