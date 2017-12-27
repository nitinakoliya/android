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

public class MainActivityCurrency extends AppCompatActivity {

    public void convert(View view) {
        Log.i("info", "Converter");
        EditText numberText = (EditText) findViewById(R.id.currencyText);
        Double number = Double.parseDouble(numberText.getText().toString());
        number = number * 65;

        Toast.makeText(MainActivityCurrency.this, number.toString() + " Rs", Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivityCurrency.this, String.format("%.2f", number) + " Rs", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main_currency);
    }

}
