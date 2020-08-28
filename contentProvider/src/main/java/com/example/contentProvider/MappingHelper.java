package com.example.contentProvider;

import android.database.Cursor;

import java.util.ArrayList;

import static com.example.contentProvider.DatabaseContract.TaskColumns.DATE_RELEASE;
import static com.example.contentProvider.DatabaseContract.TaskColumns.DESCRIPTION;
import static com.example.contentProvider.DatabaseContract.TaskColumns.PHOTO;
import static com.example.contentProvider.DatabaseContract.TaskColumns.TITLE;
import static com.example.contentProvider.DatabaseContract.TaskColumns._ID;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            String id = notesCursor.getString(notesCursor.getColumnIndexOrThrow(_ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DESCRIPTION));
            String photo = notesCursor.getString(notesCursor.getColumnIndexOrThrow(PHOTO));
            String releaseDate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DATE_RELEASE));
            notesList.add(new Movie(id,photo,title, description,releaseDate));
        }
        return notesList;
    }
}
