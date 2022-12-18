package com.good.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MusicManager {
    // PUBLIC
    public static void Prepare() {
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                NextSong();
            }
        });
    }

    public static void AddTextToList(TextView textView) {
        texts.add(textView);
    }

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

    public static void PlaySong(Context context, Song song) {
        PlaySongMP(context, song, mp);
    }

    public static void SeekTo(int progress) {
        mp.seekTo((int)(((float)progress)/100.0f * (float)mp.getDuration()));
    }
    public static int GetProgress() {
        return (int)((float)mp.getCurrentPosition()/(float)mp.getDuration() * 100);
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

    public static void NextSong() {
        int songIndex = currentSong.getNumericID();

        if (random) {
            songIndex = new Random().nextInt(FileManager.songs.size());
        }
        else {
            songIndex++;
        }

        PlaySong(latestContext, FileManager.songs.get(songIndex));

        for (TextView text : texts) {
            text.setText(currentSong.getTitle());
        }
    }

    public static boolean isPaused() {
        return paused;
    }

    // PRIVATE
    private static void PlaySongMP(Context context, Song song, MediaPlayer mpl) {
        try {
            // TODO: Replace getPath with getID when IDs are implemented
            try {
                if (Objects.equals(song.getPath(), currentSong.getPath())) {
                    Log.i(null, "Selected song was already playing");
                    return;
                }
            } catch (Exception e) {
                Log.w("WHOOPS", "hehe");
            }

            if (mpl == null) {
                mpl = new MediaPlayer();
                Log.w(null, "Created Media Player as new media player");
            } else {
                mpl.reset();
            }

            mpl.setDataSource(song.getPath());
            mpl.prepare();
            mpl.start();
            paused = false;

            currentSong = song;
        } catch (Exception e) {
            Log.e("FUCK", e.toString());
        }
    }

    private static Context latestContext;
    private static MediaPlayer mp;
    private static boolean paused = false;
    public static boolean random = false;

    public static Song currentSong;
    private static ArrayList<TextView> texts = new ArrayList<>();
}
