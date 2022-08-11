package edu.neu.madcourse.team20_finalproject.game.ingame.ability;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;

public class Slash extends Ability {

    public Slash(String name, int diceType, int stat) {
        super(name, diceType, stat);
    }

    @Override
    public String use(Entity target, int ac) {
        System.out.println("slash");
        return null;
    }
}
