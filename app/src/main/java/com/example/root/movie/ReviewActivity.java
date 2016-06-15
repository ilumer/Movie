package com.example.root.movie;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReviewActivity extends BaseActivity {

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
}
