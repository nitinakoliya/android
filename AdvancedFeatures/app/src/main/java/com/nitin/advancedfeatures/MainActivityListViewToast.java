package com.nitin.advancedfeatures;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivityListViewToast extends AppCompatActivity {

    ArrayList<String> myFriends;
    ArrayAdapter<String> arrayAdapter;

    public void changeArray(View view) {
        myFriends.add("Sahil");
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_listview_toast);

        ListView listView = (ListView) findViewById(R.id.myListView);

        myFriends = new ArrayList<String>();

        myFriends.add("Dikshit");
        myFriends.add("Bhargav");
        myFriends.add("Rahul");
        myFriends.add("Pradipsinh");

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, myFriends);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.i("Info", myFriends.get(i));
                Toast.makeText(getApplicationContext(), "Hey! " + myFriends.get(i), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
