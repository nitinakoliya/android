package com.nitin.advancedfeatures;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivityShowHide extends AppCompatActivity {

    public void showEgg(View view) {
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setVisibility(View.VISIBLE);
    }

    public void hideEgg(View view) {
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_show_hide);



    }
}
