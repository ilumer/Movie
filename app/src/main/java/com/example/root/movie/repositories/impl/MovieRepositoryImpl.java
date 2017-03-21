package com.example.root.movie.repositories.impl;

import android.content.Context;

import android.database.Cursor;
import com.example.root.movie.data.source.local.Db;
import com.example.root.movie.data.source.local.MovieContract;
import com.example.root.movie.data.source.local.MovieHelper;
import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.model.Trailers;
import com.example.root.movie.net.MovieOkhttp;
import com.example.root.movie.repositories.MovieRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by ilumer on 17-3-7.
 */

public class MovieRepositoryImpl implements MovieRepository {

  private Context context;
  private MovieHelper helper;

  public MovieRepositoryImpl(Context context) {
    this.context = context;
    helper = MovieHelper.getInstance(context);
  }

  @Override public Observable<List<MovieInfo>> getFavMoviesFromLocal() {
    return Observable.fromCallable(new Func0<Cursor>() {
      @Override public Cursor call() {
        return helper.getWritableDatabase().query(MovieContract.MovieDataBean.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null);
      }
    }).map(new Func1<Cursor, List<MovieInfo>>() {
      @Override public List<MovieInfo> call(Cursor cursor) {
        List<MovieInfo> list = new ArrayList<>();
        try{
          //需要的数据
          while (cursor.moveToNext()){
            MovieInfo info = new MovieInfo();
            info.setId(Db.getInt(cursor,MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_ID));
            info.setPosterPath(Db.getString(cursor,MovieContract.MovieDataBean.COLUMN_NAME_POSTER_PATH));
            list.add(info);
          }
        }finally {
          cursor.close();
        }
        return list;
      }
    });
  }

  @Override public Observable<List<MovieInfo>> getPopMoviesFromNet(int page) {
    return Observable.just(page).flatMap(new Func1<Integer, Observable<List<MovieInfo>>>() {
      @Override public Observable<List<MovieInfo>> call(Integer integer) {
        try {
          List<MovieInfo> movies = MovieOkhttp.getMovieData(integer);
          return Observable.just(movies);
        } catch (IOException e) {
          return Observable.error(e);
        }
      }
    });
  }

  @Override public Observable<DetailMovie> getDetailMovie(int id) {
    return Observable.just(id).flatMap(new Func1<Integer, Observable<DetailMovie>>() {
      @Override public Observable<DetailMovie> call(Integer id) {
        try {
          DetailMovie movie = MovieOkhttp.getDetialMovieInfo(id);
          return Observable.just(movie);
        } catch (IOException e) {
          return Observable.just(null);
        }
      }
    });
  }

  @Override public Observable<List<Trailers.Trailer>> getMovieTrailers(String imdbId) {
    return Observable.just(imdbId).flatMap(new Func1<String, Observable<List<Trailers.Trailer>>>() {
      @Override public Observable<List<Trailers.Trailer>> call(String id) {
        try {
          return Observable.just(MovieOkhttp.getTrailers(id));
        } catch (IOException e) {
          return Observable.error(e);
        }
      }
    });
  }
}
