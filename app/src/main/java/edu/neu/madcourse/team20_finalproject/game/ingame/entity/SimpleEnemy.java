package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import java.util.Random;

public class SimpleEnemy extends NPC {

    public SimpleEnemy(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
        rand = new Random();
    }

    @Override
    public String behavior(Entity target, int ac) {
        int choice = rand.nextInt(3);

        //regular attack
        if (choice == 0 || choice == 1) {
            return super.behavior(target, ac);
        } else { //special attack
            if (sp == 3) { //if enough sp to use ability
                doubleAttack(target, ac);
            }
            return super.behavior(target, ac);
        }
    }


    public String doubleAttack(Entity target, int ac) {
        int hit1 = rand.nextInt(20) + 1 + Entity.calcModifier(dex);
        int hit2 = rand.nextInt(20) + 1 + Entity.calcModifier(dex);

        int dmg1 = rand.nextInt(4) + 1 + Entity.calcModifier(str);
        int dmg2 = rand.nextInt(4) + 1 + Entity.calcModifier(str);

        StringBuilder builder = new StringBuilder();
        builder.append(name + " swipes at " + target.getName() + " twice\n");
        if (hit1 >= ac) { //first hits
            builder.append("The first strike hits, dealing" + String.valueOf(dmg1) + " dmg\n");
            attack(target, dmg1);
        } else { //first misses
            builder.append("The first strike misses\n");
        }
        if (hit2 >= ac) {
            builder.append("The second strike hits, dealing" + String.valueOf(dmg2) + " dmg\n");
            attack(target, dmg2);
        } else {
            builder.append("The second strike misses");
        }

        return builder.toString();
    }
}
