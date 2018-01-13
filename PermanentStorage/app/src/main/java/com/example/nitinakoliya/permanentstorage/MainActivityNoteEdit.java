package com.example.nitinakoliya.permanentstorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivityNoteEdit extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText editText;
    int index;

    @Override
    public void onBackPressed() {

        String header = editText.getText().toString().substring(0, Math.min(editText.getText().toString().length(), 25));
        header = header.replaceAll("\n", " ");

        if (index != -1) {
            MainActivityNoteList.notes.set(index, editText.getText().toString());
            MainActivityNoteList.notesHeader.set(index, header);
        }
        else {
            MainActivityNoteList.notes.add(editText.getText().toString());
            MainActivityNoteList.notesHeader.add(header);
        }
        try {
            sharedPreferences.edit().putString("Notes", ObjectSerializer.serialize(MainActivityNoteList.notes)).apply();
            sharedPreferences.edit().putString("NotesHeader", ObjectSerializer.serialize(MainActivityNoteList.notesHeader)).apply();
            Toast.makeText(getApplicationContext(), "Note Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note_edit);

        sharedPreferences = getSharedPreferences("com.example.nitinakoliya.permanentstorage", MODE_PRIVATE);
        editText = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);

        if (index != -1) {
            editText.setText(MainActivityNoteList.notes.get(index));
        }

    }
}
