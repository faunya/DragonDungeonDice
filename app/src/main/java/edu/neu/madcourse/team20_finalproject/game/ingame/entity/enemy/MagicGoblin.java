package edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.NPC;

public class MagicGoblin extends NPC {

    public MagicGoblin(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
    }

    @Override
    public String behavior(Entity target, int ac) {
        int choice = rand.nextInt(3);

        if (choice == 2 && sp >= 2) {
            return fireballAttack(target, ac);
        }
        return regAttack(target, ac);
    }
}
