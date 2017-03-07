package com.example.root.movie.PopMovie.presenter;

import com.example.root.movie.PopMovie.view.MoviesFragmentView;
import com.example.root.movie.data.source.local.MovieContract;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.repositories.MoviesRepository;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ilumer on 17-3-6.
 */

public class PopMovieFragmentPresenter {

    private MoviesFragmentView view;
    private MoviesRepository repository;
    private int page;

    public PopMovieFragmentPresenter(MoviesFragmentView view, MoviesRepository repository) {
        this.view = view;
        this.repository = repository;
        page = 1;
    }

    public void loadNetMovies() {
        repository.getPopMoviesFromNet(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MovieInfo>>() {
                    @Override
                    public void call(List<MovieInfo> movieInfos) {
                        view.displayMovies(movieInfos);
                    }
                });
    }

    public void loadLocalMovies() {

    }

}

