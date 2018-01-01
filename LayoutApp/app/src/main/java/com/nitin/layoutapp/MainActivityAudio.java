package com.nitin.layoutapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

public class MainActivityAudio extends AppCompatActivity {

    private MediaPlayer mPlayer;
    private AudioManager audioManager;
    private Handler seekHandler = new Handler();
    private SeekBar seekBar2;

    public void playSound(View view) {
        mPlayer.start();
    }

    public void pauseSound(View view) {
        mPlayer.pause();
    }

    Runnable run = new Runnable() {
        @Override public void run() {
            seekUpdation();
        }
    };

    public void seekUpdation() {
        seekBar2.setProgress(mPlayer.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_audio);

        mPlayer = MediaPlayer.create(this, R.raw.marsattacks);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumnControl = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);

        volumnControl.setMax(maxVolume);
        volumnControl.setProgress(curVolume);

        seekBar2.setMax(mPlayer.getDuration());
        seekUpdation();

        volumnControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Log.i("Progress", Integer.toString(i));

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
