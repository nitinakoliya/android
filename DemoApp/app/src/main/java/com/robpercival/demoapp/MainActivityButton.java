package com.robpercival.demoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivityButton extends AppCompatActivity {

    public void clickFunction(View view) {
        Log.i("Info", "Button Pressed!");
    }

    public void printCredentials(View view) {
        EditText username = (EditText) findViewById(R.id.editTextUsername);
        EditText password = (EditText) findViewById(R.id.editTextPassword);

        Log.i("Info", "Username : " + username.getText().toString());
        Log.i("Info", "Password : " + password.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_button);
    }
}
