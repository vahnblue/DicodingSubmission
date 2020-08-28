package rimelm.com.dicodingsubmission.Activity;

import android.content.ContentValues;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.UUID;

import io.realm.Realm;
import rimelm.com.dicodingsubmission.Adapter.FavoriteMovieAdapter;
import rimelm.com.dicodingsubmission.Adapter.FavoriteTvShowAdapter;
import rimelm.com.dicodingsubmission.Database.DatabaseContract;
import rimelm.com.dicodingsubmission.Database.FavoriteUpdateService;
import rimelm.com.dicodingsubmission.MainActivity;
import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.R;
import rimelm.com.dicodingsubmission.View.MainView;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL = "detail";
    ImageView iv_gambarDetail,img_favorite,img_favoriteRed;
    TextView tv_judulDetail,tv_contentDetail,tv_deskripsiDetail,tv_releaseDetail;
    Realm realm;
    String typeCatalog;
    int favClick;
    private MainView mainView;

    FavoriteTvShowAdapter favoriteTvShowAdapter;
    FavoriteMovieAdapter favoriteMovieAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar mToolbar = findViewById(R.id.toolbarDetail);
        mToolbar.setTitle("Back");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        realm.init(this);
        realm = Realm.getDefaultInstance();

        final Model model = getIntent().getParcelableExtra(DETAIL);

        typeCatalog = getIntent().getStringExtra("typeCatalog");
        favClick    = getIntent().getIntExtra("favClick",0);

        iv_gambarDetail     = findViewById(R.id.iv_gambarDetail);
        tv_contentDetail    = findViewById(R.id.tv_contentDetail);
        tv_judulDetail      = findViewById(R.id.tv_judulDetail);
        tv_deskripsiDetail  = findViewById(R.id.tv_deskripsiDetail);
        tv_releaseDetail    = findViewById(R.id.tv_releaseDetail);
        img_favorite        = findViewById(R.id.img_favorite);
        img_favoriteRed     = findViewById(R.id.img_favoriteRed);

        //Toast.makeText(this, tv_judulDetail.getText().toString(), Toast.LENGTH_SHORT).show();

        Log.d("awddd", tv_judulDetail.getText().toString());

        Model modelAs = realm.where(Model.class)
                .equalTo("title", model.getTitle())
                .findFirst();

        if(modelAs != null){
            Log.d("modelas", "masuk");
            img_favoriteRed.setVisibility(View.VISIBLE);
            img_favorite.setVisibility(View.GONE);
        }
        else {
            Log.d("modelas", "masuk1");
            img_favorite.setVisibility(View.VISIBLE);
            img_favoriteRed.setVisibility(View.GONE);
        }

        Picasso.with(this).load("https://image.tmdb.org/t/p/w300/"+model.getPhoto()).into(iv_gambarDetail);
        tv_contentDetail.setText(model.getDescription());
        tv_judulDetail.setText(model.getTitle());
        tv_deskripsiDetail.setText(model.getDescription());
        tv_releaseDetail.setText(model.getDateRelease());


        img_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("teskanini", favClick+"");
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            Model model1 = bgRealm.createObject(Model.class, UUID.randomUUID().toString());
                            model1.setTitle(model.getTitle());
                            model1.setDateRelease(model.getDateRelease());
                            model1.setDescription(model.getDescription());
                            model1.setPhoto(model.getPhoto());
                            model1.setTypeCatalog(typeCatalog);

                            ContentValues values = new ContentValues(4);
                            values.put(DatabaseContract.TaskColumns.TITLE, model.getTitle());
                            values.put(DatabaseContract.TaskColumns.DESCRIPTION, model.getDescription());
                            values.put(DatabaseContract.TaskColumns.PHOTO, model.getPhoto());
                            values.put(DatabaseContract.TaskColumns.DATE_RELEASE, model.getDateRelease());

                            FavoriteUpdateService.insertNewFavorite(getApplicationContext(),values);

                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Log.d("apaini", "Sukses");
                            favClick = 1;
                        }
                    });
                    img_favoriteRed.setVisibility(View.VISIBLE);
                    img_favorite.setVisibility(View.GONE);

            }
        });

        img_favoriteRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Model model1 = realm.where(Model.class)
                                    .equalTo(Model.PROPERTY_TITLE, tv_judulDetail.getText().toString())
                                    .findFirst();
                            if (model1 != null) {
                                model1.deleteFromRealm();
                                Log.d("teskanini", "masukini");
                                //favClick = 0 ;
                            }
                        }
                    });
                    img_favorite.setVisibility(View.VISIBLE);
                    img_favoriteRed.setVisibility(View.GONE);

            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
