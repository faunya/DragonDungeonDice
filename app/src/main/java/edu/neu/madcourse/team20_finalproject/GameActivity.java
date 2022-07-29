package edu.neu.madcourse.team20_finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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

public class GameActivity extends AppCompatActivity {
    private ActivityResultLauncher resultLauncher;

    private boolean firstStart = true;

    private List<Message> actLog;
    private Player player;
    private Room curRoom;

    //buttons
    private Button act1;
    private Button act2;
    private Button act3;
    private Button act4;

    private int diceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result .getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        diceResult = data.getIntExtra("roll",1);
                    } else {

                    }
                });
        if (firstStart) {

        }

        act1 = findViewById(R.id.act1);
        act2 = findViewById(R.id.act2);
        act3 = findViewById(R.id.act3);
        act4 = findViewById(R.id.act4);

        actLog = new ArrayList<Message>();

        player = new Player("Test", 15, 0, 1);
        player.setArmorClass(12);

        List<NPC> npcList = new ArrayList<NPC>();
        npcList.add(new NPC("test enemy", 10, 0));
        List<Actions> actionsList = new ArrayList<>();
        actionsList.add(Actions.ATTACK);
        curRoom = new Room(npcList, actionsList, "Test room");


    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, String strdata, Bundle bundle) {

    }

 */

    public void onAttack(View view) {
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
                        + " for " + String.valueOf(dmg) + "dmg"));
    }

    private void actionSetup() {
        List<Actions> actList = curRoom.getActions();
        List<Button> buttonList = new ArrayList<>();
        buttonList.add(act1);
        buttonList.add(act2);
        buttonList.add(act3);
        buttonList.add(act4);

        for (int i = 0; i < actList.size(); i++) {
            buttonList.get(i).setVisibility(View.VISIBLE);

        }
    }

    private void notifyRoomChange() {
        actLog.add(new Message(System.currentTimeMillis(), curRoom.getDesc()));
    }


    public void onBlock(View view) {
    }

    public void onItem(View view) {
        //makes small popup appear with list of items
    }

    public void onRun(View view) {

    }

    public void onRest(View view) {

    }

    private class gameThread implements Runnable {

        @Override
        public void run() {
            while (true) { //can change to variable so you can pause game later

            }
        }
    }
}


