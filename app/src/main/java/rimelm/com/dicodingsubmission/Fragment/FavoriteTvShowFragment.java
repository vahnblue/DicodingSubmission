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
import rimelm.com.dicodingsubmission.Adapter.FavoriteTvShowAdapter;
import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.Presenter.MainPresenter;
import rimelm.com.dicodingsubmission.R;
import rimelm.com.dicodingsubmission.View.MainView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment implements MainView {


    Context context;
    RecyclerView rv_listTvShowFav;
    ArrayList<Model> favoriteList;
    FavoriteTvShowAdapter favoriteTvShowAdapter;
    MainPresenter mainPresenter;
    ProgressBar pgFrFavTvShow;
    RealmResults<Model> models;
    public final String mCurCheckPosition = "position";

    Realm realm;

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);

        init(view,savedInstanceState);

        return view;
    }

    public void init(View view, Bundle savedInstanceState){
        context = getActivity();
        favoriteList    = new ArrayList<>();
        mainPresenter = new MainPresenter(this, context);
        rv_listTvShowFav  = view.findViewById(R.id.rv_listTvShowFav);
        pgFrFavTvShow     = view.findViewById(R.id.pgFrFavTvShow);
        rv_listTvShowFav.setHasFixedSize(true);
        rv_listTvShowFav.setVisibility(View.VISIBLE);

        Realm.init(context);
        realm = Realm.getDefaultInstance();

        models = realm.where(Model.class)
                .equalTo("typeCatalog","tvShow")
                .findAll();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_listTvShowFav.setLayoutManager(linearLayoutManager);
        pgFrFavTvShow.setVisibility(View.VISIBLE);

        if(savedInstanceState == null){
            mainPresenter.getDataMovieFromAPI(getResources().getString(R.string.language));
        }else {
            pgFrFavTvShow.setVisibility(View.GONE);
            favoriteList = savedInstanceState.getParcelableArrayList(mCurCheckPosition);
        }
        favoriteTvShowAdapter = new FavoriteTvShowAdapter(models,context);
        rv_listTvShowFav.setItemAnimator(new DefaultItemAnimator());
        rv_listTvShowFav.setAdapter(favoriteTvShowAdapter);
        favoriteTvShowAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessAddToList(List<Model> newmodelList) {
        //favoriteTvShowAdapter.setNewProductList(newmodelList);
        pgFrFavTvShow.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        models = realm.where(Model.class)
                .equalTo("typeCatalog","tvShow")
                .findAll();

        favoriteTvShowAdapter = new FavoriteTvShowAdapter(models,context);
        rv_listTvShowFav.setItemAnimator(new DefaultItemAnimator());
        rv_listTvShowFav.setAdapter(favoriteTvShowAdapter);
        favoriteTvShowAdapter.notifyDataSetChanged();
    }

}
