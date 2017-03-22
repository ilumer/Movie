package com.example.root.movie.detailmovie;

import com.example.root.movie.BasePresenter;
import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.Trailers;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by ilumer on 17-3-16.
 */

public class DetailMovieContract {
  interface View {
    void showMovieBackdrop(String url);

    void showTitle(String str);

    void showDate(String date);

    void showTrailer(List<Trailers.Trailer> trailerList);

    void showOverView(String overView);

    void failLoad();

    void failLoadTrailers();

    void showUserScore(double score);

    void loadMoviePost(String url);

    void showFavTag();

    void showNotFavTag();
  }

  interface Presenter extends BasePresenter {
    void loadDetailMovie(int id);

    Subscription loadTrailer(Observable<DetailMovie> observable);

    void onClickFav();
  }
}
