package com.example.nitinakoliya.mapsandintent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivityMemorable extends AppCompatActivity {

    static ArrayList<String> arrayList = new ArrayList<String>();
    static ArrayList<LatLng> locationList = new ArrayList<LatLng>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_memorable);

        ListView listView = (ListView) findViewById(R.id.listView1);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName(), MODE_PRIVATE);

        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        arrayList.clear();
        locationList.clear();

        if (sharedPreferences.getString("placeNames", "") == "") {
            arrayList.add("Add A Place...");
            locationList.add(new LatLng(0, 0));
        }
        else {
            try {
                arrayList = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("placeNames", ObjectSerializer.serialize(new ArrayList<String>())));
                latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes", ObjectSerializer.serialize(new ArrayList<String>())));
                longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes", ObjectSerializer.serialize(new ArrayList<LatLng>())));

                for (int i=0; i<latitudes.size(); i++) {
                    locationList.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(getApplicationContext(), MapsActivity.class);
                intent1.putExtra("index", position);
                startActivity(intent1);
            }
        });
    }
}
