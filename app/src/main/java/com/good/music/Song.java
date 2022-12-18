package com.good.music;

public class Song {
    public Song(int numID, String i, String p, String t, String al, String ar) {
        numericID = numID;
        id = i;
        path = p;
        title = t;
        album = al;
        artist = ar;
    }

    public int getNumericID() { return numericID; }
    public String getId() {
        return id;
    }
    public String getPath() {
        return path;
    }
    public String getTitle() { return title; }
    public String getAlbum() {
        return album;
    }
    public String getArtist() {
        return artist;
    }

    private int numericID = 0;
    private String id = "NULL_ID";
    private String path = "error path";

    private String title = "error title";
    private String album = "error album";
    private String artist = "error artist";
}
