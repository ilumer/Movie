package com.example.root.movie;

import android.support.v4.app.Fragment;

import com.example.root.movie.fragment.RatedDetailFragment;
import com.example.root.movie.model.MovieData;

public class DetialActivity extends ToolbarActivity {

    @Override
    protected Fragment createNewFragment() {
        MovieData.ResultsBean m = getIntent().getParcelableExtra(DetialFragment.EXTRA_MOVIE_RESULTBEAN);
        return RatedDetailFragment.getInstance(m.getId());
        //return DetialFragment.newInstance(m);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_without_toolbar;
    }
}
