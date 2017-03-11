package com.example.root.movie.PopMovie;

import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.repositories.MoviesRepository;


import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
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
    public void loadMoreFromNet(boolean loadFirst) {
        if (loadFirst){
            page = 1;
            subscription.add(loadMoviesFromNet(page)
                    .subscribe(new Action1<List<MovieInfo>>() {
                        @Override
                        public void call(List<MovieInfo> movies) {
                            view.replaceMovies(movies);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            view.stopRefreshing();
                        }
                    }, new Action0() {
                        @Override
                        public void call() {
                            view.stopRefreshing();
                        }
                    }));
        }else {
            view.loadingMore();
            subscription.add(loadMoviesFromNet(++page)
                    .subscribe(new Action1<List<MovieInfo>>() {
                        @Override
                        public void call(List<MovieInfo> movies) {
                            view.displayMovies(movies);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            view.stopLoadingMore();
                        }
                    }, new Action0() {
                        @Override
                        public void call() {
                            view.stopLoadingMore();
                        }
                    }));
        }
    }

    /**
     *
     * @param page 需要获取的Movies的分页
     */
    private Observable<List<MovieInfo>> loadMoviesFromNet(int page){
        return repository.getPopMoviesFromNet(page)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void loadFromDb() {

    }
}

