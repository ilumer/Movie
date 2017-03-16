package com.example.root.movie.detialmovie;

import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.util.schedulers.BaseSchedulerProvider;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ilumer on 17-3-16.
 */

public class DetailMovieFragmentPresenter implements DetailMovieContract.Presenter {

    private DetailMovieContract.View view;
    private BaseSchedulerProvider provider;
    private MovieRepository repository;
    private CompositeSubscription subscription;

    public DetailMovieFragmentPresenter(DetailMovieContract.View view,
                                        BaseSchedulerProvider provider,
                                        MovieRepository repository) {
        this.view = view;
        this.provider = provider;
        this.repository = repository;
        subscription = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }

    @Override
    public void loadDetailMovie(int id) {
        repository.getDetailMovie(id)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(new Action1<DetailMovie>() {
                    @Override
                    public void call(DetailMovie detailMovie) {
                        view.showDate(detailMovie.getReleaseDate());
                        view.showMovie(detailMovie.getBackdropPath());
                        view.showTitle(detailMovie.getTitle());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
