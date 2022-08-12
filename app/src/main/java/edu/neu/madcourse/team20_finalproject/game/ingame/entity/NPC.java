package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.neu.madcourse.team20_finalproject.R;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy.DarkCultist;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy.Djinn;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy.Dragon;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy.Golem;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy.MagicGoblin;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy.MeleeGoblin;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.enemy.Werewolf;

public class NPC extends Entity {
    protected Random rand;
    protected List<String> dialog;
    protected int id;
    protected int img;
    protected int xp;
    protected int counter;

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
            return name + " launches a fireball at " + target.getName() + " but misses";
        }
    }

    public String biteAttack(Entity target, int ac) {
        sp -= 2;

        int hit = rand.nextInt(20) + 1 + Entity.calcModifier(dex);
        if (hit >= ac) {
            int dmg = rand.nextInt(8) + 1 + Entity.calcModifier(str);
            attack(target, dmg);
            return name + " bites " + target.getName() + " for " + String.valueOf(dmg) + " dmg";
        } else {
            if (rand.nextInt(2) == 0) {
                return name + " tries to bite "
                        + target.getName() + " but is deflected";
            }
            return name + " tries " + target.getName() + " but misses";
        }
    }

    public String chargeAttack(Entity target, int ac) {
        if (counter == 1) {
            sp -= 3;
            counter = 0;
            int hit = rand.nextInt(20) + 1 + Entity.calcModifier(dex);
            if (hit >= ac) {
                int dmg = rand.nextInt(10) + 1 +Entity.calcModifier(str);
                attack(target, dmg);
                return name + " slams down on " + target.getName() + " for " + dmg + " dmg";
            } else {
                if (rand.nextInt(2) == 0) {
                    return name + " tries to slam down on " + target.getName() + " but they manage to dodge";
                } else {
                    return name + " tries to slam down on " + target.getName() + " but misses";
                }
            }
        } else {
            counter = 1;
            return name + " charges up";
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
        MagicGoblin goblin = new MagicGoblin("Goblin Conjurer", 7, 4);

        goblin.setArmorClass(9);
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

    public static Werewolf createWerewolf() {
        Werewolf werewolf = new Werewolf("Werewolf", 15, 5);

        werewolf.setArmorClass(12);
        werewolf.setStr(15);
        werewolf.setDex(13);
        werewolf.setVit(14);
        werewolf.setInte(10);
        werewolf.setWis(11);
        werewolf.setSp(12);

        werewolf.setXp(28);
        werewolf.setImg(R.drawable.werewolf);

        return werewolf;
    }

    public static Golem createGolem() {
        Golem golem = new Golem("Guardian Golem", 40, 10);

        golem.setArmorClass(14);
        golem.setStr(18);
        golem.setDex(8);
        golem.setVit(20);
        golem.setInte(3);
        golem.setWis(11);
        golem.setSp(5);

        golem.setXp(45);
        golem.setImg(R.drawable.golem);

        return golem;
    }

    public static DarkCultist createDarkCultist() {
        DarkCultist darkCultist = new DarkCultist("Dark Cultist", 20, 10);

        darkCultist.setArmorClass(12);
        darkCultist.setStr(11);
        darkCultist.setDex(14);
        darkCultist.setVit(8);
        darkCultist.setInte(10);
        darkCultist.setWis(10);
        darkCultist.setSpd(13);

        darkCultist.setXp(53);
        darkCultist.setImg(R.drawable.cultist_elf);

        return darkCultist;
    }

    public static Djinn createDjinn() {
        Djinn djinn = new Djinn("Storm Djinn", 30, 10);

        djinn.setArmorClass(12);
        djinn.setStr(8);
        djinn.setDex(10);
        djinn.setVit(8);
        djinn.setInte(14);
        djinn.setWis(15);
        djinn.setSpd(14);

        djinn.setXp(67);
        djinn.setImg(R.drawable.djinn);

        return djinn;
    }

    public static Dragon createDragon() {
        Dragon dragon = new Dragon("Abyssal Wyrm", 60, 15);

        dragon.setArmorClass(15);
        dragon.setStr(20);
        dragon.setDex(14);
        dragon.setVit(15);
        dragon.setInte(14);
        dragon.setWis(16);
        dragon.setSpd(13);

        dragon.setXp(100);
        dragon.setImg(R.drawable.dragon_dark);

        return dragon;
    }
}
