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

import rimelm.com.dicodingsubmission.Activity.DetailActivity;
import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.R;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    ArrayList<Model> listTvShow;
    Context context;
    String type;

    public TvShowAdapter(ArrayList<Model> listTvShow, Context context){
        this.listTvShow = listTvShow;
        this.context = context;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_tv_show, viewGroup,false);
        return new TvShowAdapter.TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder tvShowViewHolder, int i) {
        final Model model = listTvShow.get(i);

        Picasso.with(context).load("https://image.tmdb.org/t/p/w342/"+model.getPhoto()).into(tvShowViewHolder.ivPhotoTvShow);
        tvShowViewHolder.tvTitleTvShow.setText(model.getTitle());
        tvShowViewHolder.tvDescriptionTvShow.setText(model.getDescription());

        type = "tvShow";

        tvShowViewHolder.ll_contentTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Model tvShowParce = new Model();
                tvShowParce.setTitle(model.getTitle());
                tvShowParce.setPhoto(model.getPhoto());
                tvShowParce.setDescription(model.getDescription());
                tvShowParce.setDateRelease(model.getDateRelease());

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.DETAIL, tvShowParce);
                intent.putExtra("typeCatalog",type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    public void setNewProductList(List<Model> newProductList) {
        this.listTvShow.clear();
        this.listTvShow.addAll(newProductList);
        this.notifyDataSetChanged();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhotoTvShow;
        private TextView  tvTitleTvShow;
        private TextView  tvDescriptionTvShow;
        private LinearLayout ll_contentTvShow;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhotoTvShow       = itemView.findViewById(R.id.ivPhotoTvShow);
            tvTitleTvShow       = itemView.findViewById(R.id.tvTitleTvShow);
            tvDescriptionTvShow = itemView.findViewById(R.id.tvDescriptionTvShow);
            ll_contentTvShow    = itemView.findViewById(R.id.ll_contentTvShow);

        }
    }
}
