package rimelm.com.dicodingsubmission.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import rimelm.com.dicodingsubmission.R;

public class FavoriteFragment extends Fragment {


    BottomNavigationView bottomNavigationView;


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);



        bottomNavigationView = view.findViewById(R.id.topNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if(savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.navigationFavMovie);
        }

        return view;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigationFavMovie:
                    fragment = new FavoriteMovieFragment();
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.flContentFavorite, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;

                case R.id.navigationFavTvShow:
                    fragment = new FavoriteTvShowFragment();
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.flContentFavorite, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };


}
