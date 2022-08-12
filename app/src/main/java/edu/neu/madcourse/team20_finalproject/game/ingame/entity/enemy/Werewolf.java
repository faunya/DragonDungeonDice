package edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.NPC;

public class Werewolf extends NPC {
    public Werewolf(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
    }

    @Override
    public String behavior(Entity target, int ac) {
        int choice = rand.nextInt(2);

        if (choice == 0 && sp >= 4) {
            return "";
        } else {
            return regAttack(target, ac);
        }
    }
}
