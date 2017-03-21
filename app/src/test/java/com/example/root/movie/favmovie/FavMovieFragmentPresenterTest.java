package com.example.root.movie.favmovie;

import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.util.schedulers.BaseSchedulerProvider;
import com.example.root.movie.util.schedulers.ImmediateSchedulerProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import rx.Observable;

import static org.junit.Assert.*;

/**
 * Created by ilumer on 17-3-21.
 */
@RunWith(MockitoJUnitRunner.class)
public class FavMovieFragmentPresenterTest {

  @Mock FavMovieContract.View view;
  @Mock MovieRepository repository;
  FavMovieContract.Presenter presenter;

  @Before
  public void setup(){
    BaseSchedulerProvider provider = new ImmediateSchedulerProvider();
    presenter = new FavMovieFragmentPresenter(repository,view,provider);
  }

  @Test public void loadFavMovies() throws Exception {
    List<MovieInfo>list = new ArrayList<>();
    list.add(new MovieInfo());

    Mockito.when(repository.getFavMoviesFromLocal()).thenReturn(Observable.just(list));

    presenter.loadFavMovies();

    Mockito.verify(view).displayMovie(list);
  }

  @Test public void loadNoMovies() throws Exception {
    List<MovieInfo> list = new ArrayList<>();

    Mockito.when(repository.getFavMoviesFromLocal()).thenReturn(Observable.just(list));

    presenter.loadFavMovies();

    Mockito.verify(view).displayNoMovie();
  }
}