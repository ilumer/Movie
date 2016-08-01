package com.example.root.movie.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by root on 16-7-24.
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;
    View mRootView;


    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null==mRootView){
            mRootView = inflater.inflate(getLayoutId(),container,false);
        }
        unbinder=ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    protected Unbinder getUnbinder(){
        return this.unbinder;
    }
}
