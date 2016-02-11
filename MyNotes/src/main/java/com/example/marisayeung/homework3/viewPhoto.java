package com.example.marisayeung.homework3;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class viewPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String fname = intent.getStringExtra(NotesList.NOTE_FNAME);

        TextView title = (TextView) findViewById(R.id.photo_title);
        title.setText(fname);

        try {
            ImageView image = (ImageView) findViewById(R.id.photo);
            InputStream inputStream = getAssets().open(intent.getStringExtra(NotesList.NOTE_FNAME));
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            image.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView caption = (TextView) findViewById(R.id.photo_caption);
        caption.setText(intent.getStringExtra(NotesList.NOTE_CAPTION));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

}
