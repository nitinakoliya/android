package com.nitin.layoutapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivityGrid extends AppCompatActivity {

    public void playPhrase(View view) {

        Button btn = (Button) view;

        //String phraseName = btn.getText().toString();
        int phraseId = getResources().getIdentifier(btn.getText().toString(), "raw", getPackageName());

        MediaPlayer mediaPlayer = MediaPlayer.create(this, phraseId);
        mediaPlayer.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
    }
}
