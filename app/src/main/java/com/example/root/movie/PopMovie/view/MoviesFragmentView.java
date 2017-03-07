package com.example.root.movie.PopMovie.view;

import com.example.root.movie.data.source.local.MovieContract;
import com.example.root.movie.model.MovieInfo;

import java.util.List;

/**
 * Created by ilumer on 17-3-6.
 */

public interface MoviesFragmentView {
    void displayMovies(List<MovieInfo> movies);

    void displayNoMovies();
}
