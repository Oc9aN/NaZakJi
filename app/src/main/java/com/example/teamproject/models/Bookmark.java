package com.example.teamproject.models;

public class Bookmark {

    private String Bookmark;
    private int type;

    public Bookmark(String str, int type) {
        Bookmark = str;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getBookmark() {
        return Bookmark;
    }

    public void setBookmark(String bookmark) {
        Bookmark = bookmark;
    }
}