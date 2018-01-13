package com.example.nitinakoliya.permanentstorage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivityNoteList extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayList<String> notesHeader = new ArrayList<>();
    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu1, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.addNote) {
            Intent intent = new Intent(getApplicationContext(), MainActivityNoteEdit.class);
            intent.putExtra("index", -1);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);


    }

    public void storeNotes() {
        try {
            sharedPreferences.edit().putString("Notes", ObjectSerializer.serialize(notes)).apply();
            sharedPreferences.edit().putString("NotesHeader", ObjectSerializer.serialize(notesHeader)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchNotes() {
        try {
            notes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Notes", ObjectSerializer.serialize(new ArrayList<String>())));
            notesHeader = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("NotesHeader", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note_list);

        listView = (ListView) findViewById(R.id.noteListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivityNoteEdit.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int index = position;
                new AlertDialog.Builder(MainActivityNoteList.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure, you want to delete note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(index);
                                notesHeader.remove(index);
                                storeNotes();
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

        sharedPreferences = getSharedPreferences("com.example.nitinakoliya.permanentstorage", MODE_PRIVATE);

        notes.clear();
        notesHeader.clear();
        if (sharedPreferences.getString("NotesHeader", "").equals("")) {
            notes.add("Example Note");
            notesHeader.add("Example Note");
            storeNotes();
        }
        else {
            fetchNotes();
        }
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, notesHeader);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }
}
