package com.example.root.movie.detailmovie;

import com.example.root.movie.BasePresenter;
import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.model.Trailers;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by ilumer on 17-3-16.
 */

public class DetailMovieContract {
  interface View {

    void showMovieInfo(DetailMovie movie);

    void showTrailer(List<Trailers.Trailer> trailerList);

    void failLoad();

    void failLoadTrailers();

    void showFavTag();

    void showNotFavTag();

    void sendFavMovie(MovieInfo movie);
  }

  interface Presenter extends BasePresenter {
    void loadDetailMovie(int id);

    Subscription loadTrailer(Observable<DetailMovie> observable);

    void onClickFav();
  }
}
