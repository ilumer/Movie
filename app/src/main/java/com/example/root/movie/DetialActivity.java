package com.example.root.movie;

import android.support.v4.app.Fragment;

import com.example.root.movie.fragment.RatedDetailFragment;
import com.example.root.movie.model.MovieData;

public class DetialActivity extends ToolbarActivity {
    @Override
    protected Fragment createNewFragment() {
        MovieData.ResultsBean m = getIntent().getParcelableExtra(RatedDetailFragment.EXTRA_ID);
        return RatedDetailFragment.getInstance(m.getId());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_without_toolbar;
    }
}
