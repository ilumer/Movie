package com.example.root.movie.activity;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.root.movie.fragment.RatedMovieFragment;
import com.example.root.movie.R;
import com.example.root.movie.ToolbarActivity;

public class RatedActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
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
