package edu.neu.madcourse.team20_finalproject.game;

public abstract class Entity {
    protected String name;;
    //stats
    protected int maxHp;
    protected int hp; //health

    protected int maxMp;
    protected int mp; //mana

    public Entity(String name, int maxHp, int maxMp) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;

        this.maxMp = maxMp;
        this.mp = maxMp;
    }

    public void attack(Entity target) {
    }

    public void takeDmg(int dmg) {

    }
}
