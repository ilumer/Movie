package com.example.root.movie.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.root.movie.helper.IMDBHelper;
import com.example.root.movie.R;
import com.example.root.movie.model.rvadapter.MovieAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReHolder extends RecyclerView.ViewHolder {
    private MovieAdapter movieAdapter;
    @BindView(R.id.movie_image)
    ImageView imageView;
    @BindView(R.id.movie_name)
    TextView name;
    @BindView(R.id.card_view)
    View view;

    public ReHolder(View itemView, MovieAdapter adapter) {
        super(itemView);
        this.movieAdapter = adapter;
        ButterKnife.bind(this,itemView);
    }

    public void bindModel(MovieData.ResultsBean i,Context context){
        String uri = IMDBHelper.getImageBsUri(IMDBHelper.getWidth(context),i.getPoster_path());
        Glide.with(context).
                load(uri).
                centerCrop().
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imageView);
        name.setText(i.getTitle());
    }


}
