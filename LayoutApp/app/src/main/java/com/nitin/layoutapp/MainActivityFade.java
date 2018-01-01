package com.nitin.layoutapp;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivityFade extends AppCompatActivity {

    private ImageView image;

    public void fade(View view) {

        image = (ImageView) findViewById(R.id.imageView2);

        image.animate().alpha(0f).setDuration(2000);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                image.setImageResource(R.drawable.homer);
                image.animate().alpha(1f).setDuration(2000);
            }
        }, 2000);



    }

    public void setPng() {
        image.setImageResource(R.drawable.homer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fade);
    }
}
