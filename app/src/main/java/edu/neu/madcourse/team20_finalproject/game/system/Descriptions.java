package edu.neu.madcourse.team20_finalproject.game.system;

import java.util.Random;

import edu.neu.madcourse.team20_finalproject.game.ingame.item.Weapon;

public class Descriptions {
    private Random rand;

    private String[] comWepAdj = {"Dull", "Blunt", "Chipped", "Rusted"};
    private String[] rareWepAdj = {"Honed", "Sharp", "Serrated", "Fine"};

    private String[] wepMat = {"Wooden", "Bronze", "Iron", "Steel", "Mithral"};

    private String[] bladeComWepType = {"Dagger", "Arming Sword", "Longsword", "Saber", "Hatchet"};
    private String[] bladeRareWepType = {"Greatsword", "Rapier", "Pole Axe"};
    private String[] poleComWepType = {"Javelin", "Spear", "Club"};
    private String[] polRareWepType = {"Lance", "Mace"};

    /**
     *
     * @param lv
     * @return
     */
    public Weapon totalRandGenWep(int lv) {
        StringBuilder builder = new StringBuilder();
        String adj = randWepAdj();
        builder.append(adj);
        //String mat;

        String wep = randWep();
        builder.append(wep);

        return new Weapon(builder.toString(), wep);
    }

    /**
     *
     * @return
     */
    public String randWepAdj() {
        rand = new Random();

        int adj = rand.nextInt(100);
        if (adj <= 32) {
            return comWepAdj[rand.nextInt(comWepAdj.length)];
        } else if (adj >= 89) {
            return comWepAdj[rand.nextInt(rareWepAdj.length)];
        } else {
            return "";
        }
    }

    /**
     *
     * @return
     */
    public String randWep() {
        int wepNum = rand.nextInt(130);
        if (wepNum < 50) {
            return bladeComWepType[rand.nextInt(bladeComWepType.length)];
        } else if (wepNum >= 50 && wepNum < 80) {
            return poleComWepType[rand.nextInt(polRareWepType.length)];
        } else if (wepNum >= 80 && wepNum < 110) {
            return bladeRareWepType[rand.nextInt(bladeRareWepType.length)];
        } else {
            return polRareWepType[rand.nextInt(polRareWepType.length)];
        }
    }


}
