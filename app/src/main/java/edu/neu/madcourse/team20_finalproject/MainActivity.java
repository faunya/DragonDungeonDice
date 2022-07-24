package edu.neu.madcourse.team20_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.neu.madcourse.team20_finalproject.perfomance.Sound;
import edu.neu.madcourse.team20_finalproject.perfomance.Vibration;


public class MainActivity extends AppCompatActivity {

    private Sound bgm;
    private Sound se;
    private Vibration vb;
    private boolean muteBgm;
    private boolean muteSe;
    private boolean stopVb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgm = new Sound();
        se = new Sound();

        bgm.playSound(muteBgm, this, R.raw.menu, true);
        vb = new Vibration(this);
    }

    public void diceRolling(View view) {
        se.playSound(muteSe, this, R.raw.click, false);
        vb.vibrate(stopVb);
        Intent intent = new Intent(MainActivity.this, DiceRolling.class);
        startActivity(intent);
    }

    public void startGame(View view) {
        se.playSound(muteSe, this, R.raw.click, false);
        vb.vibrate(stopVb);
    }

    public void setting(View view) {
        se.playSound(muteSe, this, R.raw.click, false);
        vb.vibrate(stopVb);
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bgm.stopSound();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        bgm.playSound(muteBgm, this, R.raw.menu, true);
    }
}