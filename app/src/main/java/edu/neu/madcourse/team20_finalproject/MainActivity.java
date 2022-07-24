package edu.neu.madcourse.team20_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer bgm;
    private MediaPlayer se;
    private Vibrator vibrator;
    private boolean muteBgm;
    private boolean muteSe;
    private boolean stopVb;
    private static final int VIB_MILLISECONDS = 100;
    private static final int VIB_AMPLITUDE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        playBgm(muteBgm);
    }

    public void diceRolling(View view) {
        playSoundEffect(muteSe);
        vibrate(stopVb);
        Intent intent = new Intent(MainActivity.this, DiceRolling.class);
        startActivity(intent);
    }

    public void startGame(View view) {
        playSoundEffect(muteSe);
        vibrate(stopVb);
    }

    public void setting(View view) {
        playSoundEffect(muteSe);
        vibrate(stopVb);
    }

    private void playSoundEffect(boolean mute) {
        if (mute) return;
        if (se == null) {
            se = MediaPlayer.create(this, R.raw.click);
        }
        se.start();
        se.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                se.release();
                se = null;
            }
        });
    }

    private void stopBgm() {
        if (bgm != null) {
            bgm.release();
            bgm = null;
        }
    }

    private void playBgm(boolean mute) {
        if (mute) return;
        if (bgm == null) {
            bgm = MediaPlayer.create(this, R.raw.menu);
        }
        bgm.start();
        bgm.setLooping(true);
    }

    private void vibrate(boolean disabled) {
        if (disabled) return;
        vibrator.vibrate(VibrationEffect.createOneShot(VIB_MILLISECONDS,VIB_AMPLITUDE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopBgm();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        playBgm(false);
    }
}