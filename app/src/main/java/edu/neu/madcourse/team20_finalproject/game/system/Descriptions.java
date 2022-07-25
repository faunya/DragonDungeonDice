package edu.neu.madcourse.team20_finalproject.game.system;

import java.util.Random;

public abstract class Descriptions {
    private Random rand;
    private String[] comWepAdj = {"Dull", "Blunt", "Chipped", "Rusted"};
    private String[] rareWepAdj = {"Honed", "Sharp", "Serrated", "Fine"};

    private String[] wepMat = {"Bronze", "Iron", "Steel", "Mithral", "Wooden"};
    private String[] bladeWepType = {"Dagger", "Arming Sword", "Longsword", "Rapier", "Saber"
            , "Greatsword"};
    private String[] poleWepType = {"Javelin", "Lance", "Spear", "Mace", "Club", "Pole Axe", "Hatchet"};

    public String totalRandGenWep() {
        StringBuilder builder = new StringBuilder();
        rand = new Random();

        int adj = rand.nextInt(100);
        if (adj <= 32) {
            builder.append(comWepAdj[rand.nextInt(comWepAdj.length)]);
        } else if (adj >= 89) {
            builder.append(comWepAdj[rand.nextInt(rareWepAdj.length)]);
        }



        return builder.toString();
    }
}
