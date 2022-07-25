package edu.neu.madcourse.team20_finalproject.game.ingame.item;

import edu.neu.madcourse.team20_finalproject.game.system.ArmorType;

public class Armor {
    private ArmorType type;
    private String name;
    private int stat;

    public Armor(ArmorType type, String name, int stat) {
        this.type = type;
        this.name = name;
        this.stat = stat;
    }

    public ArmorType getType() {
        return type;
    }

    public int getStat() {
        return stat;
    }

    public String getName() {
        return name;
    }
}
