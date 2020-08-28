package com.example.contentProvider;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(ArrayList<Movie> movies , Context context){
        this.movies  = movies;
        this.context = context;
    }

    public void setListMovie(ArrayList<Movie> listNotes) {
        this.movies.clear();
        this.movies.addAll(listNotes);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.cv_movie, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(convertView);
        Movie movie           = (Movie) getItem(position);
        viewHolder.bind(movie);

        return convertView;
    }

    private class ViewHolder{

        private ImageView    iv_photo;
        private TextView     tv_title,tv_description;

        ViewHolder(View view){
            iv_photo        = view.findViewById(R.id.iv_photo);
            tv_title        = view.findViewById(R.id.tv_title);
            tv_description  = view.findViewById(R.id.tv_description);
        }

        void bind(final Movie movie){
            //iv_photo.setImageResource(movie.getPhoto());
                Picasso.with(context).load("https://image.tmdb.org/t/p/w300/"+movie.getPhoto()).into(iv_photo);
            tv_title.setText(movie.getTitle());
            tv_description.setText(movie.getDescription());

            Log.d("tesss", "https://image.tmdb.org/t/p/w300/"+movie.getPhoto());

        }
    }
}
