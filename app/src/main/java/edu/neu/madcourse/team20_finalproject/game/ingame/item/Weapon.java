package edu.neu.madcourse.team20_finalproject.game.ingame.item;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Weapon {
    private String name;
    private int dmg; //dice rolled for dmg
    private int matBonus; //dice
    private int rolls; //d

    public Weapon(String name, int dmg, int rolls, int matBonus) {
        this.name = name;
        this.dmg = dmg;
        this.rolls = rolls;
    }

    public Weapon(String name, String type) {
        this.name = name;
        genDmg(type);
        //genBonus(mat);
    }

    private void genDmg(String type) {
        this.rolls = 1;
        switch (type) {
            case "Club":
            case "Dagger":
                this.dmg = 4;
                return;

            case "Mace":
            case "Spear":
            case "Javelin":
            case "Arming Sword":
            case "Saber":
            case "Hatchet":
                this.dmg =  6;
                return;

            case "Greatsword":
                this.dmg =  6;
                this.rolls = 2;
                return;

            case "Longsword":
            case "Lance":
            case "Rapier":
            case "Pole Axe":
                this.dmg = 8;
                return;
        }
    }

    private void genBonus(String mat) {
        switch (mat) {
            case "Wooden":
            case "Copper":
                this.matBonus = 0;
                return;
            case "Iron":
                this.matBonus = 1;
                return;
            case "Steel":
                this.matBonus = 2;
                return;
            case "Mithral":
                this.matBonus = 3;
                return;
        }
    }
}
