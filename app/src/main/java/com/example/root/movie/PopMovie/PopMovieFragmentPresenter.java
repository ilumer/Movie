package com.example.root.movie.PopMovie;

import android.util.Log;

import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.repositories.MoviesRepository;


import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ilumer on 17-3-6.
 */

public class PopMovieFragmentPresenter implements PopMovieContract.Presenter {

    private CompositeSubscription subscription;
    private PopMovieContract.View view;
    private MoviesRepository repository;
    private int page;
    private boolean isLoadingMore = false;


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
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
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
            if (!isLoadingMore) {
                subscription.add(loadMoviesFromNet(++page)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                isLoadingMore = true;
                                view.loadingMore();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<MovieInfo>>() {
                            @Override
                            public void onCompleted() {
                                view.stopLoadMore();
                                isLoadingMore = false;
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.failLoadMore();
                                isLoadingMore = false;
                            }

                            @Override
                            public void onNext(List<MovieInfo> movies) {
                                view.displayMovies(movies);
                            }

                        }));
            }
        }
    }

    /**
     *
     * @param page 需要获取的Movies的分页
     */
    private Observable<List<MovieInfo>> loadMoviesFromNet(int page){
        return repository.getPopMoviesFromNet(page);
    }

    @Override
    public void loadFromDb() {

    }
}

