package edu.neu.madcourse.team20_finalproject.game.ingame.entity;

import edu.neu.madcourse.team20_finalproject.game.ingame.item.Armor;
import edu.neu.madcourse.team20_finalproject.game.ingame.item.Weapon;

public abstract class Entity {
    protected String name;;

    //stats
    protected int maxHp;
    protected int hp; //health

    protected int maxMp;
    protected int mp; //mana

    //equips
    private Weapon main;
    private Weapon offhand;

    private Armor helmet;
    private Armor chestplate;
    private Armor leggings;
    private Armor boots;

    public Entity(String name, int maxHp, int maxMp) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;

        this.maxMp = maxMp;
        this.mp = maxMp;
    }

    /**
     * @param target to attack
     * @param dmg target takes
     */
    public void attack(Entity target, int dmg) {
        target.takeDmg(dmg);
    }

    public void takeDmg(int dmg) {
        hp -= dmg;
    }

    /**
     * @returnc if entity is dead
     */
    public boolean isDead() {
        return hp <= 0;
    }

    //item setters
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
}
