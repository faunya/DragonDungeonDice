package edu.neu.madcourse.team20_finalproject.game.ingame.ability;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.activity.result.ActivityResultLauncher;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;

public abstract class Ability {
    protected String name;
    protected int diceType; //type is an int from 0 to 5 (0 for D4, 1 for D6, 2 for D8, 3 for D10, 4 for D12, 5 for D20)
    protected int stat;
    protected Entity user;

    public Ability(String name, int diceType, int stat) {
        this.name = name;
        this.diceType = diceType;
        this.stat = stat;
    }

    public void setUser(Entity user) {
        this.user = user;
    }

    /**
     * Use of ability
     * @param target
     * @param ac
     * @return
     */
    public abstract String use(Entity target, int ac);

    public static List<Ability> genAbilList(Entity user) {
        List<Ability> abilList = new ArrayList<>();
        Slash slash = new Slash("Slash", 2, 0);
        slash.setUser(user);
        abilList.add(slash);
        return abilList;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        StringBuilder builder = new StringBuilder();
        builder.append(diceTypeToString() + " + " + statToString());
        return builder.toString();
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
