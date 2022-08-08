package edu.neu.madcourse.team20_finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.ingame.Room;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.NPC;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Player;
import edu.neu.madcourse.team20_finalproject.game.system.Actions;
import edu.neu.madcourse.team20_finalproject.game.system.Message;
import edu.neu.madcourse.team20_finalproject.gameRecycler.ActLogViewAdapter;

public class GameActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private int roomNum;

    private ActivityResultLauncher rollResultLauncher;
    private ActLogViewAdapter actLogAdapter;
    private RecyclerView actLogRV;

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
    private boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        paused = false;

        rollResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        diceResult = data.getIntExtra("roll", 1);
                    } else {
                        diceResult = 1;
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

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        new Thread(new GameThread()).start();
    }

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
        if (turnList.get(turn).equals(player) && !paused) {
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
                            + " for " + dmg + "dmg" ));
            System.out.println(actLog.size());
            actLogAdapter.notifyItemInserted(actLog.size() - 1);

            nextTurn();
        }
    }

    public void onAbility(View view) {
        if (turnList.get(turn).equals(player) && !paused){

        }
    }

    public void onItem(View view) {
        if (turnList.get(turn).equals(player) && !paused){

        }
        //makes small popup appear with list of items
    }

    public void onRun(View view) {
        if (turnList.get(turn).equals(player) && !paused){

        }

    }

    public void onRest(View view) {

    }

    private void saveData() {
        saveRoom();
        savePlayer();
        saveEnemy();
    }

    private void savePlayer() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name",player.getName());
        editor.putInt("pAc",player.getArmorClass());
        editor.putInt("maxHp",player.getMaxHp());
        editor.putInt("maxSp",player.getMaxSp());
        editor.putInt("hp",player.getHp());
        editor.putInt("sp",player.getSp());
        editor.putInt("str",player.getStr());
        editor.putInt("dex",player.getDex());
        editor.putInt("vit",player.getVit());
        editor.putInt("wis",player.getWis());
        editor.putInt("int",player.getInte());
        editor.putInt("spd",player.getSpd());
        editor.commit();
    }

    private void loadPlayer() {
        String name = sharedPref.getString("pName", "Player");
        int maxHp = sharedPref.getInt("maxHp",10);
        int maxSp = sharedPref.getInt("maxSp",5);
        int ac = sharedPref.getInt("pAc",12);
        int hp = sharedPref.getInt("hp",10);
        int sp = sharedPref.getInt("sp",5);
        int str = sharedPref.getInt("str",1);
        int dex = sharedPref.getInt("dex",1);
        int vit = sharedPref.getInt("vit",1);
        int wis = sharedPref.getInt("wis",1);
        int inte = sharedPref.getInt("int",1);
        int spd = sharedPref.getInt("spd",1);

        player = new Player(name, maxHp, maxSp, 1);
        player.setArmorClass(ac);
        player.setHp(hp);
        player.setSp(sp);
        player.setStr(str);
        player.setDex(dex);
        player.setVit(vit);
        player.setWis(wis);
        player.setInte(inte);
        player.setSpd(spd);
    }

    private void saveRoom() {
        Gson gson = new Gson();
        String descJSON = gson.toJson(curRoom.getDesc());
        //String npcJSOn = gson.toJson(curRoom.getNpcList());

        SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putString("roomNPC", npcJSOn);
        editor.putString("roomDesc", descJSON);
        editor.commit();
    }

    private void loadRoom() {
        Gson gson = new Gson();

        List<String> descList = gson.fromJson(sharedPref.getString("roomDesc",""), new TypeToken<ArrayList<String>>(){}.getType());
        List<NPC> npcList = new ArrayList<NPC>();
        npcList.add(loadEnemy());
        curRoom = new Room(npcList, descList);
    }

    private void saveEnemy() {
        NPC enemy = curRoom.getNpcList().get(0);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("eName",enemy.getName());
        editor.putInt("eAc",enemy.getArmorClass());
        editor.putInt("eMaxHp",enemy.getMaxHp());
        editor.putInt("eMaxSp",enemy.getMaxSp());
        editor.putInt("eHp",enemy.getHp());
        editor.putInt("eSp",enemy.getSp());
        editor.putInt("eStr",enemy.getStr());
        editor.putInt("eDex",enemy.getDex());
        editor.putInt("eVit",enemy.getVit());
        editor.putInt("eWis",enemy.getWis());
        editor.putInt("eInt",enemy.getInte());
        editor.putInt("eSpd",enemy.getSpd());

        editor.putStringSet("eDialog", new HashSet<String>(enemy.getDialog()));
    }

    private NPC loadEnemy() {
        String name = sharedPref.getString("pName", "Player");
        int ac = sharedPref.getInt("eAc",8);
        int maxHp = sharedPref.getInt("maxHp",10);
        int maxSp = sharedPref.getInt("maxSp",5);
        int hp = sharedPref.getInt("hp",10);
        int sp = sharedPref.getInt("sp",5);
        int str = sharedPref.getInt("str",1);
        int dex = sharedPref.getInt("dex",1);
        int vit = sharedPref.getInt("vit",1);
        int wis = sharedPref.getInt("wis",1);
        int inte = sharedPref.getInt("int",1);
        int spd = sharedPref.getInt("spd",1);

        NPC enemy = new NPC(name, maxHp, maxSp);
        enemy.setHp(hp);
        enemy.setSp(sp);
        enemy.setArmorClass(ac);
        enemy.setStr(str);
        enemy.setDex(dex);
        enemy.setVit(vit);
        enemy.setWis(wis);
        enemy.setInte(inte);
        enemy.setSpd(spd);

        enemy.setDialog(new ArrayList<String>(sharedPref.getStringSet("eDialog",new HashSet<>())));

        return enemy;
    }

    private void createPlayer() {
        Intent intent = getIntent();
        String pName = intent.getStringExtra("name");
        int maxHp = intent.getIntExtra("str", 1);
        int maxSp = intent.getIntExtra("maxSp", 1);
        int str = intent.getIntExtra("str", 1);
        int dex = intent.getIntExtra("dex", 1);
        int vit = intent.getIntExtra("vit", 1);
        int wis = intent.getIntExtra("wis", 1);
        int inte = intent.getIntExtra("int", 1);
        int spd = intent.getIntExtra("spd", 1);

        player = new Player(pName, maxHp, maxSp, 1);
        player.setArmorClass(12);
        player.setStr(str);
        player.setDex(dex);
        player.setVit(vit);
        player.setWis(wis);
        player.setInte(inte);
        player.setSpd(spd);
    }

    private void notifyRoomChange() {
        paused = true;
        for (String desc : curRoom.getDesc()) {
            actLog.add(new Message(System.currentTimeMillis(), desc));
            sleepThread(500);
        }
        paused = false;
    }

    private void turnSetup() {
        turnList = new ArrayList<Entity>();
        Entity enemy = curRoom.getNpcList().get(0);
        turnList.add(player);
        if (player.getSpd() > enemy.getSpd()) {
            turnList.add(player);
            turnList.add(enemy);
        } else {
            turnList.add(enemy);
            turnList.add(player);
        }
        /*
        for (Entity npc : curRoom.getNpcList()) {
            turnList.add(npc);
        }

         */
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
            if (sharedPref.getBoolean("firstStart", true)) {
                createPlayer();
                curRoom = Room.room1();

                SharedPreferences.Editor prefEdit = sharedPref.edit();
                prefEdit.putBoolean("firstStart", false);
                prefEdit.commit();

                saveData();
            } else {
                loadPlayer();
                loadRoom();
                notifyRoomChange();
            }

            turnSetup();
            notifyRoomChange();

            while (!paused) { //can change to variable so you can pause game later
                if (!turnList.get(turn).equals(player)) {
                    NPC npc = (NPC) turnList.get(turn);

                    if (npc.isDead()) {

                    }
                    sleepThread(500);
                    String text = npc.behavior(player, player.getArmorClass());
                    actLog.add(new Message(System.currentTimeMillis(), text));
                    actLogAdapter.notifyItemInserted(actLog.size() - 1);

                    nextTurn();
                }
            }
        }
    }
}


