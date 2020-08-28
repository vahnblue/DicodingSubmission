package rimelm.com.dicodingsubmission.Fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rimelm.com.dicodingsubmission.Adapter.FavoriteMovieAdapter;
import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.Presenter.MainPresenter;
import rimelm.com.dicodingsubmission.R;
import rimelm.com.dicodingsubmission.View.MainView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements MainView {

    Context context;
    RecyclerView rv_listMovieFav;
    ArrayList<Model> favoriteList;
    FavoriteMovieAdapter favoriteMovieAdapter;
    MainPresenter mainPresenter;
    ProgressBar pgFrFavMove;

    RealmResults<Model> models;

    public final String mCurCheckPosition = "position";
    Realm realm;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);

        init(view,savedInstanceState);

        return view;
    }


    public void init(View view, Bundle savedInstanceState){
        context =  getActivity();
        favoriteList     = new ArrayList<>();
        mainPresenter    = new MainPresenter(this, context);
        rv_listMovieFav  = view.findViewById(R.id.rv_listMovieFav);
        pgFrFavMove      = view.findViewById(R.id.pgFrFavMove);
        rv_listMovieFav.setHasFixedSize(true);
        rv_listMovieFav.setVisibility(View.VISIBLE);

        Realm.init(context);
        realm = Realm.getDefaultInstance();

        models = realm.where(Model.class)
                .equalTo("typeCatalog","movie")
                .findAll();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_listMovieFav.setLayoutManager(linearLayoutManager);
        pgFrFavMove.setVisibility(View.VISIBLE);

        if(savedInstanceState == null){
            mainPresenter.getDataMovieFromAPI(getResources().getString(R.string.language));
        }else {
            pgFrFavMove.setVisibility(View.GONE);
            favoriteList = savedInstanceState.getParcelableArrayList(mCurCheckPosition);
        }
        favoriteMovieAdapter = new FavoriteMovieAdapter(models,context);
        rv_listMovieFav.setItemAnimator(new DefaultItemAnimator());
        rv_listMovieFav.setAdapter(favoriteMovieAdapter);
        favoriteMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessAddToList(List<Model> newmodelList) {
        //favoriteMovieAdapter.setNewProductList(newmodelList);
        pgFrFavMove.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        models = realm.where(Model.class)
                .equalTo("typeCatalog","movie")
                .findAll();

        favoriteMovieAdapter = new FavoriteMovieAdapter(models,context);
        rv_listMovieFav.setItemAnimator(new DefaultItemAnimator());
        rv_listMovieFav.setAdapter(favoriteMovieAdapter);
        favoriteMovieAdapter.notifyDataSetChanged();
    }
}
