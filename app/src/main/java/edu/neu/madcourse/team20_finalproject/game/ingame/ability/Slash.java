package edu.neu.madcourse.team20_finalproject.game.ingame.ability;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;

public class Slash extends Ability {

    public Slash(int diceType, int stat, int cost) {
        super("slash", diceType, stat, cost);
    }

    public Slash() {
        super("slash", 2,0,2);
    }

    @Override
    public void use(Entity target, int dmg) {
        target.takeDmg(dmg);
    }
}
