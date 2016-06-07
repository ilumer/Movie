package com.example.root.movie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.root.movie.Net.DBAPI;
import com.example.root.movie.Net.MovieOkhttp;
import com.example.root.movie.model.DetialMovie;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetialFragment extends Fragment {
    public static final String EXTRA_ID = "com.example.root.movie.ID";
    public static final String EXTRA_DETIAL_MOVIE = "com.example.root.movie.DETIAL_MOVIE";
    @BindView(R.id.movie_name)
    TextView movieName;
    @BindView(R.id.movie_overview)
    TextView movieOverview;
    @BindView(R.id.movie_imageView)
    ImageView movieImageView;
    @BindView(R.id.release_date)
    TextView release_date;
    @BindView(R.id.movie_runtime)
    TextView runtime;
    @BindView(R.id.vote_average)
    TextView vote_average;
    @BindString(R.string.movie_vote_helper)
    String voteHelper;
    @BindString(R.string.movie_runtime_unit)
    String runtimeUnit;
    @BindString(R.string.toast_notInternet)
    String toastMessage;
    DetialMovie tempData = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detial,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //not use toolbar
        if (savedInstanceState!=null){
            if ((tempData =savedInstanceState.getParcelable(EXTRA_DETIAL_MOVIE))!=null){
                UpdateUI(tempData);
            }
        }
        int id = getArguments().getInt(EXTRA_ID,10);
        new AsyncUpdateUI().execute(id);
        Log.e("TAG","onViewCreated");
    }

    public static Fragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt(EXTRA_ID,id);
        DetialFragment detialFragment = new
                DetialFragment();
        detialFragment.setArguments(args);
        return detialFragment;
    }

    public void UpdateUI(DetialMovie detialMovie){
        movieName.setText(detialMovie.getOriginal_title());
        Glide.with(this).load(DBAPI.BASEIMAGE_URI+detialMovie.getPoster_path())
                .into(movieImageView);
        release_date.setText(detialMovie.getRelease_date());
        runtime.setText(String.format(runtimeUnit,detialMovie.getRuntime()));
        vote_average.setText(String.format(voteHelper,detialMovie.getVote_average()));
        movieOverview.setText(detialMovie.getOverview());
    }

    public class AsyncUpdateUI extends AsyncTask<Integer,Void,DetialMovie>{
        @Override
        protected DetialMovie doInBackground(Integer... id) {
            return new MovieOkhttp(getActivity()).getDetialMovieInfo(id[0]);
        }

        @Override
        protected void onPostExecute(DetialMovie detialMovie) {
            super.onPostExecute(detialMovie);
            if (detialMovie!=null) {
                tempData = detialMovie;
                UpdateUI(detialMovie);
            }else {
                Toast.makeText
                        (getActivity(),toastMessage,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_DETIAL_MOVIE, tempData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destory","destory");
    }
}
