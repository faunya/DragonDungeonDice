package edu.neu.madcourse.team20_finalproject.game.ingame.ability;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;

public abstract class Ability {
    protected String name;
    protected int diceType; //type is an int from 0 to 5 (0 for D4, 1 for D6, 2 for D8, 3 for D10, 4 for D12, 5 for D20)
    protected int stat;
    protected int cost;
    protected Entity user;

    public Ability(String name, int diceType, int stat, int cost) {
        this.name = name;
        this.diceType = diceType;
        this.stat = stat;
        this.cost = cost;
    }

    public void setUser(Entity user) {
        this.user = user;
    }

    /**
     * Use of ability
     * @param target
     * @param dmg
     */
    public abstract void use(Entity target, int dmg);

    public static List<Ability> genAbilList(Entity user) {
        List<Ability> abilList = new ArrayList<>();
        Slash slash = new Slash("Slash", 2, 0,2);
        slash.setUser(user);
        abilList.add(slash);
        return abilList;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        StringBuilder builder = new StringBuilder();
        builder.append(diceTypeToString() + " + " + statToString());
        return builder.toString();
    }

    public int getStat() {
        return stat;
    }

    public int getDiceType() {
        return diceType;
    }

    private String diceTypeToString() {
        switch (diceType) {
            case 0: //d4
                return "D4";
            case 1: //d6
                return "D6";
            case 2: //d8
                return "D8";
            case 3: //d10
                return "D10";
            case 4: //d12
                return "D12";
            case 5: //d20
            default:
                return "D20";
        }
    }

    private String statToString() {
        switch (stat) {
            case 0:
                return "Str";
            case 1:
                return "Dex";
            case 2:
                return "Vit";
            case 3:
                return "Int";
            case 4:
                return "Wis";
            case 5:
            default:
                return "Spd";
        }
    }

}
