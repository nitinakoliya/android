package com.example.nitinakoliya.permanentstorage;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivityLangPref extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView textView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.spanish:
                sharedPreferences.edit().putString("Lang", "Spanish").apply();
                textView.setText("Spanish");
                return true;
            case R.id.english:
                sharedPreferences.edit().putString("Lang", "English").apply();
                textView.setText("English");
                return true;
            default:
                return false;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lang_pref);
        sharedPreferences = getSharedPreferences("com.example.nitinakoliya.permanentstorage", MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.textView);

        if (sharedPreferences.getString("Lang", "").equals("")) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Choose a Language")
                    .setMessage("Which language whould you like?")
                    .setNeutralButton("Spanish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreferences.edit().putString("Lang", "Spanish").apply();
                            textView.setText("Spanish");
                        }
                    })
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreferences.edit().putString("Lang", "English").apply();
                            textView.setText("English");
                        }
                    })
                    .show();
        }
        else {
            textView.setText(sharedPreferences.getString("Lang", ""));
        }
    }
}
