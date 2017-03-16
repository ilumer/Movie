package com.example.root.movie.popmovie;

import com.example.root.movie.model.MovieInfo;
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
import java.util.List;

import rx.Observable;


/**
 * Created by ilumer on 17-3-14.
 */
@RunWith(MockitoJUnitRunner.class)
public class PopMovieFragmentPresenterTest {

    @Mock
    MovieRepository repository;
    @Mock
    PopMovieContract.View view;
    @Mock
    List<MovieInfo> movies;

    PopMovieContract.Presenter presenter;


    @Before
    public void setup(){
        BaseSchedulerProvider provider = new ImmediateSchedulerProvider();
        presenter = new PopMovieFragmentPresenter(view,repository,provider);
    }

    @Test
    public void refreshMovies(){
        Mockito.when(repository.getPopMoviesFromNet(1))
                .thenReturn(Observable.just(movies));

        presenter.refreshMovies();

        Mockito.verify(view).replaceMovies(movies);
    }

    @Test
    public void loadMoreMovies(){
        Mockito.when(repository.getPopMoviesFromNet(2))
                .thenReturn(Observable.just(movies));

        presenter.loadMoreMovies();

        Mockito.verify(view).displayMovies(movies);
    }

    @Test
    public void getPopMoviesFromNetError() {
        Mockito.when(repository.getPopMoviesFromNet(1))
                .thenReturn(Observable.<List<MovieInfo>>error(new IOException()));

        presenter.refreshMovies();

        Mockito.verify(view).displayNoMovies();
    }

}