package edu.neu.madcourse.team20_finalproject.game;

/**
 *
 */
public class Player extends Entity {
    private int xp;
    private int lv;

    //equips
    private Weapon main;
    private Weapon offhand;

    public Player(String name, int maxHp, int maxMp, int lv) {
        super(name, maxHp, maxMp);

        this.lv = lv;
        this.xp = 0;
    }
}
