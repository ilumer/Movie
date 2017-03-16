package com.example.root.movie.detialmovie;

import com.example.root.movie.BasePresenter;
import com.example.root.movie.model.Trailers;

import java.util.List;

/**
 * Created by ilumer on 17-3-16.
 */

public class DetailMovieContract {
    interface View{
        void showMovie(String url);

        void showTitle(String str);

        void showDate(String date);

        void showTrailer(List<Trailers.Trailer> trailerList);
    }

    interface Presenter extends BasePresenter{
        void loadDetailMovie(int id);
    }
}
