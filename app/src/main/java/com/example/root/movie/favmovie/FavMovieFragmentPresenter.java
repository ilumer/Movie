package com.example.root.movie.favmovie;

import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.util.rx.Funcs;
import com.example.root.movie.util.schedulers.BaseSchedulerProvider;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ilumer on 17-3-17.
 */

public class FavMovieFragmentPresenter implements FavMovieContract.Presenter {

  private MovieRepository repository;
  private FavMovieContract.View view;
  private BaseSchedulerProvider provider;
  private CompositeSubscription subscription;
  private Func1<List<MovieInfo>,Boolean> filterEmpty = new Func1<List<MovieInfo>, Boolean>() {
    @Override public Boolean call(List<MovieInfo> movies) {
      return movies.size() > 0;
    }
  };

  public FavMovieFragmentPresenter(MovieRepository repository, FavMovieContract.View view,
      BaseSchedulerProvider provider) {
    this.repository = repository;
    this.view = view;
    this.provider = provider;
    this.subscription = new CompositeSubscription();
  }

  @Override public void unSubscribe() {
    subscription.clear();
  }

  @Override public void loadFavMovies() {
    Observable<List<MovieInfo>> result =
        repository.getFavMoviesFromLocal().subscribeOn(provider.io()).share();
    subscription.add(result.filter(filterEmpty).observeOn(provider.ui()).subscribe(new Action1<List<MovieInfo>>() {
      @Override public void call(List<MovieInfo> movieInfos) {
        view.displayMovie(movieInfos);
      }
    }));
    subscription.add(result.filter(Funcs.not(filterEmpty)).observeOn(provider.ui()).subscribe(new Action1<List<MovieInfo>>() {
      @Override public void call(List<MovieInfo> movies) {
        view.displayNoMovie();
      }
    }));
  }
}
