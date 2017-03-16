package com.example.root.movie.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.net.MovieOkhttp;

import java.io.IOException;

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
        try {
            return MovieOkhttp.getDetialMovieInfo(id);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
