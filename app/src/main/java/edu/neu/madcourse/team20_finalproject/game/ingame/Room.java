package edu.neu.madcourse.team20_finalproject.game.ingame;

import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.system.Actions;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.NPC;

public class Room {
    private List<Actions> actions;
    private List<NPC> npcList;
    private String desc;

    public Room(List<NPC> npcList, List<Actions> actions, String desc) {
        this.npcList = npcList;
        this.actions = actions;
        this.desc = desc;
    }

    public List<Actions> getActions() {
        return actions;
    }

    public List<NPC> getNpcList() {
        return npcList;
    }

    public String getDesc() {
        return desc;
    }
}
