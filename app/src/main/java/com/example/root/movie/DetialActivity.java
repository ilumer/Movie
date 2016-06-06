package com.example.root.movie;

import android.support.v4.app.Fragment;

public class DetialActivity extends BaseActivity {

    @Override
    protected Fragment createNewFragment() {
        int id = getIntent().getIntExtra(MainFragment.ACTIVITY_EXTRA_ID,0);
        return DetialFragment.newInstance(id);
    }
}
