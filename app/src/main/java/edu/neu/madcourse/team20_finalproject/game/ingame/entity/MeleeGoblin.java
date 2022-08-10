package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import java.util.Random;

public class MeleeGoblin extends NPC {

    public MeleeGoblin(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
        rand = new Random();
    }

    @Override
    public String behavior(Entity target, int ac) {
        int choice = rand.nextInt(3);

        if (choice == 2 && sp >= 3) {//special attack and has enough sp
            return doubleAttack(target, ac);
        } //regular attack
        return super.behavior(target, ac);
    }
}
