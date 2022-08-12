package edu.neu.madcourse.team20_finalproject.game.ingame.ability;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;

public class Fireball extends Ability {

    public Fireball() {
        super("Fireball", 1, 3, 2);
    }

    @Override
    public void use(Entity target, int dmg) {
        target.takeDmg(dmg);
    }
}
