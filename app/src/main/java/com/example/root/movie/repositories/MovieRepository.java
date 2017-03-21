package com.example.root.movie.repositories;

import com.example.root.movie.data.source.local.MovieContract;
import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.model.Trailers;

import java.util.List;

import rx.Observable;

/**
 * Created by ilumer on 17-3-6.
 */

public interface MovieRepository {

    Observable<List<MovieInfo>> getFavMoviesFromLocal();

    Observable<List<MovieInfo>> getPopMoviesFromNet(int page);

    Observable<DetailMovie> getDetailMovie(int id);

    Observable<List<Trailers.Trailer>> getMovieTrailers(String imdbId);

}
