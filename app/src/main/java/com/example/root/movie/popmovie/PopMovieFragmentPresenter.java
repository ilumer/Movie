package com.example.root.movie.popmovie;

import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.util.schedulers.BaseSchedulerProvider;


import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ilumer on 17-3-6.
 */

public class PopMovieFragmentPresenter implements PopMovieContract.Presenter {

    private CompositeSubscription subscription;
    private PopMovieContract.View view;
    private MovieRepository repository;
    private BaseSchedulerProvider schedulerProvider;
    private int page;
    private boolean isLoadingMore = false;


    public PopMovieFragmentPresenter(PopMovieContract.View view, MovieRepository repository, BaseSchedulerProvider schedulerProvider) {
        this.view = view;
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
        this.subscription = new CompositeSubscription();
        page = 1;
    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }

    /**
     *
     * @param page 需要获取的Movies的分页
     */
    private Observable<List<MovieInfo>> loadMoviesFromNet(int page){
        return repository.getPopMoviesFromNet(page)
                .subscribeOn(schedulerProvider.io());
    }

    @Override
    public void refreshMovies(){
        page = 1;
        subscription.add(loadMoviesFromNet(page)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new Action1<List<MovieInfo>>() {
                    @Override
                    public void call(List<MovieInfo> movies) {
                        view.replaceMovies(movies);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.stopRefreshing();
                        view.displayNoMovies();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        view.stopRefreshing();
                    }
                }));
    }

    @Override
    public void loadMoreMovies(){
        if (!isLoadingMore) {
            subscription.add(loadMoviesFromNet(++page)
                    .subscribeOn(schedulerProvider.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            isLoadingMore = true;
                            view.loadingMore();
                        }
                    })
                    .subscribeOn(schedulerProvider.ui())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(new Action1<List<MovieInfo>>() {
                        @Override
                        public void call(List<MovieInfo> movies) {
                            view.displayMovies(movies);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            view.stopLoadMore();
                            isLoadingMore = false;
                        }
                    }, new Action0() {
                        @Override
                        public void call() {
                            view.failLoadMore();
                            isLoadingMore = false;
                        }
                    }));
        }
    }

    @Override
    public void loadFromDb() {

    }

}

