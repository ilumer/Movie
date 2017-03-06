package com.example.root.movie;

import com.example.root.movie.dao.Movie;
import com.example.root.movie.repositories.MovieRepository;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by ilumer on 17-3-6.
 */
public class MovieFragmentPresenterTest {

    @Test
    public void shouldPassMovieToView(){
        // given
        MoviesFragmentView view = new MockView();
        MovieRepository repository = new MockMovieRepository(true);

        // when
        MovieFragmentPresenter presenter = new MovieFragmentPresenter(view,repository);
        presenter.loadMovies();

        // then
        Assert.assertEquals(true,((MockView)view).displayMoviesWithMoviesCalled);

    }

    @Test
    public void shouldPassNoMovieToView(){
        MoviesFragmentView view = new MockView();
        MovieRepository repository = new MockMovieRepository(false);

        MovieFragmentPresenter presenter = new MovieFragmentPresenter(view,repository);
        presenter.loadMovies();

        Assert.assertEquals(true,((MockView) view).displayNoMoviesWithMoviesCalled);
    }


    private class MockView implements MoviesFragmentView{

        boolean displayMoviesWithMoviesCalled;
        boolean displayNoMoviesWithMoviesCalled;

        @Override
        public void displayMovies(List<Movie> movies) {
            displayMoviesWithMoviesCalled = true;
        }

        @Override
        public void displayNoMovies() {
            displayNoMoviesWithMoviesCalled = true;
        }
    }

    private class MockMovieRepository implements MovieRepository{

        private boolean returnSomeMovies;

        public MockMovieRepository(boolean returnSomeMovies) {
            this.returnSomeMovies = returnSomeMovies;
        }

        @Override
        public List<Movie> getMovies() {
            if (returnSomeMovies == true)
                return Arrays.asList(new Movie(),new Movie(),new Movie());
            else
                return Collections.emptyList();
        }
    }
}