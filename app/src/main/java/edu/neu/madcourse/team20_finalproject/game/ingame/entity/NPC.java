package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import android.os.Parcel;

import java.util.List;

public class NPC extends Entity {
    private List<String> dialog;
    private int id;

    public NPC(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
