package com.example.root.movie.detialmovie;

import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.util.schedulers.BaseSchedulerProvider;
import com.example.root.movie.util.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;

/**
 * Created by ilumer on 17-3-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DetailMovieFragmentPresenterTest {

    @Mock
    MovieRepository repository;
    @Mock
    DetailMovieContract.View view;

    DetailMovieContract.Presenter presenter;

    @Before
    public void setup(){
        BaseSchedulerProvider provider = new ImmediateSchedulerProvider();
        presenter = new DetailMovieFragmentPresenter(view,provider,repository);
    }

    @Test
    public void loadDetailMovieSuccess() throws Exception {
        DetailMovie movie = new DetailMovie();
        Mockito.when(repository.getDetailMovie(1111))
                .thenReturn(Observable.just(movie));

        presenter.loadDetailMovie(1111);

        Mockito.verify(view).showTitle(movie.getTitle());
        Mockito.verify(view).showMovie(movie.getBackdropPath());
        Mockito.verify(view).showDate(movie.getReleaseDate());
    }

}