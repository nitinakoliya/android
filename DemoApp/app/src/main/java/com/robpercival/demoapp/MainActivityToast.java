package com.robpercival.demoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by nitin on 26/12/17.
 */

public class MainActivityToast extends AppCompatActivity {

    public void showToast(View view) {

        EditText name = (EditText) findViewById(R.id.editTextName);

        Toast.makeText(MainActivityToast.this, "Hi! " + name.getText().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main_toast);
    }

}
