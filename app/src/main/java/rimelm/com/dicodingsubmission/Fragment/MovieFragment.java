package rimelm.com.dicodingsubmission.Fragment;


import androidx.lifecycle.ViewModel;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import rimelm.com.dicodingsubmission.Adapter.MovieAdapter;
import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.Presenter.MainPresenter;
import rimelm.com.dicodingsubmission.R;
import rimelm.com.dicodingsubmission.View.MainView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MainView, TextWatcher {

    RecyclerView rv_listMovie;
    ArrayList<Model> moviesList;
    MovieAdapter movieAdapter;
    MainPresenter mainPresenter;
    EditText et_searchMovie;
    Context context;
    ProgressBar pgFrMovie;
    public final String mCurCheckPosition = "position";

    Toolbar toolbar;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        init(view,savedInstanceState);

        return view;
    }

    public void init(View view, Bundle savedInstanceState){
        moviesList    = new ArrayList<>();
        context       = getActivity();
        mainPresenter = new MainPresenter(this, context);
        rv_listMovie  = view.findViewById(R.id.rv_listMovie);
        pgFrMovie     = view.findViewById(R.id.pgFrMovie);
        rv_listMovie.setHasFixedSize(true);
        rv_listMovie.setVisibility(View.VISIBLE);
        toolbar         = view.findViewById(R.id.toolbarMovie);
        et_searchMovie  = view.findViewById(R.id.et_searchMovie);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_listMovie.setLayoutManager(linearLayoutManager);
        pgFrMovie.setVisibility(View.VISIBLE);

        et_searchMovie.addTextChangedListener(this);

        if(savedInstanceState == null){
            mainPresenter.getDataMovieFromAPI(getResources().getString(R.string.language));
        }else {
            pgFrMovie.setVisibility(View.GONE);
            moviesList = savedInstanceState.getParcelableArrayList(mCurCheckPosition);
        }
        movieAdapter = new MovieAdapter(moviesList,context);
        rv_listMovie.setItemAnimator(new DefaultItemAnimator());
        rv_listMovie.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessAddToList(List<Model> newmodelList) {
        movieAdapter.setNewProductList(newmodelList);
        pgFrMovie.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(mCurCheckPosition,moviesList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.toString().equalsIgnoreCase("")){
            mainPresenter.getDataMovieFromAPI(getResources().getString(R.string.language));
        }
        else {
            mainPresenter.searchingMovie(charSequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
