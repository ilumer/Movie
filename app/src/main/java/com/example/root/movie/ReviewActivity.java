package com.example.root.movie;

import android.support.v4.app.Fragment;

public class ReviewActivity extends ToolbarActivity {

    @Override
    protected Fragment createNewFragment() {
        return new ReviewFragment();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()==0) {
            super.onBackPressed();
        }else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_with_toolbar;
    }
}
