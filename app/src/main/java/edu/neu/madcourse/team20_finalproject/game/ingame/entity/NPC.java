package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.neu.madcourse.team20_finalproject.R;

public class NPC extends Entity {
    protected Random rand;
    protected List<String> dialog;
    protected int id;
    protected int img;

    public NPC(String name, int maxHp, int maxMp) {
        super(name, maxHp, maxMp);
        rand = new Random();
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
        int hit = rand.nextInt(20) + 1 + Entity.calcModifier(dex);
        int dmg = rand.nextInt(6) + 1 + Entity.calcModifier(str);

        if (hit >= ac) { //if hits
            attack(target, dmg);
            return name + " attacks " + target.getName() + " for " + String.valueOf(dmg) + " dmg";
        }
        //if misses
        int textChoice = rand.nextInt(2);
        String text;
        if (textChoice == 0) {
            text = name + " swings at " + target.getName() + " but misses completely";
        } else {
            text = name + " attacks " + target.getName() + " but their armor deflects the blow";
        }
        return text;
    }

    public static SimpleEnemy createGoblin() {
        SimpleEnemy goblin = new SimpleEnemy("Goblin", 7, 3);
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

        goblin.setImg(R.drawable.m6);

        return goblin;
    }
}
