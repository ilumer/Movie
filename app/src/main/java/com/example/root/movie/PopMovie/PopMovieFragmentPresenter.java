package com.example.root.movie.PopMovie;

import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.repositories.MoviesRepository;


import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ilumer on 17-3-6.
 */

public class PopMovieFragmentPresenter implements PopMovieContract.Presenter {

    private CompositeSubscription subscription;
    private PopMovieContract.View view;
    private MoviesRepository repository;
    private int page;


    public PopMovieFragmentPresenter(PopMovieContract.View view, MoviesRepository repository) {
        this.view = view;
        this.repository = repository;
        subscription = new CompositeSubscription();
        page = 1;
    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }

    @Override
    public void refresh() {
        page = 1;
        subscription.add(repository.getPopMoviesFromNet(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MovieInfo>>() {
                    @Override
                    public void call(List<MovieInfo> movies) {
                        view.replaceMovies(movies);
                        view.stopRefreshing();
                    }
                }));
    }

    @Override
    public void refreshFailed() {
        view.displayNoMovies();
    }

    @Override
    public void loadMore() {
        view.loadingMore();
        subscription.add(repository.getPopMoviesFromNet(++page)
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<MovieInfo>>() {
                    @Override
                    public void call(List<MovieInfo> movies) {
                        view.displayMovies(movies);
                    }
                }));
    }

    @Override
    public void loadFailed() {

    }

}

