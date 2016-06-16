package com.example.root.movie;

import android.support.v4.app.Fragment;

import com.example.root.movie.model.MovieData;

public class DetialActivity extends BaseActivity {

    @Override
    protected Fragment createNewFragment() {
        MovieData.ResultsBean m = getIntent().getParcelableExtra(DetialFragment.EXTRA_MOVIE_RESULTBEAN);
        return DetialFragment.newInstance(m);
    }
}
