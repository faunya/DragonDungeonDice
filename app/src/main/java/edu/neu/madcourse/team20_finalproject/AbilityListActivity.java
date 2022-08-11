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

import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.ingame.ability.Ability;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Player;
import edu.neu.madcourse.team20_finalproject.gameRecycler.AbilListAdapter;

public class AbilityListActivity extends AppCompatActivity implements AbilListAdapter.ItemClickListener {
    private static final String TYPE = "type";
    private static final String AC_REQUIREMENT = "ac";
    private static final String ROLL = "roll";
    private static final String FINISHED = "finished";
    private static final String ABILITY = "ability";

    private int selected;
    private Player player;
    private SharedPreferences sharedPref;

    private List<Ability> abilityList;
    private RecyclerView abilityRV;
    private AbilListAdapter abilityAdapter;

    protected int diceResult;
    protected boolean finishedRolling;
    protected ActivityResultLauncher rollResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_list);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        loadPlayer();

        abilityList = Ability.genAbilList(player);
        abilityAdapter = new AbilListAdapter(this, abilityList, this);
        abilityRV = findViewById(R.id.abilityList);
        abilityRV.setLayoutManager(new LinearLayoutManager(this));
        abilityRV.setHasFixedSize(true);
        abilityRV.setAdapter(abilityAdapter);

        finishedRolling = false;
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
    }

    @Override
    public void onItemClick(int pos) {
        selected = pos;
        finish();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra(ABILITY, selected);
        data.putExtra(FINISHED, true);
        setResult(RESULT_OK, data);
        super.finish();
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
}