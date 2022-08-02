package edu.neu.madcourse.team20_finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;

public class CreatePlayerActivity extends AppCompatActivity {
    private ActivityResultLauncher rollResultLauncher;
    private String selected;
    private int diceResult;

    private ProgressBar hpBar;
    private ProgressBar spBar;

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

        rollResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        diceResult = data.getIntExtra("roll", 1);
                        setStat();
                    } else {

                    }
                });

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
        onRoll("Wis");
    }

    public void onSpd(View view) {
        onRoll("spd");
    }

    private void onRoll(String stat) {
        selected = stat;
        Intent intent = new Intent();//(this, diceRollingScreen);
        intent.putExtra("stat", stat);
        rollResultLauncher.launch(intent);
    }

    private void setStat() {
        switch (selected) {
            case "str":
                strTV.setText(diceResult);
                return;
            case "dex":
                dexTV.setText(diceResult);
                return;
            case "vit":
                vitTV.setText(diceResult);
                hpTV.setText(Entity.calcModifier(diceResult) + 10);
                return;
            case "int":
                intTV.setText(diceResult);
                return;
            case "wis":
                wisTV.setText(diceResult);
                spTV.setText(Entity.calcModifier(diceResult) + 5);
                return;
            case "spd":
                spdTV.setText(diceResult);
                return;
        }
    }
}