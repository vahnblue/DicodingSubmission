package com.example.contentProvider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String TABLE_FAVORITE = "favorite";

    public static final class TaskColumns implements BaseColumns {
        //Task ID
        public static final String _ID = "id";
        //Task description
        public static final String TITLE = "title";
        //Task description
        public static final String DESCRIPTION = "description";
        //Completed marker
        public static final String PHOTO = "photo";
        //Priority marker
        public static final String DATE_RELEASE = "date_release";
    }

    public static final String CONTENT_AUTHORITY = "rimelm.com.dicodingsubmission";

    //Base content Uri for accessing the provider
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    /* Helpers to retrieve column values */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}
