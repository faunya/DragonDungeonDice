package edu.neu.madcourse.team20_finalproject.game.ingame;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.system.Actions;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.NPC;

public class Room {
    private List<Actions> actions;
    private List<NPC> npcList;
    private List<String> desc;
    private int roomNum;

    public Room() {

    }

    public Room(List<NPC> npcList, List<String> desc) {
        this.npcList = npcList;
        this.desc = desc;
    }

    public Room(List<NPC> npcList, List<Actions> actions, List<String> desc) {
        this.npcList = npcList;
        this.actions = actions;
        this.desc = desc;
    }

    public String finishedRoom() {
        return "";
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public List<Actions> getActions() {
        return actions;
    }

    public List<NPC> getNpcList() {
        return npcList;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setNpcList(List<NPC> npcList) {
        this.npcList = npcList;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    public static List<Room> getLevels() {
        List<Room> roomList = new ArrayList<>();
        roomList.add(room1());
        roomList.add(room2());

        return roomList;
    }

    public static Room room1() {
        List<String> desc = new ArrayList<>();
        desc.add("You are an adventurer who has been doing quests for a while.");
        desc.add("Your latest quest has been to check out some ruins near the town.");
        desc.add("Some villagers have reported seeing goblins nearby in them.");
        desc.add("You are to check it out and clear out the goblin threat if necessary.");
        desc.add("");
        desc.add("You enter into a clearing in the forest. It is still plenty shaded from the trees but" +
                "light does make it through from some gaps.");
        desc.add("You hear a rustling from your right and a goblin jumps out at you.");
        desc.add("Goblin: Shineies! Gimmie shinies!");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createMeleeGoblin());
        Room room = new Room(npc, desc);
        room.setRoomNum(0);

        return room;
    }

    public static Room room2() {
        List<String> desc = new ArrayList<>();
        desc.add("You manage to strike down the goblin.");
        desc.add("If your few adventures had taught you anything, if there is one goblin, " +
                "there is a dozen more somewhere");
        desc.add("Your intuition proves correct as a second goblin comes out the brushes, " +
                "probably alerted by his kinsman's screams");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createMeleeGoblin());
        Room room = new Room(npc, desc);
        room.setRoomNum(1);

        return room;
    }

    public static Room room3() {
        List<String> desc = new ArrayList<>();
        desc.add("");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createMagicGoblin());
        Room room = new Room(npc, desc);
        room.setRoomNum(2);

        return room;
    }

    public static List<String> getEpilogue() {
        List<String> epilogue = new ArrayList<>();
        epilogue.add("the end");
        return epilogue;
    }
}


