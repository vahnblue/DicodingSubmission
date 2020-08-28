package rimelm.com.dicodingsubmission.Presenter;

import androidx.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.View.MainView;

public class MainPresenter {

    private MainView view;
    Context context;

    private RequestQueue queue;

    public MainPresenter(MainView view, Context context){
        this.view       = view;
        this.context    = context;
    }

    public void getDataMovieFromAPI(String language){
        final JsonObjectRequest rec = new JsonObjectRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/now_playing?api_key=7c10b1851b48fcd85652c7f921820eee&language="+language+"&page=1",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ini_respon", "respon :"+response);
                ArrayList<Model> moviesList = new ArrayList<>();
                JSONArray movie = null;
                try {
                    moviesList.clear();
                    movie = response.getJSONArray("results");
                    for (int i = 0; i < movie.length(); i++) {
                        try {
                            JSONObject obj = movie.getJSONObject(i);

                            moviesList.add(new Model(obj.getString("id"),
                                    obj.getString("title"),
                                    obj.getString("backdrop_path"),
                                    obj.getString("overview"),
                                    obj.getString("release_date")));

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    view.onSuccessAddToList(moviesList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal memuat", Toast.LENGTH_SHORT).show();
                Log.d("ini_respon", ""+error);
            }
        });
        queue = Volley.newRequestQueue(context);
        queue.add(rec);
    }

    public void getDataTvShowFromAPI(String language){
        final JsonObjectRequest rec = new JsonObjectRequest(Request.Method.GET, "https://api.themoviedb.org/3/tv/airing_today?api_key=7c10b1851b48fcd85652c7f921820eee&language="+language+"&page=1",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ini_respon", "respon :"+response);
                ArrayList<Model> tvShowList = new ArrayList<>();
                JSONArray tvShow = null;
                try {
                    tvShowList.clear();
                    tvShow = response.getJSONArray("results");
                    for (int i = 0; i < tvShow.length(); i++) {
                        try {
                            JSONObject obj = tvShow.getJSONObject(i);

                            tvShowList.add(new Model(obj.getString("id"),
                                    obj.getString("name"),
                                    obj.getString("backdrop_path"),
                                    obj.getString("overview"),
                                    obj.getString("first_air_date")));

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    view.onSuccessAddToList(tvShowList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal memuat", Toast.LENGTH_SHORT).show();
                Log.d("ini_respon", ""+error);
            }
        });
        queue = Volley.newRequestQueue(context);
        queue.add(rec);
    }

    public void searchingMovie(String text){
        final JsonObjectRequest rec = new JsonObjectRequest(Request.Method.GET, "https://api.themoviedb.org/3/search/movie?api_key=7c10b1851b48fcd85652c7f921820eee&language=en-US&query="+text,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ini_respon", "respon :"+response);
                ArrayList<Model> moviesList = new ArrayList<>();
                JSONArray movie = null;
                try {
                    moviesList.clear();
                    movie = response.getJSONArray("results");
                    for (int i = 0; i < movie.length(); i++) {
                        try {
                            JSONObject obj = movie.getJSONObject(i);

                            moviesList.add(new Model(obj.getString("id"),
                                    obj.getString("title"),
                                    obj.getString("backdrop_path"),
                                    obj.getString("overview"),
                                    obj.getString("release_date")));

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    view.onSuccessAddToList(moviesList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal memuat", Toast.LENGTH_SHORT).show();
                Log.d("ini_respon", ""+error);
            }
        });
        queue = Volley.newRequestQueue(context);
        queue.add(rec);
    }

    public void searchingTvShow(String text){
        final JsonObjectRequest rec = new JsonObjectRequest(Request.Method.GET, "https://api.themoviedb.org/3/search/tv?api_key=7c10b1851b48fcd85652c7f921820eee&language=en-US&query="+text,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ini_respon", "respon :"+response);
                ArrayList<Model> tvShowList = new ArrayList<>();
                JSONArray tvShow = null;
                try {
                    tvShowList.clear();
                    tvShow = response.getJSONArray("results");
                    for (int i = 0; i < tvShow.length(); i++) {
                        try {
                            JSONObject obj = tvShow.getJSONObject(i);

                            tvShowList.add(new Model(obj.getString("id"),
                                    obj.getString("name"),
                                    obj.getString("backdrop_path"),
                                    obj.getString("overview"),
                                    obj.getString("first_air_date")));

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    view.onSuccessAddToList(tvShowList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal memuat", Toast.LENGTH_SHORT).show();
                Log.d("ini_respon", ""+error);
            }
        });
        queue = Volley.newRequestQueue(context);
        queue.add(rec);
    }

}
