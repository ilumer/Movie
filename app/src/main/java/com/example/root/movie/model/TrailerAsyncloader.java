package com.example.root.movie.model;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.root.movie.Net.MovieOkhttp;

import java.util.List;

/**
 * Created by root on 16-6-7.
 */
public class TrailerAsyncloader extends AsyncTaskLoader<List<Trailers.ResultsBean>> {
    int id;
    public TrailerAsyncloader(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    public List<Trailers.ResultsBean> loadInBackground() {
        return MovieOkhttp.getTrailers(id);
    }
}
