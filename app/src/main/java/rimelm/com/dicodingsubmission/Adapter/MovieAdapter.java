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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    ArrayList<Model> listMovie;
    Context context;
    String type;

    public MovieAdapter(ArrayList<Model> listMovie, Context context){
        this.listMovie     = listMovie;
        this.context       = context;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_movie, viewGroup,false);
        return new MovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder viewHolder, int i) {
        final Model model = listMovie.get(i);

        Picasso.with(context).load("https://image.tmdb.org/t/p/w342/"+model.getPhoto()).into(viewHolder.ivPhotoMovie);
        viewHolder.tvTitleMovie.setText(model.getTitle());
        viewHolder.tvDescriptionMovie.setText(model.getDescription());


        type = "movie";

        viewHolder.ll_contentMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model movieParce = new Model();
                movieParce.setTitle(model.getTitle());
                movieParce.setPhoto(model.getPhoto());
                movieParce.setDescription(model.getDescription());
                movieParce.setDateRelease(model.getDateRelease());

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.DETAIL, movieParce);
                intent.putExtra("typeCatalog",type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public void setNewProductList(List<Model> newProductList) {
        this.listMovie.clear();
        this.listMovie.addAll(newProductList);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivPhotoMovie;
        private TextView tvTitleMovie;
        private TextView tvDescriptionMovie;
        private LinearLayout ll_contentMovie;
        private ImageView ivFavMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhotoMovie        = itemView.findViewById(R.id.ivPhotoMovie);
            tvTitleMovie        = itemView.findViewById(R.id.tvTitleMovie);
            tvDescriptionMovie  = itemView.findViewById(R.id.tvDescriptionMovie);
            ll_contentMovie     = itemView.findViewById(R.id.ll_contentMovie);
            ivFavMovie          = itemView.findViewById(R.id.ivFavMovie);

        }
    }
}
