package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import android.os.Parcel;

import java.util.List;

import edu.neu.madcourse.team20_finalproject.R;

public class NPC extends Entity {
    private List<String> dialog;
    private int id;
    R.drawable.b1

    public NPC(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<String> getDialog() {
        return dialog;
    }

    public void setDialog(List<String> dialog) {
        this.dialog = dialog;
    }
}
