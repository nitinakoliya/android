package com.nitin.advancedfeatures;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivityTimesTable extends AppCompatActivity {

    ArrayList<String> timesArray;
    ArrayAdapter<String> arrayAdapter;
    SeekBar timesBar;

    public void updateArrayList(int mult) {

        if (mult > 0) {
            for (int k = 0; k < 10; k++) {
                timesArray.set(k, Integer.toString((k+1) * mult));
            }
            arrayAdapter.notifyDataSetChanged();
        }
        else {
            timesBar.setProgress(1);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_times_table);

        ListView listView = (ListView) findViewById(R.id.timesTableView);

        timesArray = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, timesArray);

        listView.setAdapter(arrayAdapter);

        timesBar = (SeekBar) findViewById(R.id.timerBar);
        timesBar.setMax(20);
        timesBar.setProgress(1);
        timesBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateArrayList(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
