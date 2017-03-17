package com.example.root.movie;

import android.support.v4.app.Fragment;

import com.example.root.movie.detailmovie.DetailMovieFragment;

public class DetailActivity extends ToolbarActivity {
    @Override
    protected Fragment createNewFragment() {
        int movieId = getIntent().getIntExtra(DetailMovieFragment.EXTRA_MOVIE_ID,-1);
        return DetailMovieFragment.newInstance(movieId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_without_toolbar;
    }
}
