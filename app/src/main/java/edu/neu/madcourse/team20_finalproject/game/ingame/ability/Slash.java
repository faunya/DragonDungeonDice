package edu.neu.madcourse.team20_finalproject.game.ingame.ability;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;

public class Slash extends Ability {

    public Slash(String name, int diceType, int stat, int cost) {
        super(name, diceType, stat, cost);
    }

    @Override
    public void use(Entity target, int dmg) {
        target.takeDmg(dmg);
    }
}
