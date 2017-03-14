package com.example.root.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.root.movie.R;
import com.example.root.movie.adapter.ViewHolder.BaseViewHolder;
import com.example.root.movie.adapter.ViewHolder.MovieViewHolder;
import com.example.root.movie.model.MovieInfo;

import java.util.List;

/**
 * Created by ilumer on 17-3-10.
 */

public class MovieAdapter extends ProgressAdapter {
    private static final int MOVIE_TYPE = 1;

    private List<MovieInfo> moviesList;

    public MovieAdapter(List<MovieInfo> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    int getExtItemViewType(int position) {
        return MOVIE_TYPE;
    }

    @Override
    int getDataCount() {
        return moviesList.size();
    }

    @Override
    BaseViewHolder onCreateExtViewHolder(Context context, ViewGroup parent) {
        return new MovieViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.viewholder_movie,parent,false));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case MOVIE_TYPE:{
                MovieInfo movie = moviesList.get(position);
                holder.bind(movie);
                break;
            }
            case LOADING_TYPE:
            case LOADING_ERROR:{
                break;
            }
        }
    }

    public void replaceAll(List<MovieInfo> movies){
        moviesList.clear();
        moviesList.addAll(movies);
        notifyDataSetChanged();
    }

    public void addAll(List<MovieInfo> movies){
        loadingEnd();
        moviesList.addAll(movies);
        notifyItemRangeInserted(moviesList.size() - movies.size(),movies.size());
    }

    public int getSpanSize(int position){
        switch (getItemViewType(position)){
            case LOADING_ERROR:
            case LOADING_TYPE:
                return 2;
            default:
                return 1;
        }
    }
}
