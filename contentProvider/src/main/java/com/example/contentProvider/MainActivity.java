package com.example.contentProvider;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.contentProvider.DatabaseContract.CONTENT_URI;
import static com.example.contentProvider.MappingHelper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadNotesCallBack {

    ListView lv_movies;
    MovieAdapter        movieAdapter;
    ArrayList<Movie> movieList;
    Movie               movie;
    private DataObserver myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_movies=findViewById(R.id.lv_movies);
        lv_movies.setVisibility(View.VISIBLE);

        movie = new Movie();
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList, getApplicationContext());
        lv_movies.setAdapter(movieAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        new getData(this, this).execute();

    }


    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallBack> weakCallback;
        private getData(Context context, LoadNotesCallBack callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }
    }


    @Override
    public void postExecute(Cursor movie) {
        ArrayList<Movie> listMovie = mapCursorToArrayList(movie);

        if (listMovie.size() > 0) {
            movieAdapter.setListMovie(listMovie);
        } else {
            Toast.makeText(this, "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
            movieAdapter.setListMovie(new ArrayList<Movie>());
        }
    }


    static class DataObserver extends ContentObserver {

        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }

}
