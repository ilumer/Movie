package com.example.root.movie.repositories.impl;

import android.content.Context;

import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.net.MovieOkhttp;
import com.example.root.movie.repositories.MovieRepository;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ilumer on 17-3-7.
 */

public class MovieRepositoryImpl implements MovieRepository {

    private Context context;

    public MovieRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<MovieInfo>> getPopMoviesFromLocal() {

        /*MovieHelper.getInstance(context)
                .getWritableDatabase().query();*/

        return null;
    }

    @Override
    public Observable<List<MovieInfo>> getPopMoviesFromNet(int page) {
        return Observable.just(page)
                .flatMap(new Func1<Integer, Observable<List<MovieInfo>>>() {
                    @Override
                    public Observable<List<MovieInfo>> call(Integer integer) {
                        try{
                            List<MovieInfo> movies = MovieOkhttp.getMovieData(integer);
                            return Observable.just(movies);
                        }catch (IOException e){
                            return Observable.error(e);
                        }
                    }
                });
    }

    @Override
    public Observable<DetailMovie> getDetailMovie(int id) {
        return Observable.just(id)
                .flatMap(new Func1<Integer, Observable<DetailMovie>>() {
                    @Override
                    public Observable<DetailMovie> call(Integer id) {
                        try{
                            DetailMovie movie = MovieOkhttp.getDetialMovieInfo(id);
                            return Observable.just(movie);
                        }catch (IOException e){
                            return Observable.error(e);
                        }
                    }
                });
    }
}
