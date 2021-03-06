package com.example.marisayeung.homework3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class NotesList extends AppCompatActivity {

    public static final String NOTE_CAPTION = "MyNotes.note_caption";
    public static final String NOTE_FNAME = "MyNotes.note_filename";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        /*NotesDbHelper dbHelper = NotesDbHelper.getInstance(this);

        List<Note> notes = dbHelper.getAllPhotoNotes();

        if (notes.size() > 0) {
            ListView listView = (ListView) findViewById(R.id.custom_notes_list_view);
            NotesAdapter notesAdapter = new NotesAdapter(this, R.layout.notes_list_row, notes);
            listView.setAdapter(notesAdapter);

            listView.setOnItemClickListener((new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                    Note note = (Note) adapter.getItemAtPosition(position);

                    viewDetail(note);
                }
            }));
        }
        else {
            TextView emptyList = (TextView) findViewById(R.id.empty_list);
            emptyList.setText("Tap + to create a new note");
        }*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAddPhoto();
            }
        });
    }

    public void viewDetail(Note note) {
        Intent intent = new Intent(this, viewPhoto.class);
        intent.putExtra(NOTE_FNAME, note.getFilename());
        intent.putExtra(NOTE_CAPTION, note.getCaption());
        startActivity(intent);
    }

    public void viewAddPhoto() {
        Intent intent = new Intent(this, AddPhoto.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return MenuHelper.handleOnItemSelected(this, item);
    }

    // create onResume method to re-load data after returning from add photo
    @Override
    public void onResume() {
        super.onResume();

        NotesDbHelper dbHelper = NotesDbHelper.getInstance(this);

        List<Note> notes = dbHelper.getAllPhotoNotes();

        if (notes.size() > 0) {
            // clear "create new note" textview if it exists
            TextView emptyList = (TextView) findViewById(R.id.empty_list);
            if (emptyList != null) {
                ((ViewGroup) emptyList.getParent()).removeView(emptyList);
            }

            // create and fill out listview
            ListView listView = (ListView) findViewById(R.id.custom_notes_list_view);
            NotesAdapter notesAdapter = new NotesAdapter(this, R.layout.notes_list_row, notes);
            listView.setAdapter(notesAdapter);

            listView.setOnItemClickListener((new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                    Note note = (Note) adapter.getItemAtPosition(position);

                    viewDetail(note);
                }
            }));
        }
        else {
            TextView emptyList = (TextView) findViewById(R.id.empty_list);
            emptyList.setText("Tap + to create a new note");
        }
    }

}
