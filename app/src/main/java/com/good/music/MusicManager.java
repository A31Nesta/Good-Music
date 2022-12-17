package com.good.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

public class MusicManager {
    // PUBLIC
    public static void PlayResource(Context context, int i) {
        if (mp != null) {
            mp.stop();
        }
        mp = MediaPlayer.create(context, i);
        mp.start();
        paused = false;
    }

    public static void PlayFile(Context context, String fileWithPath) {
        try {
            if (mp == null) {
                mp = new MediaPlayer();
                Log.w(null, "Created Media Player as new media player");
            } else {
                mp.reset();
            }

            mp.setDataSource(fileWithPath);
            mp.prepare();
            mp.start();
            paused = false;
        } catch (Exception e) {
            Log.e("FUCK", e.toString());
        }
    }

    public static void Pause() {
        if (mp.isPlaying()) {
            mp.pause();
            paused = true;
        }
    }
    public static void Resume() {
        mp.start();
        paused = false;
    }

    public static boolean isPaused() {
        return paused;
    }

    // PRIVATE
    private static MediaPlayer mp;
    private static boolean paused = false;
}
