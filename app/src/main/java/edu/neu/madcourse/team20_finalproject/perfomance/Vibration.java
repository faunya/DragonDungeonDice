package edu.neu.madcourse.team20_finalproject.perfomance;

import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * A class with an attribute of a Vibrator object
 */
public class Vibration {

    private Vibrator vibrator;
    private static final int VIB_MILLISECONDS = 100;
    private static final int VIB_AMPLITUDE = 10;

    public Vibration(Context context) {
        vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
    }

    public void vibrate(boolean disabled) {
        if (disabled) return;
        vibrator.vibrate(VibrationEffect.createOneShot(VIB_MILLISECONDS,VIB_AMPLITUDE));
    }
}
