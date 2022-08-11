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

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return xp;
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
            lvlUp();
            return true;
        }
        return false;
    }

    private void lvlUp() {
        armorClass += 1;
        str += 1;
        dex += 1;
        vit += 1;
        wis += 1;
        inte += 1;
        spd += 1;
        maxHp = 20 + Entity.calcModifier(vit) + lv;
        maxSp = 10 + Entity.calcModifier(wis) + lv;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public String getPClass() {
        return pClass;
    }

    public void setPClass(String pClass) {
        this.pClass = pClass;
    }

}
