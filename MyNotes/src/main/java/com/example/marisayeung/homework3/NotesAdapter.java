package com.example.marisayeung.homework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by marisayeung on 2/10/16.
 */
public class NotesAdapter extends ArrayAdapter<Note> {

    private final List<Note> notes;

    public NotesAdapter(Context context, int resource, List<Note> notes) {
        super(context, resource, notes);

        this.notes = notes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = notes.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.notes_list_row, null);

        TextView filenameView = (TextView) row.findViewById(R.id.rowTitle);
        String[] splitPath = note.getFilename().split("/");
        String fName = splitPath[splitPath.length - 1];
        filenameView.setText(fName);

//        Create a one line brief preview of the caption for the list view
        TextView captionView = (TextView) row.findViewById(R.id.rowText);
        String caption_preview;
        int cap_length = note.getCaption().length();
        if (cap_length < 45) {
            caption_preview = note.getCaption();
        } else {
            caption_preview = note.getCaption().substring(0,20) + "...";
        }
        captionView.setText(caption_preview);

        return row;
    }

}
