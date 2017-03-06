package com.example.root.movie;

import com.example.root.movie.dao.Movie;

import java.util.List;

/**
 * Created by ilumer on 17-3-6.
 */

public interface MoviesFragmentView {
    void displayMovies(List<Movie> movies);

    void displayNoMovies();
}
