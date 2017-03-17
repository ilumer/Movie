package com.example.root.movie;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class ToolbarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_without_toolbar);
        ButterKnife.bind(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment==null) {
            fragment = createNewFragment();
            fragmentManager.
                    beginTransaction().
                    add(R.id.fragment_container, fragment).commit();
        }

    }

    protected abstract Fragment createNewFragment();

    protected abstract int getLayoutId();

    protected boolean IsToolbar(int id){
        return true;
    }

}
