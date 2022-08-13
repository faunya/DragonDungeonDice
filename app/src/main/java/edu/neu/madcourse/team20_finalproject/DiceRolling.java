package edu.neu.madcourse.team20_finalproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import edu.neu.madcourse.team20_finalproject.dice.*;
import edu.neu.madcourse.team20_finalproject.perfomance.Sound;
import edu.neu.madcourse.team20_finalproject.perfomance.Vibration;
import pl.droidsonroids.gif.GifImageView;

public class DiceRolling extends AppCompatActivity implements SensorEventListener {

    // // keys in preferences
    private static final String SETTINGS = "settings";
    private static final String MUSIC = "music";
    private static final String SOUND_EFFECT = "soundEffect";
    private static final String VIBRATION = "vibration";
    private static final String BACKGROUND = "background";
    private static final String DIE_TYPE = "dieType";
    private static final String ROLL_TIMES = "rollingTimes";

    private static final int ROLLING_TIME = 2000;
    private static final int BLINK_TIME = 300;

    private ConstraintLayout layout;
    private Sound bgm;
    private Sound se;
    private Sound rollingSound;
    private Vibration vb;

    // values in preferences
    private boolean muteBgm;
    private boolean muteSe;
    private boolean stopVb;
    private int backgroundId;
    private int type;
    private int times;

    private Handler handler;
    private Thread blink;
    private Thread diceRoller;
    private GifImageView gif;
    private TextView point;
    private TextView result;
    private DiceList diceList; // all 6 types of dices
    private Die die;
    private TextView label;

    // sensor
    private Sensor sensor;
    private SensorManager sensorManager;
    private static final int SHAKE_THRESHOLD = 15;
    private Long lastUpdatedTime;
    private static final int TIME_INTERVAL = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_rolling);

        layout = findViewById(R.id.dice_layout);
        bgm = new Sound();
        se = new Sound();
        rollingSound = new Sound();
        vb = new Vibration(this);
        handler = new Handler();
        gif = findViewById(R.id.dice_display);
        point = findViewById(R.id.dice_point);
        result = findViewById(R.id.dice_result);
        label = findViewById(R.id.dice_dice);
        diceList = new DiceList();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        muteBgm = sharedPreferences.getBoolean(MUSIC, false);
        muteSe = sharedPreferences.getBoolean(SOUND_EFFECT, false);
        stopVb = sharedPreferences.getBoolean(VIBRATION, false);
        backgroundId = sharedPreferences.getInt(BACKGROUND, R.drawable.default_background);
        type = sharedPreferences.getInt(DIE_TYPE, 1);
        times = sharedPreferences.getInt(ROLL_TIMES, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bgm.stopSound();
    }

    // save the lasted used die type
    @Override
    protected void onPause() {
        super.onPause();
        terminateBlink();
        terminateRolling();
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DIE_TYPE, diceList.getCurrDie());
        editor.putInt(ROLL_TIMES, times);
        editor.apply();

        sensorManager.unregisterListener(this);
    }

    // load settings and set blinking effect
    @Override
    protected void onResume() {
        super.onResume();
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
        loadData();
        layout.setBackgroundResource(backgroundId);
        bgm.playSound(muteBgm, this, R.raw.dicerolling, true);
        result.setText(R.string.start_rolling);
        die = diceList.getDie(type);
        updateDie();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void tap(View view) {
        roll();
    }

    // terminate blinking and rolling
    // display gif for 2 seconds then show the point
    private void roll() {
        terminateBlink();
        terminateRolling();
        result.setVisibility(View.VISIBLE);
        result.setText("");
        vb.vibrate(stopVb);
        rollingSound.playSound(muteSe, this, R.raw.dice_sound, false);
        gif.setImageResource(die.getRotateImgId());
        diceRoller = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ROLLING_TIME);
                    String number = String.valueOf(die.roll());
                    vb.vibrate(stopVb);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            gif.setImageResource(die.getImgId());
                            result.setText(number);
                            point.setText(number);
                            times += 1;
                        }
                    });
                } catch (InterruptedException e) {
                    Log.d(TAG, "interrupted");
                }
            }
        };
        diceRoller.start();
    }

    private void terminateRolling() {
        point.setText("");
        if (diceRoller != null) {
            diceRoller.interrupt();
            rollingSound.stopSound();
            diceRoller = null;
        }
    }

    private void terminateBlink() {
        if (blink != null) {
            blink.interrupt();
            blink = null;
        }
    }

    private void updateDie() {
        terminateRolling();
        if (blink == null) {
            result.setText("");
        }
        gif.setImageResource(die.getImgId());
        label.setText(die.toString());
    }

    public void leftArrow(View view) {
        vb.vibrate(stopVb);
        se.playSound(muteSe, this, R.raw.click, false);
        die = diceList.getPrevious();
        updateDie();
    }

    public void rightArrow(View view) {
        vb.vibrate(stopVb);
        se.playSound(muteSe, this, R.raw.click, false);
        die = diceList.getNext();
        updateDie();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (lastUpdatedTime != null
                && System.currentTimeMillis() - lastUpdatedTime < TIME_INTERVAL) {
            return;
        }
        float x = Math.abs(event.values[0]);
        float y = Math.abs(event.values[1]);
        float z = Math.abs(event.values[2]);
        double currAccel = Math.sqrt((double) x * x + y * y + z * z);
        if (currAccel > SHAKE_THRESHOLD) {
            lastUpdatedTime = System.currentTimeMillis();
            roll();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void menu(View view) {
        vb.vibrate(stopVb);
        se.playSound(muteSe, this, R.raw.click, false);
        showMenu(view);
    }

    private void showMenu(View view) {
        Context context = new ContextThemeWrapper(DiceRolling.this, R.style.PopupMenu);
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                vb.vibrate(stopVb);
                se.playSound(muteSe, DiceRolling.this, R.raw.click, false);
                if (item.getItemId() == R.id.menu_background) {
                    Intent intent = new Intent(DiceRolling.this, SkinActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.menu_home) {
//                    Intent intent = new Intent(DiceRolling.this, MainActivity.class);
//                    startActivity(intent);
                    finish();
                } else if (item.getItemId() == R.id.menu_settings) {
                    Intent intent = new Intent(DiceRolling.this, Settings.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        popupMenu.show();
    }
}
