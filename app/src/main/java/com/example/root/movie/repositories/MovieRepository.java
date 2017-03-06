package com.example.root.movie.repositories;

import com.example.root.movie.dao.Movie;

import java.util.List;

/**
 * Created by ilumer on 17-3-6.
 */

public interface MovieRepository {
    List<Movie> getMovies();
}
