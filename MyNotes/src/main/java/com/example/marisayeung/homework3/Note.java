package com.example.marisayeung.homework3;

/**
 * Created by marisayeung on 2/10/16.
 */
public class Note {
    private String filename;
    private String caption;

    public Note (String filename, String caption) {
        this.filename = filename;
        this.caption = caption;
    }


    public String getFilename() {
        return filename;
    }

    public String getCaption() {
        return caption;
    }
}
