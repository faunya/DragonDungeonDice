package edu.neu.madcourse.team20_finalproject.perfomance;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * A class with an attribute of a MediaPlayer object
 */
public class Sound {

    private MediaPlayer mp;

    private void createMp(Context context, int id, boolean loop) {
        mp = MediaPlayer.create(context, id);
        if (loop) {
            mp.setLooping(true);
        } else {
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopSound();
                }
            });
        }
    }

    public void stopSound() {
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    /**
     * Control MediaPlayer to play sound
     * @param mute true: mute the MediaPlayer; false: play sound
     * @param id resource id of the sound to play
     * @param loop true: keep playing after completion; false: play once
     */
    public void playSound(boolean mute, Context context, int id, boolean loop) {
        if (mute) {
            stopSound();
            return;
        }
        if (mp == null) {
            createMp(context, id, loop);
        }
        mp.start();
    }
}
