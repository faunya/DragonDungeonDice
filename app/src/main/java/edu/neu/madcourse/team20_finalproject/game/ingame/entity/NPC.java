package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import android.os.Parcel;

import java.util.List;

public class NPC extends Entity {
    private List<String> dialog;

    public NPC(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
