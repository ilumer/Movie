package com.example.root.movie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.movie.R;
import com.example.root.movie.api.model.RateInfo;
import com.example.root.movie.api.model.Rated;
import com.example.root.movie.helper.IMDBHelper;

import butterknife.BindView;

/**
 * Created by root on 16-7-27.
 */
public class RatedDetialFragment extends BaseFragment {
    public static final String EXTRA_INFO = "com.example.root.movie.EXTRA_INFO";
    public static final String TAG = RatedDetialFragment.class.getName();
    @BindView(R.id.movie_screen)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.release_date)
    TextView mdate;
    @BindView(R.id.movie_runtime)
    TextView mruntime;
    @BindView(R.id.Director)
    TextView mDirector;
    @BindView(R.id.Language)
    TextView mLanguage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.detial_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RateInfo info = getArguments().getParcelable(EXTRA_INFO);
        Glide.with(this)
                .load(IMDBHelper.getImageBsUri(getActivity(),info.getPoster_path()))
                .centerCrop()
                .into(imageView);
        mdate.setText(info.getRelease_date());
        mruntime.setText(info.getTitle());
        mDirector.setText(info.getVote_count()+"");
        mLanguage.setText(info.getVote_average()+"");
    }

    public static RatedDetialFragment getInstance(RateInfo info){
        Bundle arg = new Bundle();
        arg.putParcelable(EXTRA_INFO,info);
        RatedDetialFragment fragment = new
                RatedDetialFragment();
        fragment.setArguments(arg);
        return fragment;
    }
}
