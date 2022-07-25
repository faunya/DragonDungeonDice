package edu.neu.madcourse.team20_finalproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import edu.neu.madcourse.team20_finalproject.dice.*;
import edu.neu.madcourse.team20_finalproject.perfomance.Sound;
import edu.neu.madcourse.team20_finalproject.perfomance.Vibration;
import pl.droidsonroids.gif.GifImageView;

public class DiceRolling extends AppCompatActivity {

    private static final String SETTINGS = "settings";
    private static final String MUSIC = "music";
    private static final String SOUND_EFFECT = "soundEffect";
    private static final String VIBRATION = "vibration";
    private static final int ROLLING_TIME = 2000;

    private Sound bgm;
    private Sound se;
    private Sound rollingSound;
    private Vibration vb;
    private boolean muteBgm;
    private boolean muteSe;
    private boolean stopVb;

    private Handler handler;
    private GifImageView gif;
    private TextView point;
    private TextView result;
    private Die die = new D6();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_rolling);

        bgm = new Sound();
        se = new Sound();
        rollingSound = new Sound();
        vb = new Vibration(this);

        handler = new Handler();
        gif = findViewById(R.id.dice_display);
        point = findViewById(R.id.dice_point);
        result = findViewById(R.id.dice_result);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        muteBgm = sharedPreferences.getBoolean(MUSIC, false);
        muteSe = sharedPreferences.getBoolean(SOUND_EFFECT, false);
        stopVb = sharedPreferences.getBoolean(VIBRATION, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bgm.stopSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rollingSound.stopSound();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        bgm.playSound(muteBgm, this, R.raw.dicerolling, true);
    }

    public void tap(View view) {
        roll();
    }

    private void roll() {
        result.setText("");
        rollingSound.playSound(muteSe, this, R.raw.dice_sound, false);
        gif.setImageResource(die.getRotateImgId());
        DiceRoller diceRoller = new DiceRoller();
        diceRoller.start();
    }

    private class DiceRoller extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(ROLLING_TIME);
            } catch (InterruptedException e) {
                Log.d(TAG, "interrupted");
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    gif.setImageResource(die.getImgId());
                    String number = String.valueOf(die.roll());
                    result.setText(number);
                    point.setText(number);
                }
            });
        }
    }
}