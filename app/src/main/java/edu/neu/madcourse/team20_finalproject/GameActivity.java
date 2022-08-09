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
import android.widget.ProgressBar;
import android.widget.TextView;

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

    //views
    private TextView enemyNameTV;
    private ProgressBar enemyHPBar;
    private ProgressBar hpBar;
    private ProgressBar spBar;

    private int diceResult;
    private boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        paused = true;

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

        hpBar = findViewById(R.id.playerHpBar);
        spBar = findViewById(R.id.playerSpBar);
        enemyHPBar = findViewById(R.id.enemyHpBar);
        enemyNameTV = findViewById(R.id.enemyName);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        new Thread(new GameThread()).start();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        saveData();
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
            actLogAdapter.notifyItemInserted(actLog.size() - 1);

            nextTurn();
        }
    }

    public void onAbility(View view) {
        if (turnList.get(turn).equals(player) && !paused){

        }
    }

    public void onItem(View view) {
        if (turnList.get(turn).equals(player) && !paused) {

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
        String name = sharedPref.getString("eName", "Monster");
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
            actLogAdapter.notifyItemInserted(actLog.size() - 1);
            actLogScroll();
            sleepThread(500);
        }
        paused = false;
    }

    private void turnSetup() {
        turnList = new ArrayList<Entity>();
        Entity enemy = curRoom.getNpcList().get(0);

        if (player.getSpd() > enemy.getSpd()) {
            turnList.add(player);
            turnList.add(enemy);
        } else {
            turnList.add(enemy);
            turnList.add(player);
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

    private void actLogScroll() {
        actLogRV.post(new Runnable() {
            @Override
            public void run() {
                actLogRV.scrollToPosition(actLog.size() - 1);
            }
        });
    }

    private void updateMaxHp(int maxHp) {
        hpBar.post(new Runnable() {
            @Override
            public void run() {
                hpBar.setMax(maxHp);
            }
        });
    }

    private void updateHP(int hp) {
        hpBar.post(new Runnable() {
            @Override
            public void run() {
                hpBar.setProgress(hp);
            }
        });
    }

    private void updateEnemyName(String name) {
        enemyNameTV.post(new Runnable() {
            @Override
            public void run() {
                enemyNameTV.setText(name);
            }
        });
    }

    private void updateEnemyMaxHp(int maxHp) {
        enemyHPBar.post(new Runnable() {
            @Override
            public void run() {
                enemyHPBar.setMax(maxHp);
            }
        });
    }

    private void updateEnemyHp(int hp) {
        enemyHPBar.post(new Runnable() {
            @Override
            public void run() {
                enemyHPBar.setProgress(hp);
            }
        });
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
            }

            turnSetup();
            notifyRoomChange();

            while (!paused) { //can change to variable so you can pause game later
                Entity eTurn = turnList.get(turn);

                if (!eTurn.equals(player)) {

                    System.out.println("eturn " + eTurn);
                    NPC npc = (NPC) eTurn;

                    System.out.println("npc " + npc);
                    System.out.println(npc.getName());
                    System.out.println(player.getName());

                    if (npc.isDead()) {

                    }

                    sleepThread(500);
                    String text = npc.behavior(player, player.getArmorClass());
                    actLog.add(new Message(System.currentTimeMillis(), text));
                    actLogAdapter.notifyItemInserted(actLog.size() - 1);
                    actLogScroll();

                    nextTurn();
                }
            }
        }
    }
}


