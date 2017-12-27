package com.robpercival.demoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by nitin on 26/12/17.
 */

public class MainActivityHigherLower extends AppCompatActivity {

    private Random rnd = new Random();
    private int randNumber;

    public void guess(View view) {
        Log.i("info", "guess");
        EditText numberText = (EditText) findViewById(R.id.numberGuess);
        int number = Integer.parseInt(numberText.getText().toString());

        String str = "Correct Guess!";
        if (number > this.randNumber) {
            str = "Lower";
        }
        else if (number < this.randNumber) {
            str = "Higher";
        }

        Toast.makeText(MainActivityHigherLower.this, str, Toast.LENGTH_SHORT).show();
    }

    public void thinkNewNumber(View view) {
        this.randNumber = rnd.nextInt(20) + 1;
        Toast.makeText(MainActivityHigherLower.this, "Thought a new number.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main_higher_lower);
        this.randNumber = rnd.nextInt(20) + 1;
    }

}
