package edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.NPC;

public class Golem extends NPC {

    public Golem(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
    }

    @Override
    public String behavior(Entity target, int ac) {
        int choice = rand.nextInt(2);
        if ((choice == 1 || counter == 1) && sp >= 3) {
            return chargeAttack(target, ac);
        } else {
            return regAttack(target, ac);
        }
    }
}
