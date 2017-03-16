package com.example.root.movie.model;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.root.movie.net.MovieOkhttp;

import java.util.List;

public class TrailerAsyncloader extends AsyncTaskLoader<List<Trailers.Trailer>> {
    int id;
    public TrailerAsyncloader(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    public List<Trailers.Trailer> loadInBackground() {
        return MovieOkhttp.getTrailers(id);
    }
}
