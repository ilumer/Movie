package com.example.root.movie;

import com.example.root.movie.dao.Movie;
import com.example.root.movie.repositories.MovieRepository;

import java.util.List;

/**
 * Created by ilumer on 17-3-6.
 */

public class MovieFragmentPresenter {

    private MoviesFragmentView view;
    private MovieRepository repository;

    public MovieFragmentPresenter(MoviesFragmentView view, MovieRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void loadMovies() {
        List<Movie> movieList = repository.getMovies();
        if( movieList.isEmpty()){
            view.displayNoMovies();
        }
        view.displayMovies(movieList);
    }
}

