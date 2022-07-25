package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import edu.neu.madcourse.team20_finalproject.game.ingame.item.Armor;
import edu.neu.madcourse.team20_finalproject.game.ingame.item.Weapon;

/**
 *
 */
public class Player extends Entity {
    private int xp;
    private int lv;

    public Player(String name, int maxHp, int maxMp, int lv) {
        super(name, maxHp, maxMp);

        this.lv = lv;
        this.xp = 0;
    }

    public int getLv() {
        return lv;
    }

    public void addXP(int gain) {
        xp += gain;

        if (xp >= (Math.pow((lv + 1)/3, 2) * 100)) {
            int nextLv = lv +1 ;
            lv = nextLv;
        }
    }


}
