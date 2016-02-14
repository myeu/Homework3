package com.example.marisayeung.homework3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marisayeung.homework3.NotesDbHelper;
import com.example.marisayeung.homework3.NotesList;
import com.example.marisayeung.homework3.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPhoto extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    boolean photoTaken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set the photo taken icon if photo was taken and then activity reloaded
        if(photoTaken) {
            setThumbnailIcon();
        }
    }

    /*
        Use the phone's camera to take a new photo to insert into the new note
     */
    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                // create a file to save the photo in
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.d("marisatest", "photoFile is null");
            }
            if (photoFile != null) {
                // file was created successfully, open the camera
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /*
        Create the uniquely named file to save the photo we will take
     */
    private File createImageFile() throws IOException {
        // form unique filename
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp;

        if (!isExternalStorageWritable()) {
            Log.d("marisatest", "external storage not writable");
        }

        // get pathname to use for file
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        File image;
        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Log.d("marisatest", " " + ioe);
            return null;
        }

        // save path for later
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /*
        check the state of external storage
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /*
        User clicked the save button to save the image path and caption to the db
     */
    public void onSave(View view) {
        if (!photoTaken) {
            TextView errorField = (TextView) findViewById(R.id.error_field);
            errorField.setText("Please take a photo.");
            return;
        }
        EditText caption = (EditText) findViewById(R.id.edit_caption);
        String new_caption = caption.getText().toString();
        if (new_caption.equals("")) {
            caption.setError("Please enter a caption.");
            return;
        }

        NotesDbHelper dbHelper = NotesDbHelper.getInstance(this);

        dbHelper.createPhotoNote(mCurrentPhotoPath, new_caption);

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photoTaken = true;
            setThumbnailIcon();

            // clear missing photo error if there was one
            TextView errorField = (TextView) findViewById(R.id.error_field);
            errorField.setText("");
        }
    }

    private void setThumbnailIcon() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setImageResource(R.drawable.ic_image_24dp);
    }
}
