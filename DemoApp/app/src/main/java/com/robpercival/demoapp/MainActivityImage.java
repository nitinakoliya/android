package com.robpercival.demoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by nitin on 26/12/17.
 */

public class MainActivityImage extends AppCompatActivity {

    public void changeImage(View view) {
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.cat2);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main_image);
    }

}
