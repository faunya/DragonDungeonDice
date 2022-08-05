package edu.neu.madcourse.team20_finalproject.skinRecycler;

import edu.neu.madcourse.team20_finalproject.R;

public enum WallpaperID {
    B1("b1"),
    B2("b2"),
    B3("b3"),
    B4("b4"),
    B5("b5"),
    B6("b6"),
    B7("b7"),
    B8("b8"),
    B9("b9");

    private String wpString;


    WallpaperID(String wpString) {
        this.wpString = wpString;
    }

    public String getWpString() {
        return wpString;
    }

    public static int getWallpaperReference(WallpaperID wallpaperID) {
        switch (wallpaperID) {
            case B1:
                return R.drawable.b1;
            case B2:
                return R.drawable.b2;
            case B3:
                return R.drawable.b3;
            case B4:
                return R.drawable.b4;
            case B5:
                return R.drawable.b5;
            case B6:
                return R.drawable.b6;
            case B7:
                return R.drawable.b7;
            case B8:
                return R.drawable.b8;
            case B9:
                return R.drawable.b9;
            default:
                break;
        }
        return 0;
    }
}
