package edu.neu.madcourse.team20_finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;

public class CreatePlayerActivity extends AppCompatActivity {
    private ActivityResultLauncher rollResultLauncher;

    private String SELECTED_KEY = "selectedStat";
    private String selected;
    private int diceResult;

    private ProgressBar hpBar;
    private ProgressBar spBar;

    private EditText charNameTV;

    private TextView hpTV;
    private TextView spTV;
    private TextView strTV;
    private TextView dexTV;
    private TextView vitTV;
    private TextView intTV;
    private TextView wisTV;
    private TextView spdTV;

    private Button strBtn;
    private Button dexBtn;
    private Button vitBtn;
    private Button wisBtn;
    private Button intBtn;
    private Button spdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        if (savedInstanceState != null) {
            savedInstanceState.getString(SELECTED_KEY);
        }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("firstStart", true);
        editor.commit();

        rollResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        diceResult = data.getIntExtra("roll", 1);
                        setStat();
                    } else {

                    }
                });

        charNameTV = findViewById(R.id.createCharName);

        hpBar = findViewById(R.id.createHPBar);
        spBar = findViewById(R.id.createSPBar);

        hpTV = findViewById(R.id.createHPStat);
        spTV = findViewById(R.id.createSPStat);

        strTV = findViewById(R.id.createStrStat);
        dexTV = findViewById(R.id.createDexStat);
        vitTV = findViewById(R.id.createVitStat);
        intTV = findViewById(R.id.createIntStat);
        wisTV = findViewById(R.id.createWisStat);
        spdTV = findViewById(R.id.createSpdStat);

        strBtn = findViewById(R.id.createStrBtn);
        dexBtn = findViewById(R.id.createDexBtn);
        vitBtn = findViewById(R.id.createVitBtn);
        intBtn = findViewById(R.id.createIntBtn);
        wisBtn = findViewById(R.id.createWisBtn);
        spdBtn = findViewById(R.id.createSpdBtn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECTED_KEY, selected);
    }

    public void onFinish(View view) {
        if (strTV.getText().toString().equals("-") || dexTV.getText().toString().equals("-")
                || vitTV.getText().toString().equals("-") || wisTV.getText().toString().equals("-")
                || intTV.getText().toString().equals("-") || spdTV.getText().toString().equals("-")
                || charNameTV.getText().toString().equals("")) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.charCreationScreen),
                    "Please roll for all stats and name your character", BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        } else {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("maxHp", Integer.parseInt(hpTV.getText().toString()));
            intent.putExtra("maxSp", Integer.parseInt(spTV.getText().toString()));
            intent.putExtra("str", Integer.parseInt(strTV.getText().toString()));
            intent.putExtra("dex", Integer.parseInt(dexTV.getText().toString()));
            intent.putExtra("vit", Integer.parseInt(vitTV.getText().toString()));
            intent.putExtra("wis", Integer.parseInt(wisTV.getText().toString()));
            intent.putExtra("int", Integer.parseInt(intTV.getText().toString()));
            intent.putExtra("spd", Integer.parseInt(spdTV.getText().toString()));
            intent.putExtra("name", charNameTV.getText().toString());
            startActivity(intent);
        }
    }

    public void onStr(View view) {
        onRoll("str");
    }

    public void onDex(View view) {
        onRoll("dex");
    }

    public void onVit(View view) {
        onRoll("vit");
    }

    public void onInt(View view) {
        onRoll("int");
    }

    public void onWis(View view) {
        onRoll("wis");
    }

    public void onSpd(View view) {
        onRoll("spd");
    }

    private void onRoll(String stat) {
        selected = stat;

        //diceResult = 10;

        Intent intent = new Intent(this, DiceForGame.class);
        intent.putExtra("type", 5);
        intent.putExtra("ac", 0);
        rollResultLauncher.launch(intent);



        setStat();
    }

    private void setStat() {
        switch (selected) {
            case "str":
                strTV.setText(String.valueOf(diceResult));
                return;
            case "dex":
                dexTV.setText(String.valueOf(diceResult));
                return;
            case "vit":
                vitTV.setText(String.valueOf(diceResult));
                hpTV.setText(String.valueOf(Entity.calcModifier(diceResult) + 20));
                return;
            case "int":
                intTV.setText(String.valueOf(diceResult));
                return;
            case "wis":
                wisTV.setText(String.valueOf(diceResult));
                spTV.setText(String.valueOf(Entity.calcModifier(diceResult) + 10));
                return;
            case "spd":
                spdTV.setText(String.valueOf(diceResult));
                return;
        }
    }
}