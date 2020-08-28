package rimelm.com.dicodingsubmission.Fragment;


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

import rimelm.com.dicodingsubmission.Adapter.TvShowAdapter;
import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.Presenter.MainPresenter;
import rimelm.com.dicodingsubmission.R;
import rimelm.com.dicodingsubmission.View.MainView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements MainView, TextWatcher {


    RecyclerView rv_listTvShow;
    ArrayList<Model> tvShowList;
    TvShowAdapter tvShowAdapter;
    MainPresenter mainPresenter;
    Context context;
    EditText et_searchTvShow;
    ProgressBar pgFrTvShow;
    public final String mCurCheckPosition = "position";
    Toolbar toolbar;


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        init(view,savedInstanceState);

        return view;
    }

    void init(View view,Bundle savedInstanceState){
        tvShowList    = new ArrayList<>();
        context       = getActivity();
        mainPresenter = new MainPresenter(this, context);
        rv_listTvShow = view.findViewById(R.id.rv_listTvShow);
        pgFrTvShow    = view.findViewById(R.id.pgFrTvShow);
        rv_listTvShow.setHasFixedSize(true);
        rv_listTvShow.setVisibility(View.VISIBLE);

        toolbar         = view.findViewById(R.id.toolbarTvShow);
        et_searchTvShow  = view.findViewById(R.id.et_searchTvShow);

        et_searchTvShow.addTextChangedListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_listTvShow.setLayoutManager(linearLayoutManager);
        pgFrTvShow.setVisibility(View.VISIBLE);

        if(savedInstanceState == null){

            mainPresenter.getDataTvShowFromAPI(getResources().getString(R.string.language));

        }else {
            pgFrTvShow.setVisibility(View.GONE);
            tvShowList = savedInstanceState.getParcelableArrayList(mCurCheckPosition);
        }
        tvShowAdapter = new TvShowAdapter(tvShowList, context);
        rv_listTvShow.setItemAnimator(new DefaultItemAnimator());
        rv_listTvShow.setAdapter(tvShowAdapter);
        tvShowAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessAddToList(List<Model> newmodelList) {
        tvShowAdapter.setNewProductList(newmodelList);
        pgFrTvShow.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(mCurCheckPosition,tvShowList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.toString().equalsIgnoreCase("")){
            mainPresenter.getDataTvShowFromAPI(getResources().getString(R.string.language));
        }
        else {
            mainPresenter.searchingTvShow(charSequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
