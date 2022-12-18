package com.good.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MusicPlayerActivity extends AppCompatActivity {

    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Intent intent = getIntent();
        String songName = intent.getStringExtra("MUSIC_PLAYING");

        TextView musicText = findViewById(R.id.musicText);
        musicText.setText(songName);
        MusicManager.AddTextToList(musicText);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    MusicManager.SeekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Handler hdl = new Handler();
        int delay = 1000;
        hdl.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setProgress(MusicManager.GetProgress(), true);
                    }
                    else {
                        seekBar.setProgress(MusicManager.GetProgress());
                    }
                    hdl.postDelayed(this, delay);
                } catch (Exception e) {
                    seekBar.setProgress(0);
                }
            }
        }, delay);

        // Buttons
        ImageButton pause = findViewById(R.id.PauseButton);
        pause.setOnClickListener(pauseListener);

        ImageButton next = findViewById(R.id.NextSong);
        next.setOnClickListener(view -> {
            MusicManager.NextSong();
        });

        ImageButton randomButton = findViewById(R.id.RandomButton);
        randomButton.setOnClickListener(view -> {
            MusicManager.random = !MusicManager.random;
            Toast.makeText(this, "Random is now " + MusicManager.random, Toast.LENGTH_SHORT).show();
        });
    }

    // Button callbacks
    View.OnClickListener pauseListener = view -> {
        if (MusicManager.isPaused()) {
            MusicManager.Resume();
        }
        else {
            MusicManager.Pause();
        }
    };
}