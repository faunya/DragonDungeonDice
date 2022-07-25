package edu.neu.madcourse.team20_finalproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final int BLINK_TIME = 300;

    private Sound bgm;
    private Sound se;
    private Sound rollingSound;
    private Vibration vb;
    private boolean muteBgm;
    private boolean muteSe;
    private boolean stopVb;

    private Handler handler;
    private Thread blink;
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
        blink.interrupt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        bgm.playSound(muteBgm, this, R.raw.dicerolling, true);
        point.setText("");
        result.setText(R.string.start_rolling);
        blink = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(BLINK_TIME);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (result.getVisibility() == View.VISIBLE) {
                                    result.setVisibility(View.INVISIBLE);
                                } else {
                                    result.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
        blink.start();
    }

    public void tap(View view) {
        roll();
    }

    private void roll() {
        blink.interrupt();
        vb.vibrate(stopVb);
        result.setVisibility(View.VISIBLE);
        result.setText("");
        rollingSound.playSound(muteSe, this, R.raw.dice_sound, false);
        gif.setImageResource(die.getRotateImgId());
        Thread diceRoller = new Thread() {
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
        };
        diceRoller.start();
    }

}