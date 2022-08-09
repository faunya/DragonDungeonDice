package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.neu.madcourse.team20_finalproject.R;

public class NPC extends Entity {
    protected Random rand;
    protected List<String> dialog;
    protected int id;
    protected int img;

    protected int xp;

    public NPC(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
        rand = new Random();
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<String> getDialog() {
        return dialog;
    }

    public void setDialog(List<String> dialog) {
        this.dialog = dialog;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public String behavior(Entity target, int ac) {
        return regAttack(target, ac);
    }

    public String regAttack(Entity target, int ac) {
        setSp(sp + 1);
        int hit = rand.nextInt(20) + 1 + Entity.calcModifier(dex);
        int dmg = rand.nextInt(6) + 1 + Entity.calcModifier(str);
        //System.out.println("ac: " + ac + ", hit: " + hit + ", dmg: " + dmg);
        if (hit < ac) { //if misses
            return name + " swings at " + target.getName() + " but misses completely";
        }
        if (dmg <= 0) { //0 dmg
            return name + " attacks " + target.getName() + " but their armor deflects the blow";
        }
        attack(target, dmg);
        return name + " attacks " + target.getName() + " for " + String.valueOf(dmg) + " dmg";
    }

    public String doubleAttack(Entity target, int ac) {
        setSp(sp - 3);
        int hit1 = rand.nextInt(20) + 1 + Entity.calcModifier(dex);
        int hit2 = rand.nextInt(20) + 1 + Entity.calcModifier(dex);

        int dmg1 = rand.nextInt(4) + 1 + Entity.calcModifier(str);
        int dmg2 = rand.nextInt(4) + 1 + Entity.calcModifier(str);

        StringBuilder builder = new StringBuilder();
        builder.append(name + " swipes at " + target.getName() + " twice\n");
        if (hit1 >= ac) { //first hits
            builder.append("The first strike hits, dealing " + String.valueOf(dmg1) + " dmg\n");
            attack(target, dmg1);
        } else { //first misses
            builder.append("The first strike misses\n");
        }
        if (hit2 >= ac) {
            builder.append("The second strike hits, dealing " + String.valueOf(dmg2) + " dmg\n");
            attack(target, dmg2);
        } else {
            builder.append("The second strike misses");
        }

        return builder.toString();
    }

    public String fireballAttack(Entity target, int ac) {
        sp -= 2;
        int hit = rand.nextInt(20) + 1 + Entity.calcModifier(dex);

        if (hit >= ac) {
            int dmg = rand.nextInt(6) + 1 + Entity.calcModifier(inte);
            attack(target, dmg);
            return name + " shoots a fireball at " + target.getName() +
                    " dealing " + String.valueOf(dmg) + " dmg";
        } else {
            if (rand.nextInt(2) == 0) {
                return name + " shoots a fireball but "
                        + target.getName() + " manages to dodge out of the way";
            }
            return name + " launches a fireball at " + target.getName() + "but misses";
        }
    }

    public static MeleeGoblin createMeleeGoblin() {
        MeleeGoblin goblin = new MeleeGoblin("Goblin", 7, 3);
        List<String> dialog = new ArrayList<>();

        dialog.add("*screeches*");
        dialog.add("Grrrrr");
        dialog.add("I'll kill you!");
        dialog.add("DIE!");

        goblin.setArmorClass(8);
        goblin.setDialog(dialog);
        goblin.setStr(8);
        goblin.setDex(12);
        goblin.setVit(10);
        goblin.setWis(8);
        goblin.setInte(10);
        goblin.setSpd(12);

        goblin.setXp(12);

        goblin.setImg(R.drawable.goblinsword);

        return goblin;
    }

    public static MagicGoblin createMagicGoblin() {
        MagicGoblin goblin = new MagicGoblin("", 7, 4);

        goblin.setArmorClass(8);
        goblin.setStr(8);
        goblin.setDex(10);
        goblin.setVit(10);
        goblin.setWis(12);
        goblin.setInte(12);
        goblin.setSpd(8);

        goblin.setXp(17);
        goblin.setImg(R.drawable.goblinmagic);

        return goblin;
    }
}
