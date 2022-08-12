package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import android.content.SharedPreferences;

import edu.neu.madcourse.team20_finalproject.game.ingame.item.Armor;
import edu.neu.madcourse.team20_finalproject.game.ingame.item.Weapon;

public abstract class Entity {
    protected boolean blocking;

    protected String name;

    //stats
    protected int armorClass;

    protected int maxHp;
    protected int hp; //health

    protected int maxSp;
    protected int sp; //mana

    protected int str;
    protected int dex;
    protected int vit;
    protected int inte;
    protected int wis;
    protected int spd;

    //equips
    protected Weapon main;
    protected Weapon offhand;

    protected Armor helmet;
    protected Armor chestplate;
    protected Armor leggings;
    protected Armor boots;

    public Entity(String name, int maxHp, int maxSp) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;

        this.maxSp = maxSp;
        this.sp = maxSp;

        this.blocking = false;
    }

    /**
     * @param target to attack
     * @param dmg    target takes
     */
    public void attack(Entity target, int dmg) {
        target.takeDmg(dmg);

    }

    public void takeDmg(int dmg) {
        if (dmg < 0) {
            dmg = 0;
        }
        if (blocking) {
            dmg = Math.floorDiv(dmg, 3);
        }
        hp -= dmg;
    }

    public void heal(int heal) {
        hp += heal;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    /**
     * @return if entity is dead
     */
    public boolean isDead() {
        return hp <= 0;
    }

    public static int calcModifier(int stat) {
        return Math.floorDiv(stat - 10, 2);
    }

    // -------------------------------setters and getters -----------------------------------------
    public void setBlocking(boolean block) {
        this.blocking = block;
    }

    public boolean getBlocking() {
        return this.blocking;
    }

    public String getName() {
        return name;
    }

    //stat setters and getters
    public void setArmorClass(int ac) {
        this.armorClass = ac;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getSp() {
        return sp;
    }

    public int getMaxSp() {
        return maxSp;
    }

    public int getStr() {
        return str;
    }

    public int getDex() {
        return dex;
    }

    public int getInte() {
        return inte;
    }

    public int getVit() {
        return vit;
    }

    public int getWis() {
        return wis;
    }

    public int getSpd() {
        return spd;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setMaxSp(int maxSp) {
        this.maxSp = maxSp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
    }

    public void setSp(int sp) {
        this.sp = sp;
        if (this.sp > maxSp) {
            this.sp = maxSp;
        } else if (sp < 0) {
            this.sp = 0;
        }
    }

    public void setStr(int str) {
        this.str = str;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public void setVit(int vit) {
        this.vit = vit;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public void setWis(int wis) {
        this.wis = wis;
    }

    public void setInte(int inte) {
        this.inte = inte;
    }

    //item getters and setters
    public void setMain(Weapon main) {
        this.main = main;
    }

    public void setOffhand(Weapon offhand) {
        this.offhand = offhand;
    }

    public void setHelmet(Armor helmet) {
        this.helmet = helmet;
    }

    public void setChestplate(Armor chestplate) {
        this.chestplate = chestplate;
    }

    public void setLeggings(Armor leggings) {
        this.leggings = leggings;
    }

    public void setBoots(Armor boots) {
        this.boots = boots;
    }

    public Weapon getMain() {
        return main;
    }

    public Weapon getOffhand() {
        return offhand;
    }

    public Armor getHelmet() {
        return helmet;
    }

    public Armor getChestplate() {
        return chestplate;
    }

    public Armor getLeggings() {
        return leggings;
    }

    public Armor getBoots() {
        return boots;
    }

    public int getStat(int stat) {
        switch (stat) {
            case 0:
                return str;
            case 1:
                return dex;
            case 2:
                return vit;
            case 3:
                return inte;
            case 4:
                return wis;
            case 5:
            default:
                return spd;
        }
    }
}
