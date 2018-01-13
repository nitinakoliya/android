package com.example.nitinakoliya.mapsandintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public void goBack(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent thisIntent = getIntent();

        Toast.makeText(getApplicationContext(), thisIntent.getStringExtra("Name"), Toast.LENGTH_SHORT).show();
    }
}
