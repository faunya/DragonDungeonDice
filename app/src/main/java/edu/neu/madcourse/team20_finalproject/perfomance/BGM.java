package edu.neu.madcourse.team20_finalproject.perfomance;

import android.content.Context;
import android.media.MediaPlayer;

public abstract class BGM {

    private static MediaPlayer mp;
    private static int count;

    private static void stop() {
        count = 0;
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    public static void checkMute(boolean mute, Context context, int id) {
        if (mute) {
            stop();
        } else {
            if (mp == null) {
                playBGM(mute, context, id);
            }
        }
    }

    public static void playBGM(boolean mute, Context context, int id) {
        if (mute) {
            stop();
            return;
        }
        count++;
        if (count == 1) {
            mp = MediaPlayer.create(context, id);
            mp.setLooping(true);
            mp.start();
        }
    }

    public static void stopBGM() {
        if (count > 0) {
            count--;
        }
        if (count == 0) {
            stop();
        }
    }
}
