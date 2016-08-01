package com.example.root.movie.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.root.movie.fragment.RatedDetialFragment;
import com.example.root.movie.fragment.RatedMovieFragment;
import com.example.root.movie.R;
import com.example.root.movie.ToolbarActivity;

public class RatedActivity extends ToolbarActivity {

    @Override
    protected Fragment createNewFragment() {
        return new RatedMovieFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_without_toolbar;
    }

}
