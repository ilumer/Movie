package com.example.root.movie;

import android.support.v4.app.Fragment;

import com.example.root.movie.fragment.RatedDetailFragment;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.MovieInfo;

public class DetialActivity extends ToolbarActivity {
    @Override
    protected Fragment createNewFragment() {
        MovieInfo m = getIntent().getParcelableExtra(RatedDetailFragment.EXTRA_ID);
        return RatedDetailFragment.getInstance(m.getId());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_without_toolbar;
    }
}
