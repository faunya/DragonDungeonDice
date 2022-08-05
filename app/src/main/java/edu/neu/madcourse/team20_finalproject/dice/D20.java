package edu.neu.madcourse.team20_finalproject.dice;

import edu.neu.madcourse.team20_finalproject.R;

public class D20 extends Die {

    public D20() {
        this.side = 20;
        this.imgId = R.drawable.d20;
        this.rotateImgId = R.drawable.d20_rotate;
    }

    @Override
    public String toString() {
        return "D20";
    }
}
