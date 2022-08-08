package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import android.os.Parcel;

import edu.neu.madcourse.team20_finalproject.game.ingame.item.Armor;
import edu.neu.madcourse.team20_finalproject.game.ingame.item.Weapon;

/**
 *
 */
public class Player extends Entity {
    private int xp;
    private int lv;

    private String pClass;

    public Player(String name, int maxHp, int maxMp, int lv) {
        super(name, maxHp, maxMp);

        this.lv = lv;
        this.xp = 0;
    }

    /**
     *
     * @param gain
     * @return true if leveled up, false if didn't level up
     */
    public boolean addXP(int gain) {
        xp += gain;

        if (xp >= (Math.pow((lv + 1)/3, 2) * 100)) {
            int nextLv = lv +1 ;
            lv = nextLv;
            return true;
        }
        return false;
    }

    public int getLv() {
        return lv;
    }

    public String getPClass() {
        return pClass;
    }

    public void setPClass(String pClass) {
        this.pClass = pClass;
    }

}
