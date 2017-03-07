package com.example.root.movie.repositories;

import com.example.root.movie.data.source.local.MovieContract;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.MovieInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by ilumer on 17-3-6.
 */

public interface MoviesRepository {

    Observable<List<MovieInfo>> getPopMoviesFromLocal();

    Observable<List<MovieInfo>> getPopMoviesFromNet(int page);
}
