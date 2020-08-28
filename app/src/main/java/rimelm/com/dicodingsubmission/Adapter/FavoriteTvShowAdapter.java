package rimelm.com.dicodingsubmission.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import rimelm.com.dicodingsubmission.Activity.DetailActivity;
import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.R;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.ViewHolder> {

    ArrayList<Model> listFavorite;
    Context context;
    RealmResults<Model> models;
    int fav;

    public FavoriteTvShowAdapter(RealmResults<Model> models, Context context){
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_favorite, viewGroup,false);
        return new FavoriteTvShowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Model model = models.get(i);

        Picasso.with(context).load("https://image.tmdb.org/t/p/w342/"+model.getPhoto()).into(viewHolder.ivPhotoFavorite);
        viewHolder.tvTitleFavorite.setText(model.getTitle());
        viewHolder.tvDescriptionFavorite.setText(model.getDescription());
        fav = 1;

        viewHolder.ll_contentFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model movieParce = new Model();
                movieParce.setTitle(model.getTitle());
                movieParce.setPhoto(model.getPhoto());
                movieParce.setDescription(model.getDescription());
                movieParce.setDateRelease(model.getDateRelease());

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.DETAIL, movieParce);
                //intent.putExtra("typeCatalog",type);
                intent.putExtra("favClick",fav);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setNewProductList(List<Model> newProductList) {
        this.models.clear();
        this.models.addAll(newProductList);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhotoFavorite;
        TextView tvTitleFavorite,tvDescriptionFavorite;
        LinearLayout ll_contentFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhotoFavorite       = itemView.findViewById(R.id.ivPhotoFavorite);
            tvTitleFavorite       = itemView.findViewById(R.id.tvTitleFavorite);
            tvDescriptionFavorite = itemView.findViewById(R.id.tvDescriptionFavorite);
            ll_contentFavorite    = itemView.findViewById(R.id.ll_contentFavorite);
        }
    }
}
