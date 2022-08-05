package edu.neu.madcourse.team20_finalproject.dice;

import edu.neu.madcourse.team20_finalproject.R;

public class D12 extends Die {

    public D12() {
        this.side = 12;
        this.imgId = R.drawable.d12;
        this.rotateImgId = R.drawable.d12_rotate;
    }

    @Override
    public String toString() {
        return "D12";
    }
}
