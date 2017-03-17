package com.example.root.movie.detailmovie;

import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.Trailers;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.util.rx.Funcs;
import com.example.root.movie.util.schedulers.BaseSchedulerProvider;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
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
        this.subscription = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }

    @Override
    public void loadDetailMovie(int id) {
        Observable<DetailMovie> data = repository.getDetailMovie(id)
                .subscribeOn(provider.io())
                .share();
        // hot Observable

        data.filter(new Func1<DetailMovie, Boolean>() {
            @Override
            public Boolean call(DetailMovie detailMovie) {
                return detailMovie == null;
            }
        }).observeOn(provider.ui())
                .subscribe(new Action1<DetailMovie>() {
                    @Override
                    public void call(DetailMovie detailMovie) {
                        view.failLoad();
                    }
                });
        //load fail

        Observable<DetailMovie> success = data.filter(Funcs.not(new Func1<DetailMovie, Boolean>() {
            @Override
            public Boolean call(DetailMovie detailMovie) {
                return detailMovie == null;
            }
        }));

        success.observeOn(provider.ui())
                .subscribe(new Action1<DetailMovie>() {
                    @Override
                    public void call(DetailMovie detailMovie) {
                        view.showDate(detailMovie.getReleaseDate());
                        view.showMovie(detailMovie.getBackdropPath());
                        view.showTitle(detailMovie.getTitle());
                    }
                });

        subscription.add(loadTrailer(success));
    }

    @Override
    public Subscription loadTrailer(Observable<DetailMovie> observable){
        return observable.flatMap(new Func1<DetailMovie, Observable<List<Trailers.Trailer>>>() {
            @Override
            public Observable<List<Trailers.Trailer>> call(DetailMovie detailMovie) {
                return repository.getMovieTrailers(detailMovie.getImdbId());
            }
        }).observeOn(provider.ui())
                .subscribe(new Action1<List<Trailers.Trailer>>() {
                    @Override
                    public void call(List<Trailers.Trailer> trailers) {
                        view.showTrailer(trailers);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.failLoadTrailers();
                    }
                });
    }
}
