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
import edu.neu.madcourse.team20_finalproject.game.system.Message;
import edu.neu.madcourse.team20_finalproject.gameRecycler.ActLogViewAdapter;

public class GameActivity extends AppCompatActivity {
    private static final String TYPE = "type";
    private static final String AC_REQUIREMENT = "ac";
    private static final String ROLL = "roll";
    private static final String FINISHED = "finished";

    private SharedPreferences sharedPref;
    private List<Room> roomList;

    private ActivityResultLauncher rollResultLauncher;
    private ActLogViewAdapter actLogAdapter;
    private RecyclerView actLogRV;

    private List<Entity> turnList;
    private int turn;
    private boolean finRest;

    private List<Message> actLog;
    private Player player;
    private Room curRoom;

    //buttons
    private Button atkBtn;
    private Button abilBtn;
    private Button itmBtn;
    private Button blockBtn;
    private Button restBtn;

    //views
    private TextView enemyNameTV;
    private ProgressBar enemyHPBar;
    private TextView enemyHPNumTv;
    private TextView maxEnemyHPNumTV;
    private ProgressBar hpBar;
    private ProgressBar spBar;
    private TextView hpNumTV;
    private TextView maxHpNumTV;
    private TextView spNumTV;
    private TextView maxSpNumTV;

    private boolean finishedRolling;
    private int diceResult;
    private boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        paused = true;
        finRest = false;
        finishedRolling = false;
        diceResult = 0;

        rollResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        diceResult = data.getIntExtra(ROLL, 0);
                        finishedRolling = data.getBooleanExtra(FINISHED, true);
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

        //buttons
        atkBtn = findViewById(R.id.attackBtn);
        abilBtn = findViewById(R.id.ablBtn);
        itmBtn = findViewById(R.id.itemBtn);
        blockBtn = findViewById(R.id.runBtn);
        restBtn = findViewById(R.id.restBtn);

        //hp and sp views
        hpNumTV = findViewById(R.id.hpNum);
        maxHpNumTV = findViewById(R.id.maxHpNum);
        spNumTV = findViewById(R.id.spNum);
        maxSpNumTV = findViewById(R.id.maxSpNum);
        hpBar = findViewById(R.id.playerHpBar);
        spBar = findViewById(R.id.playerSpBar);
        enemyHPNumTv = findViewById(R.id.enemyHpNum);
        maxEnemyHPNumTV = findViewById(R.id.maxEnemyHpNum);
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

