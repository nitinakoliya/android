package com.nitin.advancedfeatures;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivityBrainTrain extends AppCompatActivity {

    int rand1;
    int rand2;
    int correctAns;
    int totalAttempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_brain_train);

    }
}
