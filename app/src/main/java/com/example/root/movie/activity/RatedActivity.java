package com.example.root.movie.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.root.movie.fragment.RatedMovieFragment;
import com.example.root.movie.R;
import com.example.root.movie.ToolbarActivity;

public class RatedActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createNewFragment() {
        return new RatedMovieFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_without_toolbar;
    }

}
