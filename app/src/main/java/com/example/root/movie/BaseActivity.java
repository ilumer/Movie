package com.example.root.movie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract Fragment createNewFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = createNewFragment();
        fragmentManager.
                beginTransaction().
                add(R.id.fragment_container,fragment).commit();
    }
}
