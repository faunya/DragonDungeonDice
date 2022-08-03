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

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        logOnDays = sharedPreferences.getInt(LOG_ON_DAYS, 0);
        lastLogin = sharedPreferences.getString(LAST_LOGIN, "");
    }

    private void saveLoginDays() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LOG_ON_DAYS, logOnDays);
        editor.putString(LAST_LOGIN, lastLogin);
        editor.apply();
    }

    private void updateLoginDays() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String date = sdf.format(calendar.getTime());
        if (!lastLogin.equals(date)) {
            lastLogin = date;
            logOnDays += 1;
            Toast.makeText(this, "First Login Today!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLoginDays();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        updateLoginDays();
    }
}
