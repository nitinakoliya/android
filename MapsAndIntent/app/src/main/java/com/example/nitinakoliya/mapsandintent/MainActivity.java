package com.example.nitinakoliya.mapsandintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public void goToSecondActivity(View view) {

        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("Name", "Nitin");
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Nitin");
        arrayList.add("DJ");
        arrayList.add("Bhargav");
        arrayList.add("Krunal");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("Name", arrayList.get(position));
                startActivity(intent);
            }
        });

    }
}
