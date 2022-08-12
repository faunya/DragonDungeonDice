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
        roomList.add(room0());
        roomList.add(room1());
        roomList.add(room2());
        roomList.add(room3());
        roomList.add(room4());
        roomList.add(room5());
        roomList.add(room6());
        roomList.add(room7());
        roomList.add(room8());
        roomList.add(room9());

        return roomList;
    }

    public static Room room0() {
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

    public static Room room1() {
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

    public static Room room2() {
        List<String> desc = new ArrayList<>();
        desc.add("You continue on deeper into the forest.");
        desc.add("After traveling a bit, you stop to take a quick rest and a drink.");
        desc.add("A fireball shoots past you, nearly hitting your head.");
        desc.add("You turn around and there stand a goblin in a tattered cloak, " +
                "in the middle conjuring a second fireball");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createMagicGoblin());
        Room room = new Room(npc, desc);
        room.setRoomNum(2);

        return room;
    }

    public static Room room3() {
        List<String> desc = new ArrayList<>();
        desc.add("You slay the goblin conjurer.");
        desc.add("'A goblin that can use magic? Quite usual,' you think to youself.");
        desc.add("Something seems off about that.");
        desc.add("Goblins are too stupid and have a low affinity for traditional magic like the conjurer was using.");
        desc.add("You continue on towards the ruins in order to investigate what may be the cause.");
        desc.add("");
        desc.add("You can see the ruins in the distance before you see two flashes in the corner of your eye.");
        desc.add("Ducking, you manage to dodge two fireballs that were aimed at you.");
        desc.add("You turn around  and there stands another goblin conjurer.");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createMagicGoblin());
        Room room = new Room(npc, desc);
        room.setRoomNum(3);

        return room;
    }

    public static Room room4() {
        List<String> desc = new ArrayList<>();
        desc.add("As you continue on towards the ruins, a dark shadow leaps out at you.");
        desc.add("You jump back in surprise to see it's a werewolf.");
        desc.add("A werewolf this close to the goblin nest? Incredibly strange.");
        desc.add("Snarling at you, it charges again, swinging its weapons.");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createWerewolf());
        Room room = new Room(npc, desc);
        room.setRoomNum(4);
        return room;
    }

    public static Room room5() {
        List<String> desc = new ArrayList<>();
        desc.add("The werewolf is at its last breath, glaring at you.");
        desc.add("Before you can strike it down, it howls, the noice echoing through the trees.");
        desc.add("The distant sound of something running gets louder.");
        desc.add("Another werewolf leaps at you.");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createWerewolf());
        Room room = new Room(npc, desc);
        room.setRoomNum(5);
        return room;
    }

    public static Room room6() {
        List<String> desc = new ArrayList<>();
        desc.add("Werewolves and goblins together this close is strange.");
        desc.add("Something may be gathering them together for some purpose");
        desc.add("You continue to the ruins and finally arrive.");
        desc.add("There appears to be an opening in the ground further in the ruins.");
        desc.add("As you move towards it, you feel the ground start shaking.");
        desc.add("A guardian golem which was hidden by rubble gets up and begins to approach you.");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createGolem());
        Room room = new Room(npc, desc);
        room.setRoomNum(6);
        return room;
    }

    public static Room room7() {
        List<String> desc = new ArrayList<>();
        desc.add("The golem collapses after you defeat it.");
        desc.add("You push forward and enter into the opening in the floor, stairs leading down.");
        desc.add("It continues into a tunnel, dark and damp");
        desc.add("You don't make it very far before you hear a voice behind you.");
        desc.add("???: You made a mistake coming down here.");
        desc.add("You turn around to see an elf cultist.");
        desc.add("Dark Cultist: Unfortunately, you will have to die.");
        desc.add("Dark Cultist: A sacrifice for my god.");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createDarkCultist());
        Room room = new Room(npc, desc);
        room.setRoomNum(7);
        return room;
    }

    public static Room room8() {
        List<String> desc = new ArrayList<>();
        desc.add("Dark Cultist: Ugh...");
        desc.add("Dark Cultist: You may have defeated me but it is too late.");
        desc.add("Dark Cultist: My god is already being summoned");
        desc.add("The elf steps backwards and disappears into the darkness");
        desc.add("You move further in the tunnel and enter into a large, domed room with a high ceiling.");
        desc.add("There is an opening in the ceiling from which you can see the sky.");
        desc.add("There is what looks like a conjuration circle on the center directly below the opening.");
        desc.add("Another opening to a tunnel is on the other side of the room.");
        desc.add("As you move forward, storm clouds quickly gather and swirl in the sky and lightning strikes down on the circle.");
        desc.add("A djinn appears.");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createDjinn());
        Room room = new Room(npc, desc);
        room.setRoomNum(8);
        return room;
    }

    public static Room room9() {
        List<String> desc = new ArrayList<>();
        desc.add("The djinn begins floating upwards slowly and beams of light erupt from its body");
        desc.add("Before it continue any further, a black dragon shoots out from the tunnel opposite of you.");
        desc.add("It swallows the djinn whole before turning towards you.");
        desc.add("The dark cultist limps out of the tunnel you came from.");
        desc.add("Dark Cultist: It's over for you... Praise to the abyss...");

        List<NPC> npc = new ArrayList<>();
        npc.add(NPC.createDragon());
        Room room = new Room(npc, desc);
        room.setRoomNum(9);
        return room;
    }

    public static List<String> getEpilogue() {
        List<String> epilogue = new ArrayList<>();
        epilogue.add("You manage to defeat the Abyssal Wyrm the cultist was trying to summon.");
        epilogue.add("Returning to the town, you report the news and are celebrated as a hero.");
        epilogue.add("The Adventurer's Guild promotes you and your tale becomes a local legend.");
        epilogue.add("");
        epilogue.add("Congrats! You finished the game.");
        epilogue.add("This is not the end though. Future adventures await.");
        return epilogue;
    }
}


