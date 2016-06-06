package com.example.root.movie;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends BaseActivity {
    @Override
    protected Fragment createNewFragment() {
        return new MainFragment();
    }
}
