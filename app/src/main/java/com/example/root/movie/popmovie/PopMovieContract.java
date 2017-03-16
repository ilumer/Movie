package com.example.root.movie.popmovie;

import com.example.root.movie.BasePresenter;
import com.example.root.movie.model.MovieInfo;

import java.util.List;

/**
 * Created by ilumer on 17-3-10.
 */

public class PopMovieContract {
    interface View {
        void displayMovies(List<MovieInfo> movies);

        void displayNoMovies();

        void replaceMovies(List<MovieInfo> movies);

        void stopRefreshing();

        void stopLoadMore();

        void failLoadMore();

        void loadingMore();
    }

    interface Presenter extends BasePresenter{

        void refreshMovies();

        void loadMoreMovies();

        void loadFromDb();
    }
}
