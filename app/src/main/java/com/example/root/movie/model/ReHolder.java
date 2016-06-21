package com.example.root.movie.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.root.movie.net.DBAPI;
import com.example.root.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReHolder extends RecyclerView.ViewHolder {
    private MovieAdapter movieAdapter;
    private Context mcontext;
    @BindView(R.id.movie_image)
    ImageView imageView;

    public ReHolder(View itemView, MovieAdapter adapter, Context context) {
        super(itemView);
        this.movieAdapter = adapter;
        this.mcontext = context;
        ButterKnife.bind(this,itemView);
    }

    void bindModel(MovieData.ResultsBean i){
        String uri = DBAPI.BASEIMAGE_URI + i.getPoster_path();
        Glide.with(mcontext).load(uri).
                fitCenter().
                diskCacheStrategy(DiskCacheStrategy.ALL).
                error(R.mipmap.ic_launcher).
                into(imageView);
    }
}
