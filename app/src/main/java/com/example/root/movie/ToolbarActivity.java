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

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mtoolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if (IsToolbar(getLayoutId())) {
            setSupportActionBar(mtoolbar);
        }
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
     switch (id){
         case R.layout.activity_with_toolbar:
             return true;
         case R.layout.activity_without_toolbar:
             return false;
         default:
             return false;
     }
    }

}