    //action buttons--------------------------------------------------------------------------------
    public void onAttack(View view) {
        if (turnList.size() == 0) {
            return;
        }

        if (turnList.get(turn).equals(player) && !paused) {
            Thread atkThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    player.setSp(player.getSp() + 1);
                    NPC enemy = curRoom.getNpcList().get(0);
                    int ac = enemy.getArmorClass() - Entity.calcModifier(player.getDex());
                    StringBuilder builder = new StringBuilder();

                    Intent intent = new Intent(getBaseContext(), DiceForGame.class);
                    intent.putExtra(TYPE, 2);
                    intent.putExtra(AC_REQUIREMENT, ac);
                    rollResultLauncher.launch(intent);

                    while (!finishedRolling) {
                        sleepThread(100);
                    }

                    int dmg = diceResult + Entity.calcModifier(player.getStr());
                    if (diceResult == 0) { //misses or dmg was 0
                        builder.append(player.getName() + "'s attack on " + enemy.getName() + " misses");
                    } else if (dmg <= 0) {
                        builder.append(player.getName() + "'s attack is deflected");
                    } else { //attack hits
                        builder.append(player.getName() + " attacked " + enemy.getName()
                                + " for " + dmg + "dmg");
                        player.attack(enemy, dmg);
                        updateEnemyHp(enemy.getHp());
                    }
                    finishedRolling = false;

                    actLog.add(new Message(System.currentTimeMillis(), builder.toString()));

                    actLogRV.post(new Runnable() {
                        @Override
                        public void run() {
                            actLogAdapter.notifyDataSetChanged();//notifyItemInserted(actLog.size() - 1);
                        }
                    });
                    actLogScroll();

                    nextTurn();
                }
            });
            atkThread.start();
        }
    }

    public void onAbility(View view) {
        if (turnList.size() == 0) {
            return;
        }
        if (turnList.get(turn).equals(player) && !paused) {

        }
    }

    public void onItem(View view) {
        if (turnList.size() == 0) {
            return;
        }
        if (turnList.get(turn).equals(player) && !paused) {

        }
        //makes small popup appear with list of items
    }

    public void onBlock(View view) {
        if (turnList.get(turn).equals(player) && !paused) {
            player.setBlocking(true);
            nextTurn();
        }

    }

    public void onRest(View view) {
        if (!paused) {
            Thread restThead = new Thread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder builder = new StringBuilder();

                    Intent intent = new Intent(getBaseContext(), DiceForGame.class);
                    intent.putExtra(TYPE, 4);
                    intent.putExtra(AC_REQUIREMENT, 0);
                    rollResultLauncher.launch(intent);

                    while (!finishedRolling) {
                        sleepThread(100);
                    }

                    int hp = diceResult + Entity.calcModifier(player.getVit());
                    player.heal(hp);
                    player.setSp(player.getMaxSp());

                    actLog.add(new Message(System.currentTimeMillis(),
                            "You rest for a bit and heal " + String.valueOf(hp) + " hp"));
                    finishedRolling = false;

                    setRestVisible(false);
                    finRest = true;
                    //nextRoom();
                }
            });
            restThead.start();
        }
    }

    private void setRestVisible(boolean visible) {
        restBtn.post(() -> {
            if (visible) {
                atkBtn.setVisibility(View.GONE);
                abilBtn.setVisibility(View.GONE);
                itmBtn.setVisibility(View.GONE);
                blockBtn.setVisibility(View.GONE);

                restBtn.setVisibility(View.VISIBLE);
            } else {
                restBtn.setVisibility(View.GONE);

                atkBtn.setVisibility(View.VISIBLE);
                abilBtn.setVisibility(View.VISIBLE);
                itmBtn.setVisibility(View.VISIBLE);
                blockBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    //save and load data----------------------------------------------------------------------------
    private void saveData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("turn", turn);
        editor.apply();

        saveRoom();
        savePlayer();
        saveEnemy();
    }

    private void loadData() {
        turn = sharedPref.getInt("turn", 0);
        loadPlayer();
        loadRoom();
    }

    private void savePlayer() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", player.getName());
        editor.putInt("pAc", player.getArmorClass());
        editor.putInt("maxHp", player.getMaxHp());
        editor.putInt("maxSp", player.getMaxSp());
        editor.putInt("hp", player.getHp());
        editor.putInt("sp", player.getSp());
        editor.putInt("str", player.getStr());
        editor.putInt("dex", player.getDex());
        editor.putInt("vit", player.getVit());
        editor.putInt("wis", player.getWis());
        editor.putInt("int", player.getInte());
        editor.putInt("spd", player.getSpd());
        editor.putBoolean("blocking", player.getBlocking());
        editor.putInt("xp", player.getXp());
        editor.putInt("lv", player.getLv());
        editor.apply();
    }

    private void loadPlayer() {
        String name = sharedPref.getString("name", "Player");
        int maxHp = sharedPref.getInt("maxHp", 10);
        int maxSp = sharedPref.getInt("maxSp", 5);
        int ac = sharedPref.getInt("pAc", 12);
        int hp = sharedPref.getInt("hp", 10);
        int sp = sharedPref.getInt("sp", 5);
        int str = sharedPref.getInt("str", 1);
        int dex = sharedPref.getInt("dex", 1);
        int vit = sharedPref.getInt("vit", 1);
        int wis = sharedPref.getInt("wis", 1);
        int inte = sharedPref.getInt("int", 1);
        int spd = sharedPref.getInt("spd", 1);
        int xp = sharedPref.getInt("xp", 0);
        int lv = sharedPref.getInt("lv", 1);
        boolean blocking = sharedPref.getBoolean("blocking", false);

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
        player.setBlocking(blocking);
        player.setXp(xp);
        player.setLv(lv);
    }

    private void saveRoom() {
        Gson gson = new Gson();
        String descJSON = gson.toJson(curRoom.getDesc());
        //String npcJSOn = gson.toJson(curRoom.getNpcList());

        SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putString("roomNPC", npcJSOn);
        editor.putInt("roomNum", curRoom.getRoomNum());
        editor.putString("roomDesc", descJSON);
        editor.apply();
    }

    private void loadRoom() {
        Gson gson = new Gson();

        List<String> descList = gson.fromJson(sharedPref.getString("roomDesc", ""), new TypeToken<ArrayList<String>>() {
        }.getType());
        List<NPC> npcList = new ArrayList<>();
        npcList.add(loadEnemy());

        curRoom = new Room(npcList, descList);
        curRoom.setRoomNum(sharedPref.getInt("roomNum", 0));
    }

    private void saveEnemy() {
        NPC enemy = curRoom.getNpcList().get(0);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("eName", enemy.getName());
        editor.putInt("eAc", enemy.getArmorClass());
        editor.putInt("eMaxHp", enemy.getMaxHp());
        editor.putInt("eMaxSp", enemy.getMaxSp());
        editor.putInt("eHp", enemy.getHp());
        editor.putInt("eSp", enemy.getSp());
        editor.putInt("eStr", enemy.getStr());
        editor.putInt("eDex", enemy.getDex());
        editor.putInt("eVit", enemy.getVit());
        editor.putInt("eWis", enemy.getWis());
        editor.putInt("eInt", enemy.getInte());
        editor.putInt("eSpd", enemy.getSpd());
        editor.putInt("eXp", enemy.getXp());

        editor.putStringSet("eDialog", new HashSet<>(enemy.getDialog()));

        editor.apply();
    }

    private NPC loadEnemy() {
        String name = sharedPref.getString("eName", "Monster");
        int ac = sharedPref.getInt("eAc", 8);
        int maxHp = sharedPref.getInt("eMaxHp", 10);
        int maxSp = sharedPref.getInt("eMaxSp", 5);
        int hp = sharedPref.getInt("eHp", 10);
        int sp = sharedPref.getInt("eSp", 5);
        int str = sharedPref.getInt("eStr", 1);
        int dex = sharedPref.getInt("eDex", 1);
        int vit = sharedPref.getInt("eVit", 1);
        int wis = sharedPref.getInt("eWis", 1);
        int inte = sharedPref.getInt("eInt", 1);
        int spd = sharedPref.getInt("eSpd", 1);
        int xp = sharedPref.getInt("eXp", 0);

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
        enemy.setXp(xp);

        enemy.setDialog(new ArrayList<>(sharedPref.getStringSet("eDialog", new HashSet<>())));

        return enemy;
    }

    private void createPlayer() {
        Intent intent = getIntent();
        String pName = intent.getStringExtra("name");
        int maxHp = intent.getIntExtra("maxHp", 1);
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

    //----------------------------------------------------------------------------------------------

    private void notifyRoomChange() {
        paused = true;
        for (String desc : curRoom.getDesc()) {
            actLog.add(new Message(System.currentTimeMillis(), desc));
            actLogAdapter.notifyItemInserted(actLog.size() - 1);
            actLogScroll();
            sleepThread(1000);
        }
        paused = false;
    }

    private void nextRoom() {
        paused = true;
        turnList.clear();
        int curRoomNum = curRoom.getRoomNum();
        if (curRoomNum + 1 != roomList.size()) { //not last level

            curRoom = roomList.get(curRoomNum + 1);
            turnSetup();
            notifyRoomChange();
            saveData();
            paused = false;
            return;
        }

        ending();
    }

    private void ending() {
        paused = true;
        List<String> epilogue = Room.getEpilogue();
        for (String line : epilogue) {
            sleepThread(1000);
            actLog.add(new Message(System.currentTimeMillis(), line));
            actLogAdapter.notifyItemInserted(actLog.size() - 1);
            actLogScroll();
        }
    }

    private void turnSetup() {
        turnList = new ArrayList<>();
        if (curRoom.getNpcList().size() != 0) {
            Entity enemy = curRoom.getNpcList().get(0);

            updateEnemyName(enemy.getName());
            updateEnemyMaxHp(enemy.getMaxHp());
            updateEnemyHp(enemy.getHp());

            if (player.getSpd() > enemy.getSpd()) {
                turnList.add(player);
                turnList.add(enemy);
            } else {
                turnList.add(enemy);
                turnList.add(player);
            }
        }
    }
    //----------------------------------------------------------------------------------------------

    private void nextTurn() {
        int nxt = turn + 1;
        if (nxt >= turnList.size()) {
            turn = 0;
        } else {
            ++turn;
        }
        if (player.getBlocking() == true && turnList.get(turn).equals(player)) {
            player.setBlocking(false);
        }
    }

    private void actLogScroll() {
        actLogRV.post(() -> actLogRV.scrollToPosition(actLog.size() - 1));
    }

    //update hp, sp bars and enemy name-------------------------------------------------------------
    private void updateMaxHp(int maxHp) {
        hpBar.post(() -> hpBar.setMax(maxHp));
        maxHpNumTV.post(() -> maxHpNumTV.setText("/" + String.valueOf(maxHp)));
    }

    private void updateHP(int hp) {
        hpBar.post(() -> hpBar.setProgress(hp));
        hpNumTV.post(() -> hpNumTV.setText(String.valueOf(hp)));
    }

    private void updateMaxSp(int maxSp) {
        spBar.post(() -> spBar.setMax(maxSp));
        maxSpNumTV.post(() -> maxSpNumTV.setText("/" + String.valueOf(maxSp)));
    }

    private void updateSp(int sp) {
        spBar.post(() -> spBar.setProgress(sp));
        spNumTV.post(() -> spNumTV.setText(String.valueOf(sp)));
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
        enemyHPBar.post(() -> enemyHPBar.setMax(maxHp));
        maxEnemyHPNumTV.post(() -> maxEnemyHPNumTV.setText("/" + String.valueOf(maxHp)));
    }

    private void updateEnemyHp(int hp) {
        enemyHPBar.post(() -> enemyHPBar.setProgress(hp));
        enemyHPNumTv.post(() -> enemyHPNumTv.setText(String.valueOf(hp)));
    }
    //----------------------------------------------------------------------------------------------

    private void sleepThread(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private void addLog(Message message) {
        actLog.add(message);
        actLogRV.post(new Runnable() {
            @Override
            public void run() {
                actLogAdapter.notifyItemInserted(actLog.size() - 1);
                actLogScroll();
            }
        });
    }

    /**
     *
     */
    private class GameThread implements Runnable {

        @Override
        public void run() {
            roomList = Room.getLevels();
            if (sharedPref.getBoolean("firstStart", true)) {
                createPlayer();
                curRoom = Room.room1();

                SharedPreferences.Editor prefEdit = sharedPref.edit();
                prefEdit.putBoolean("firstStart", false);
                prefEdit.commit();

                saveData();
                turn = 0;
            } else {
                loadData();
            }

            turnSetup();
            updateMaxHp(player.getMaxHp());
            updateHP(player.getHp());
            updateMaxSp(player.getMaxSp());
            updateSp(player.getSp());


            notifyRoomChange();

            while (!paused) { //can change to variable so you can pause game later
                //System.out.println("running");
                entityAI();
            }
            while (paused) {
                System.out.println("paused");
                sleepThread(100);
            }
        }

        private void entityAI() {
            Entity eTurn = turnList.get(turn);

            if (!eTurn.equals(player)) {
                System.out.println("enemy turn");
                NPC npc = (NPC) eTurn;

                if (npc.isDead()) {//completed room
                    turnList.remove(npc);
                    turn = 0;

                    actLog.add(new Message(System.currentTimeMillis(), "Gained " + npc.getXp() + "xp"));
                    actLogAdapter.notifyItemInserted(actLog.size() - 1);
                    actLogScroll();
                    sleepThread(1000);

                    if (player.addXP(npc.getXp())) {
                        actLog.add(new Message(System.currentTimeMillis(), "Congrats! Your character is now level " + player.getLv()));
                        actLogAdapter.notifyItemInserted(actLog.size() - 1);
                        actLogScroll();

                        updateMaxHp(player.getMaxHp());
                        updateMaxSp(player.getMaxSp());
                        sleepThread(1000);
                    }
                    setRestVisible(true);
                    while (!finRest) {
                        sleepThread(100);
                    }
                    finRest = false;
                    nextRoom();
                    return;
                }

                sleepThread(1000);
                String text = npc.behavior(player, player.getArmorClass());
                updateHP(player.getHp());
                actLog.add(new Message(System.currentTimeMillis(), text));
                actLogAdapter.notifyItemInserted(actLog.size() - 1);
                actLogScroll();

                if (player.getBlocking()) {
                    sleepThread(1000);
                    actLog.add(new Message(System.currentTimeMillis(), player.getName() + " was blocking and takes reduced damage"));

                    actLogAdapter.notifyItemInserted(actLog.size() - 1);
                    actLogScroll();
                }

                nextTurn();
            }
        }
    }
}


