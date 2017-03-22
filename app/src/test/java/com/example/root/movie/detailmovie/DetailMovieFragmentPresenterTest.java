package com.example.root.movie.detailmovie;

import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.Trailers;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.util.schedulers.BaseSchedulerProvider;
import com.example.root.movie.util.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ilumer on 17-3-16.
 */
@RunWith(MockitoJUnitRunner.class) public class DetailMovieFragmentPresenterTest {

  @Mock MovieRepository repository;
  @Mock DetailMovieContract.View view;

  public static final int detailId = 1111;

  public static final String imdbId = "tt1111";

  //http://stackoverflow.com/questions/18514033/create-a-mocked-list-by-mockito
  // not mock list;

  DetailMovieContract.Presenter presenter;

  @Before public void setup() {
    BaseSchedulerProvider provider = new ImmediateSchedulerProvider();
    presenter = new DetailMovieFragmentPresenter(view, provider, repository);
  }

  @Test public void loadDetailMovieSuccess() throws Exception {
    DetailMovie movie = new DetailMovie();
    List<Trailers.Trailer> m = new ArrayList<>();
    Mockito.when(repository.getDetailMovie(detailId)).thenReturn(Observable.just(movie));
    Mockito.when(repository.checkFavMovieById(detailId)).thenReturn(Observable.just(true));

    presenter.loadDetailMovie(detailId);

    Mockito.verify(view).showTitle(movie.getTitle());
    Mockito.verify(view).showMovieBackdrop(movie.getBackdropPath());
    Mockito.verify(view).showDate(movie.getReleaseDate());
  }

  @Test public void loadDetailMovieFail() throws Exception {
    Mockito.when(repository.getDetailMovie(detailId))
        .thenReturn(Observable.<DetailMovie>just(null));
    // 泛型需要确定类型参数
    Mockito.when(repository.checkFavMovieById(detailId)).thenReturn(Observable.just(true));

    List<Trailers.Trailer> list = new ArrayList<>();

    presenter.loadDetailMovie(detailId);

    Mockito.verify(view).failLoad();
    Mockito.verify(view, Mockito.never()).failLoadTrailers();
    Mockito.verify(view, Mockito.never()).showTrailer(list);
  }

  @Test public void loadTrailersSuccess() throws Exception {
    List<Trailers.Trailer> list = new ArrayList<>();
    DetailMovie movie = new DetailMovie();
    movie.setImdbId(imdbId);
    Observable<DetailMovie> data = Observable.just(movie);
    Mockito.when(repository.getMovieTrailers(imdbId)).thenReturn(Observable.just(list));

    presenter.loadTrailer(data);

    Mockito.verify(view).showTrailer(list);
  }

  @Test public void loadTrailersFail() throws Exception {
    DetailMovie movie = new DetailMovie();
    movie.setImdbId(imdbId);
    Observable<DetailMovie> data = Observable.just(movie);
    Mockito.when(repository.getMovieTrailers(imdbId))
        .thenReturn(Observable.<List<Trailers.Trailer>>error(new IOException()));

    presenter.loadTrailer(data);
    Mockito.verify(view).failLoadTrailers();
  }

  @Test public void loadFavMovie() throws Exception {
    Mockito.when(repository.getDetailMovie(detailId)).thenReturn(Observable.just(new DetailMovie()));
    Mockito.when(repository.checkFavMovieById(detailId)).thenReturn(Observable.just(true));

    presenter.loadDetailMovie(detailId);

    Mockito.verify(view).showFavTag();
  }
}