package com.nitin.advancedfeatures;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivityEggTimer extends AppCompatActivity {

    SeekBar timerBar;
    TextView timerText;
    MediaPlayer mPlayer;
    CountDownTimer countDownTimer;
    Button btn;


    public void startCountDownFunc() {
        int sec = timerBar.getProgress();
        countDownTimer = new CountDownTimer((long)sec * 1000, 1000) {
            public void onTick(long milisecondsUntillDone) {
                timerBar.setProgress(timerBar.getProgress() - 1);
            }
            public void onFinish() {
                mPlayer.start();
            }
        }.start();
    }

    public void buttonAction(View view) {
        Log.i("Tag", btn.getTag().toString());
        if (btn.getTag().toString().equals("0")) {
            startCountDownFunc();
            btn.setTag("1");
            btn.setText("Stop");
            timerBar.setEnabled(false);
        }
        else {
            countDownTimer.cancel();
            btn.setTag("0");
            btn.setText("Go!");
            timerBar.setEnabled(true);
            timerBar.setProgress(30);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_egg_timer);

        timerText = (TextView) findViewById(R.id.timerText);
        btn = (Button) findViewById(R.id.actionButton);
        timerBar = (SeekBar) findViewById(R.id.timerBar);
        timerBar.setMax(600);
        timerBar.setProgress(30);
        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int min = i / 60;
                int sec = i % 60;
                timerText.setText(min + ":" + sec);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mPlayer = MediaPlayer.create(MainActivityEggTimer.this, R.raw.airhorn);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btn.setTag("0");
                btn.setText("Go!");
                timerBar.setEnabled(true);
                timerBar.setProgress(30);
            }
        });

    }
}
