package com.example.root.movie;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.root.movie.DAO.FavDAO;
import com.example.root.movie.Helper.MovieHelper;
import com.example.root.movie.Net.DBAPI;
import com.example.root.movie.Net.MovieOkhttp;
import com.example.root.movie.model.AccountFavourite;
import com.example.root.movie.model.DetialMovie;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.TrailerAdapter;
import com.example.root.movie.model.TrailerAsyncloader;
import com.example.root.movie.model.Trailers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetialFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<Trailers.ResultsBean>>{
    public static final String EXTRA_MOVIE_RESULTBEAN = "com.example.root.movie.MOVIERESULTBEAN";
    public static final String EXTRA_DETAIL_MOVIE = "com.example.root.movie.DETIAL_MOVIE";
    public static final String EXTRA_ID = "com.example.root.movie.ID";
    public static final String TAG = DetialFragment.class.getSimpleName();
    @BindView(R.id.trailer_show)
    RecyclerView trailerRv;
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
    @BindView(R.id.favorites)
    Button favMovie;
    @BindView(R.id.movie_reviews)
    Button movie_reviews;
    @BindString(R.string.movie_vote_helper)
    String voteHelper;
    @BindString(R.string.movie_runtime_unit)
    String runtimeUnit;
    @BindString(R.string.toast_notInternet)
    String toastMessage;
    @BindString(R.string.remove_favourite_movie)
    String removeFav;

    DetialMovie tempData = null;
    List<Trailers.ResultsBean> mList = new ArrayList<>();
    TrailerAdapter mtrailers ;
    SQLiteDatabase database;
    AccountFavourite accountFavourite;
    int id;
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
        mtrailers = new TrailerAdapter(mList,getActivity());
        trailerRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        trailerRv.setAdapter(mtrailers);
        if (savedInstanceState!=null){
            if ((tempData =savedInstanceState.getParcelable(EXTRA_DETAIL_MOVIE))!=null){
                UpdateUI(tempData);
            }
        }
        id = ((MovieData.ResultsBean) getArguments().getParcelable(EXTRA_MOVIE_RESULTBEAN)).getId();
        if (!checkFavouriteMovie(id)){
            favMovie.setText(removeFav);
        }
        getLoaderManager().initLoader(id,null,this).forceLoad();
        database= new MovieHelper(getActivity()).getReadableDatabase();
        //the same id
        new AsyncUpdateUI().execute(id);
        Log.e("TAG","onViewCreated");
    }

    public static Fragment newInstance(MovieData.ResultsBean r){
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_MOVIE_RESULTBEAN,r);
        DetialFragment detialFragment = new
                DetialFragment();
        detialFragment.setArguments(args);
        return detialFragment;
    }

    public void UpdateUI(DetialMovie detialMovie){
        movieName.setText(detialMovie.getOriginal_title());
        Glide.with(getActivity()).load(DBAPI.BASEIMAGE_URI+detialMovie.getPoster_path())
                .into(movieImageView);
        release_date.setText(detialMovie.getRelease_date());
        runtime.setText(String.format(runtimeUnit,detialMovie.getRuntime()));
        vote_average.setText(String.format(voteHelper,detialMovie.getVote_average()));
        movieOverview.setText(detialMovie.getOverview());
    }

    @OnClick(R.id.movie_reviews)
    public void startReviewActivity(){
        Intent i = new Intent(getActivity(),ReviewActivity.class);
        i.putExtra(EXTRA_ID,id);
        getActivity().startActivity(i);
    }

    @OnClick(R.id.favorites)
    public void addFavMovie(){
        final MovieData.ResultsBean m =getArguments().getParcelable(EXTRA_MOVIE_RESULTBEAN);
        if (checkFavouriteMovie(m.getId())) {
            AccountFavourite.getInstance().addFavMovie(m);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    FavDAO.InsertFavMovieToDB(database,m);
                }
            });
        }else {
            AccountFavourite.getInstance().removeFavMovie(m);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    FavDAO.deleteFavMovieByDB(database,m.getId());
                }
            });
        }
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

    public boolean checkFavouriteMovie(int id){
        boolean results = true;
        for (MovieData.ResultsBean i :AccountFavourite.getInstance().getmList()){
            if (i.getId()==id){
                results = false;
            }
        }
        return results;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_DETAIL_MOVIE, tempData);
    }

    @Override
    public Loader<List<Trailers.ResultsBean>> onCreateLoader(int id, Bundle args) {
        Log.e(TAG,"create");
        return new TrailerAsyncloader(getActivity(),id);
    }

    @Override
    public void onLoadFinished(Loader<List<Trailers.ResultsBean>> loader, List<Trailers.ResultsBean> data) {
        mList.addAll(data);
        mtrailers.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Trailers.ResultsBean>> loader) {
        mList.clear();
        mtrailers.notifyDataSetChanged();
    }

}
