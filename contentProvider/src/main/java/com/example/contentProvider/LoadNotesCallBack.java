package com.example.contentProvider;

import android.database.Cursor;

public interface LoadNotesCallBack {
    void postExecute(Cursor movie);
}
