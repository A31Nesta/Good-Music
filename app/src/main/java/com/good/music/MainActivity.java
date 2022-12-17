package com.good.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        // Get the LinearLayout from the activity_main.xml
        linearLayout = findViewById(R.id.musicLinearLayout);
        CreateBotan();
    }

    private void CreateBotan() {
        // Create 100 Buttons
        for (int i = 0; i < 100; i++) {
            botans.add(new Button(this));
            botans.get(i).setHeight(50);
            botans.get(i).setTag(i+1);
            botans.get(i).setText("Button " + (i+1));
            botans.get(i).setOnClickListener(clickButton);
            linearLayout.addView(botans.get(i));
        }
    }

    // The Click callback for the buttons.
    // It gets the tag from the button and pass it
    // to the intent when opening the Music Player Activity
    View.OnClickListener clickButton = view -> {
        Object tag = view.getTag();
        String songName = "Song " + tag.toString();

        Intent intent = new Intent(this, MusicPlayerActivity.class);
        intent.putExtra("MUSIC_PLAYING", songName);
        startActivity(intent);
    };
}