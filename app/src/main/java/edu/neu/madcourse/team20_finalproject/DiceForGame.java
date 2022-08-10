package edu.neu.madcourse.team20_finalproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.neu.madcourse.team20_finalproject.dice.DiceList;
import edu.neu.madcourse.team20_finalproject.dice.Die;
import pl.droidsonroids.gif.GifImageView;

/**
 * Takes dice type(key : type) and ac(key: ac)
 * type is an int from 0 to 5 (0 for D4, 1 for D6, 2 for D8, 3 for D10, 4 for D12, 5 for D20)
 * If ac doesn't exist or equals 0, roll once. Else roll twice.
 * If the first roll is less than ac, return 0.
 * If the activity finishes before the second roll, return the first roll.
 */
public class DiceForGame extends AppCompatActivity implements SensorEventListener {

    private static final int ROLLING_TIME = 2000;
    private static final String TYPE = "type";
    private static final String AC_REQUIREMENT = "ac";
    private static final String RETRY = "retry";
    private static final String REMAINS = "remains";
    private static final String ROLL = "roll";
    private static final String FINISHED = "finished";

    private boolean rolled;

    private Handler handler;
    private Thread diceRoller;
    private GifImageView gif;
    private TextView ACLabel;
    private TextView result;
    private DiceList diceList; // all 6 types of dices
    private Die die;
    private TextView status;
    private int type;
    private int ACValue;
    private int numberOfRolls;
    private int point;
    private String info;
    private boolean failed;

    private Sensor sensor;
    private SensorManager sensorManager;
    private static final int SHAKE_THRESHOLD = 15;
    private Long lastUpdatedTime;
    private static final int TIME_INTERVAL = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_for_game);

        rolled = false;
        handler = new Handler();
        diceList = new DiceList();
        gif = findViewById(R.id.d4g_display);
        result = findViewById(R.id.d4g_result);
        status = findViewById(R.id.d4g_status);
        ACLabel = findViewById(R.id.d4g_ACLabel);
        info = "";
        point = 0;


        Intent intent = getIntent();
        type = intent.getIntExtra(TYPE, 0);
        ACValue = intent.getIntExtra(AC_REQUIREMENT, 0);
        if (ACValue == 0) {
            numberOfRolls = 1;
        } else {
            numberOfRolls = 2;
            info = "AC: " + String.valueOf(ACValue);
        }
        updateValuesFromBundle(savedInstanceState);

        if (numberOfRolls > 0) {
            result.setText(R.string.start_rolling);
            ACLabel.setText(info);
        } else {
            result.setText(String.valueOf(point));
            ACLabel.setText("TAP TO EXIT");
        }

        if (ACValue != 0) {
            die = diceList.getDie(5);
        } else {
            die = diceList.getDie(type);
        }
        gif.setImageResource(die.getImgId());

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            type = savedInstanceState.getInt(TYPE);
            ACValue = savedInstanceState.getInt(AC_REQUIREMENT);
            if (ACValue != 0) {
                info = "AC: " + String.valueOf(ACValue);
            }
            if (savedInstanceState.containsKey(RETRY)) {
                numberOfRolls = savedInstanceState.getInt(REMAINS);
                info = "KEEP ROLLING";
            } else {
                numberOfRolls = ACValue == 0 ? 1 : 2;
            }
            if (savedInstanceState.containsKey(ROLL)) {
                point = savedInstanceState.getInt(ROLL);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RETRY, true);
        outState.putInt(REMAINS, numberOfRolls);
        outState.putInt(TYPE, type);
        outState.putInt(AC_REQUIREMENT, ACValue);
        outState.putInt(ROLL, point);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        terminateRolling();

        sensorManager.unregisterListener(this);
    }

    private void terminateRolling() {
        if (diceRoller != null) {
            diceRoller.interrupt();
            diceRoller = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if ((numberOfRolls == 0) || (lastUpdatedTime != null
                && System.currentTimeMillis() - lastUpdatedTime < TIME_INTERVAL)) {
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

    private void roll() {
        terminateRolling();
        result.setText("");
        status.setText("");
        gif.setImageResource(die.getRotateImgId());
        diceRoller = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ROLLING_TIME);
                    point = die.roll();
                    numberOfRolls -= 1;
                    rolled = true;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            gif.setImageResource(die.getImgId());
                            result.setText(String.valueOf(point));
                            if (numberOfRolls != 0) {
                                if (point >= ACValue) {
                                    status.setText("PASSED!");
                                    ACLabel.setText("KEEP ROLLING");
                                } else {
                                    status.setText("FAILED!");
                                    failed = true;
                                    numberOfRolls = 0;
                                }
                            }
                            if (numberOfRolls == 0) {
                                ACLabel.setText("TAP TO EXIT");
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    Log.d(TAG, "interrupted");
                }
            }
        };
        diceRoller.start();
    }

    public void backToGame(View view) {
        if (numberOfRolls != 0) return;
        finish();
    }

    public void tap(View view) {
        if (numberOfRolls != 0 && !rolled) {
            roll();
        } else if (numberOfRolls == 1 && rolled) {
            die = diceList.getDie(type);
            gif.setImageResource(die.getImgId());
            rolled = false;
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        if (failed) {
            data.putExtra(ROLL, 0);
            data.putExtra(FINISHED,true);
        } else {
            data.putExtra(ROLL, point);
        }
        setResult(RESULT_OK, data);
        super.finish();
    }

}