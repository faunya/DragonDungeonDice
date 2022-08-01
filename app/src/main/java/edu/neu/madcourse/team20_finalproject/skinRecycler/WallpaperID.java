package edu.neu.madcourse.team20_finalproject.skinRecycler;

public enum WallpaperID {
    B1("b1"),
    B2("b2"),
    B3("b3"),
    B4("b4"),
    B5("b5"),
    B6("b6"),
    B7("b7"),
    B8("b8"),
    B9("b9"),
    B10("b10"),
    B11("b11"),
    B12("b12");

    private String wpString;


    WallpaperID(String wpString) {
        this.wpString = wpString;
    }

    public String getWpString() {
        return wpString;
    }

    public static WallpaperID stringToID(String id) {
        switch (id) {
            case "b1":
                return B1;
            case "b2":
                return B2;
            case "b3":
                return B3;
            case "b4":
                return B4;
            case "b5":
                return B5;
            case "b6":
                return B6;
            case "b7":
                return B7;
            case "b8":
                return B8;
            case "b9":
                return B9;
            case "b10":
                return B10;
            case "b11":
                return B11;
            case "b12":
                return B12;
            default:
                return null;
        }
    }
}
