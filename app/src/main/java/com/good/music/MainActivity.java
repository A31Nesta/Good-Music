package com.good.music;

import static com.good.music.FileManager.GetSongList;
import static com.good.music.FileManager.songs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Button> botans = new ArrayList<Button>();
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetSongList(this);
        MusicManager.Prepare();

        // Get the LinearLayout from the activity_main.xml
        linearLayout = findViewById(R.id.musicLinearLayout);
        ImageButton pause = findViewById(R.id.PauseMainMenu);
        ImageButton next = findViewById(R.id.NextSongMainMenu);
        TextView songName = findViewById(R.id.SongPlayingMainMenu);

        pause.setOnClickListener(view -> {
            if (MusicManager.isPaused()) {
                MusicManager.Resume();
            }
            else {
                MusicManager.Pause();
            }
        });

        songName.setOnClickListener(view -> {
            Intent intent = new Intent(this, MusicPlayerActivity.class);
            intent.putExtra("MUSIC_PLAYING", MusicManager.currentSong.getTitle());
//            intent.putExtra("MUSIC_PATH", "");
            startActivity(intent);
        });

        MusicManager.AddTextToList(songName);

        CreateBotan();
    }

    private void CreateBotan() {
        // Create 100 Buttons
        for (int i = 0; i < songs.size(); i++) {
            botans.add(new Button(this));
            botans.get(i).setHeight(50);
            botans.get(i).setTag(i);
            botans.get(i).setText(songs.get(i).getTitle());
            botans.get(i).setOnClickListener(clickButton);
            linearLayout.addView(botans.get(i));
        }
    }

    // The Click callback for the buttons.
    // It gets the tag from the button and pass it
    // to the intent when opening the Music Player Activity
    View.OnClickListener clickButton = view -> {
        Object tag = view.getTag();
        Song selectedSong = songs.get(Integer.parseInt(tag.toString()));
        String songName = selectedSong.getTitle();

        Intent intent = new Intent(this, MusicPlayerActivity.class);
        intent.putExtra("MUSIC_PLAYING", songName);

        MusicManager.PlaySong(getApplicationContext(), selectedSong);

        startActivity(intent);
        if (MusicManager.currentSong != null) {
            TextView text = findViewById(R.id.SongPlayingMainMenu);
            text.setText(MusicManager.currentSong.getTitle());
        }
    };
}