package com.example.root.movie.repositories.impl;

import android.content.Context;

import com.example.root.movie.data.source.local.MovieContract;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.net.MovieOkhttp;
import com.example.root.movie.repositories.MoviesRepository;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by ilumer on 17-3-7.
 */

public class PopMoviesRepository implements MoviesRepository {

    private Context context;

    public PopMoviesRepository(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<MovieInfo>> getPopMoviesFromLocal() {

        /*MovieHelper.getInstance(context)
                .getWritableDatabase().query();*/

        return null;
    }

    @Override
    public Observable<List<MovieInfo>> getPopMoviesFromNet(final int page) {
        return Observable.fromCallable(new Func0<List<MovieInfo>>() {
            @Override
            public List<MovieInfo> call() {
                return MovieOkhttp.getMovieData(page);
            }
        }).subscribeOn(Schedulers.io());
    }
}
