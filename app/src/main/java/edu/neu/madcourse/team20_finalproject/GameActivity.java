package edu.neu.madcourse.team20_finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.ingame.Room;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.NPC;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Player;
import edu.neu.madcourse.team20_finalproject.game.system.Actions;
import edu.neu.madcourse.team20_finalproject.game.system.Message;
import edu.neu.madcourse.team20_finalproject.gameRecycler.ActLogViewAdapter;

public class GameActivity extends AppCompatActivity {
    private ActivityResultLauncher resultLauncher;
    private ActLogViewAdapter actLogAdapter;
    private RecyclerView actLogRV;

    private boolean firstStart;

    private List<Entity> turnList;
    private int turn;

    private List<Message> actLog;
    private Player player;
    private Room curRoom;

    //buttons
    private Button atkBtn;
    private Button abilBtn;
    private Button itmBtn;
    private Button runBtn;

    private int diceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        diceResult = data.getIntExtra("roll", 1);
                    } else {

                    }
                });

        //recycler view
        actLog = new ArrayList<Message>();
        actLogAdapter = new ActLogViewAdapter(this, actLog);
        actLogRV = findViewById(R.id.actionLog);
        actLogRV.setHasFixedSize(true);
        actLogRV.setLayoutManager(new LinearLayoutManager(this));
        actLogRV.setAdapter(actLogAdapter);

        atkBtn = findViewById(R.id.attackBtn);
        abilBtn = findViewById(R.id.ablBtn);
        itmBtn = findViewById(R.id.itemBtn);
        runBtn = findViewById(R.id.runBtn);

        player = new Player("Test", 15, 0, 1);
        player.setArmorClass(12);

        List<NPC> npcList = new ArrayList<NPC>();
        npcList.add(new NPC("test enemy", 10, 0));
        npcList.add(new NPC("test enemy2", 10, 0));
        List<Actions> actionsList = new ArrayList<>();
        actionsList.add(Actions.ATTACK);
        curRoom = new Room(npcList, actionsList, "Test room");

        turnSetup();

        new Thread(new GameThread()).start();
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, String strdata, Bundle bundle) {

    }

 */

    private void actionSetup() {
        List<Actions> actList = curRoom.getActions();
        List<Button> buttonList = new ArrayList<>();
        buttonList.add(atkBtn);
        buttonList.add(abilBtn);
        buttonList.add(itmBtn);
        buttonList.add(runBtn);

        for (int i = 0; i < actList.size(); i++) {
            buttonList.get(i).setVisibility(View.VISIBLE);
            switch (actList.get(i)) {
                case ATTACK:
                case ABILITY:
                case ITEM:
                case RUN:
                case REST:
            }
        }
    }

    public void onAttack(View view) {
        if (turnList.get(turn).equals(player)) {
            int dmg = 0;
            int ac = curRoom.getNpcList().get(0).getArmorClass();
            //Intent intent = new Intent(this, DiceRollScreen.activity);
        /*
        Sends ac to dice rolling screen
        if roll is equal to or above ac, then it rolls again for dmg
        returns dmg value here
         */
            player.attack(curRoom.getNpcList().get(0), dmg);
            actLog.add(new Message(System.currentTimeMillis(),
                    player.getName() + " attacked " + curRoom.getNpcList().get(0).getName()
                            + " for " + dmg + "dmg"));
            System.out.println(actLog.size());
            actLogAdapter.notifyItemInserted(actLog.size() - 1);

            nextTurn();
        }
    }

    public void onAbility(View view) {
    }

    public void onItem(View view) {
        //makes small popup appear with list of items
    }

    public void onRun(View view) {

    }

    public void onRest(View view) {

    }

    private void notifyRoomChange() {
        actLog.add(new Message(System.currentTimeMillis(), curRoom.getDesc()));
    }

    private void turnSetup() {
        turnList = new ArrayList<Entity>();
        turnList.add(player);
        for (Entity npc : curRoom.getNpcList()) {
            turnList.add(npc);
        }
    }

    private void nextTurn() {
        int nxt = turn + 1;
        if (nxt >= turnList.size()) {
            turn = 0;
        } else {
            ++turn;
        }
    }

    private void sleepThread(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private class GameThread implements Runnable {

        @Override
        public void run() {
            while (true) { //can change to variable so you can pause game later

                if (!turnList.get(turn).equals(player)) {
                    NPC npc = (NPC) turnList.get(turn);
                    sleepThread(500);
                    System.out.println(npc.getName() + " turn");
                    actLog.add(new Message(System.currentTimeMillis(), npc.getName() + " attacks"));
                    actLogAdapter.notifyItemInserted(actLog.size() - 1);
                    nextTurn();
                }
            }
        }
    }
}


