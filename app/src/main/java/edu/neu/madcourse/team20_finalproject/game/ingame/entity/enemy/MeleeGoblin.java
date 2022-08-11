package edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy;

import java.util.Random;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.NPC;

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
