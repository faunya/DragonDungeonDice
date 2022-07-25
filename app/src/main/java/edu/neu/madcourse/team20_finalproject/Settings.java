package edu.neu.madcourse.team20_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.neu.madcourse.team20_finalproject.perfomance.Sound;
import edu.neu.madcourse.team20_finalproject.perfomance.Vibration;

public class Settings extends AppCompatActivity {

    private static final String SETTINGS = "settings";
    private static final String MUSIC = "music";
    private static final String SOUND_EFFECT = "soundEffect";
    private static final String VIBRATION = "vibration";

    private boolean music;
    private boolean soundEffect;
    private boolean vibration;

    private Sound se;
    private Vibration vb;

    private TextView musicSetting;
    private TextView seSetting;
    private TextView vbSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        musicSetting = findViewById(R.id.settings_statusM);
        seSetting = findViewById(R.id.settings_statusSe);
        vbSetting = findViewById(R.id.settings_statusV);

        se = new Sound();
        vb = new Vibration(this);

        loadData();
        update(musicSetting, music);
        update(seSetting, soundEffect);
        update(vbSetting, vibration);
    }

    public void saveData(View view) {
        se.playSound(soundEffect, this, R.raw.click, false);
        vb.vibrate(vibration);
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MUSIC, music);
        editor.putBoolean(SOUND_EFFECT, soundEffect);
        editor.putBoolean(VIBRATION, vibration);
        editor.apply();
        finish();
    }

    public void setMusic(View view) {
        se.playSound(soundEffect, this, R.raw.click, false);
        vb.vibrate(vibration);
        music = !music;
        update(musicSetting, music);
    }

    public void setSoundEffect(View view) {
        se.playSound(soundEffect, this, R.raw.click, false);
        vb.vibrate(vibration);
        soundEffect = !soundEffect;
        update(seSetting, soundEffect);
    }

    public void setVibration(View view) {
        se.playSound(soundEffect, this, R.raw.click, false);
        vb.vibrate(vibration);
        vibration = !vibration;
        update(vbSetting, vibration);
    }

    public void update(TextView textView, boolean bool) {
        if (bool) {
            textView.setText(R.string.off);
        } else {
            textView.setText(R.string.on);
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        music = sharedPreferences.getBoolean(MUSIC, false);
        soundEffect = sharedPreferences.getBoolean(SOUND_EFFECT, false);
        vibration = sharedPreferences.getBoolean(VIBRATION, false);
    }
}