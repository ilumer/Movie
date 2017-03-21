package com.example.root.movie.favmovie;

import com.example.root.movie.BasePresenter;
import com.example.root.movie.model.MovieInfo;
import java.util.List;

/**
 * Created by ilumer on 17-3-17.
 */

public class FavMovieContract {
  interface View{
    void displayMovie(List<MovieInfo> movies);

    void displayNoMovie();
  }

  interface Presenter extends BasePresenter{
    void loadFavMovies();
  }
}
