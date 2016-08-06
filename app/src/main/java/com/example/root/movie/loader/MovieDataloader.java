package com.example.root.movie.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.net.MovieOkhttp;

/**
 * Created by root on 8/4/16.
 *
 */
public class MovieDataloader extends AsyncTaskLoader<DetailMovie> {
    int id;

    public MovieDataloader(Context context,int id) {
        super(context);
        this.id = id;
    }

    @Override
    public DetailMovie loadInBackground() {
        return MovieOkhttp.getDetialMovieInfo(id);
    }
}
