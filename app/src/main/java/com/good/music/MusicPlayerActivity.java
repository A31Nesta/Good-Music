package com.good.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MusicPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Intent intent = getIntent();
        String songName = intent.getStringExtra("MUSIC_PLAYING");

        TextView musicText = findViewById(R.id.musicText);

        if (Objects.equals(songName, "Song 1")) {
            MusicManager.PlayFile(getApplicationContext(), "/storage/C399-0667/Music/Yakuza 4 B-Sides/02. For Faith [Opening ver.].mp3");
            songName = "For Faith [Opening ver.]";
        } else if (Objects.equals(songName, "Song 2")) {
            MusicManager.PlayFile(getApplicationContext(), "/storage/emulated/0/Music/Inabakumori/ウエザーステーション/ラグトレイン.mp3");
            songName = "ラグトレイン";
        } else if (Objects.equals(songName, "Song 3")) {
            MusicManager.PlayFile(getApplicationContext(), "/storage/C399-0667/Music/Lost_Judgment[M]/Lost Judgment Original Soundtrack [MP3]/Disc 1/1.16 Vorarephilia.mp3");
            songName = "Vorarephilia";
        } else {
            Toast.makeText(this, "not a song lul", Toast.LENGTH_SHORT).show();
        }

        musicText.setText(songName);

        // Buttons
        ImageButton pause = findViewById(R.id.PauseButton);
        pause.setOnClickListener(pauseListener);
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