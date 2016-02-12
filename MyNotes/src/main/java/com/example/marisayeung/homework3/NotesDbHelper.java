package com.example.marisayeung.homework3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marisayeung on 2/11/16.
 */
public class NotesDbHelper extends SQLiteOpenHelper {
    private static NotesDbHelper instance;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_CAPTION = "caption";

    public static final String TABLE_NAME = "notes";
    public static final int DATABASE_VERSION = 1;

    public static synchronized NotesDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NotesDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    public NotesDbHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text)",
            TABLE_NAME, COLUMN_ID, COLUMN_PATH, COLUMN_CAPTION);

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
//        createPhotoNote("wocket.jpg", "Did you ever get the feeling there's a ZAMP in the LAMP?");
//        createPhotoNote("footbook.jpg", "Left foot Left foot, Right foot Right. Feet in the day. Feet in the night");
//        createPhotoNote("abc.jpg", "Big C little C what begins with c?  Camel on the ceiling c, c, c!");
//        createPhotoNote("nook.jpg", "We took a look, we saw a nook. On his head, he had a hook.");
//        createPhotoNote("onefish.jpg", "One fish, two fish. Red fish, blue fish. Old fish, new fish.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long createPhotoNote(String path, String caption) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PATH, path);
        values.put(COLUMN_CAPTION, caption);

        long note_id =  db.insert(TABLE_NAME, null, values);
        return note_id;
    }

    public List<Note> getAllPhotoNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_PATH,
                COLUMN_CAPTION
        };

        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                where,
                whereArgs,
                groupBy,
                having,
                order
        );

        List<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            String path = cursor.getString(0);
            String caption = cursor.getString(1);
            Note note = new Note(path, caption);
            notes.add(note);
        }
        return notes;
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
