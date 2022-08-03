package edu.neu.madcourse.team20_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;




public class TestActivity extends AppCompatActivity {

    // keys in preferences
    private static final String SETTINGS = "settings";
    private static final String MUSIC = "music";
    private static final String SOUND_EFFECT = "soundEffect";
    private static final String VIBRATION = "vibration";
    private static final String LOG_ON_DAYS = "logOnDays";
    private static final String LAST_LOGIN = "lastLogin";


    private int logOnDays;
    private String lastLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LOG_ON_DAYS, 30);
        int days = sharedPreferences.getInt(LOG_ON_DAYS,0);
        System.out.println("days:" + days);
        editor.apply();
    }
}
