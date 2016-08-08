package com.example.root.movie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.movie.R;
import com.example.root.movie.handler.CommonHandler;
import com.example.root.movie.helper.IMDBHelper;
import com.example.root.movie.loader.MovieDataloader;
import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.ReviewsModel;
import com.example.root.movie.model.Trailers;
import com.example.root.movie.model.rvadapter.ReviewAdapter;
import com.example.root.movie.model.rvadapter.TrailerAdapter;
import com.example.root.movie.net.MovieOkhttp;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by root on 16-7-27.
 * detail data
 */
public class RatedDetailFragment extends BaseFragment
        implements LoaderManager.LoaderCallbacks<DetailMovie>{
    public static final String EXTRA_ID = "com.example.root.movie.EXTRA_ID";
    //public static final String TAG = RatedDetailFragment.class.getName();
    @BindView(R.id.movie_screen)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.release_date)
    TextView mdate;
    @BindView(R.id.title)
    TextView mtitle;
    @BindView(R.id.vote_average)
    TextView mvote;
    @BindView(R.id.movie_overview)
    TextView moverview;
    @BindView(R.id.trailer)
    RecyclerView mtrailer;
    @BindView(R.id.review)
    RecyclerView mreview;
    @BindString(R.string.movie_release_date)
    String dateformat;
    @BindString(R.string.movie_vote_helper)
    String voteformat;
    int movieId;
    CommonHandler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        movieId = getArguments().getInt(EXTRA_ID);
        handler = new CommonHandler(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.detial_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar()==null){
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getLoaderManager().initLoader(0,null,this).forceLoad();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                getActivity().onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static RatedDetailFragment getInstance(int id){
        Bundle arg = new Bundle();
        arg.putInt(EXTRA_ID,id);
        RatedDetailFragment fragment = new
                RatedDetailFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    public void updateUI(DetailMovie movie){
        Glide.with(this)
                .load(IMDBHelper.getImageBsUri(getActivity(),movie.getPoster_path()))
                .centerCrop()
                .into(imageView);
        mtitle.setText(movie.getTitle());
        moverview.setText(movie.getOverview());
        mdate.setText(String.format(dateformat,movie.getRelease_date()));
        mvote.setText(String.format(voteformat,movie.getVote_average()));
        MovieOkhttp.getReviews(handler, movieId);
        MovieOkhttp.getTrailers(handler, movieId);
    }

    public void updateReviews(ReviewsModel model){
        ReviewAdapter adapter = new ReviewAdapter(model.getResults());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mreview.setLayoutManager(linearLayoutManager);
        mreview.setAdapter(adapter);
        mreview.setNestedScrollingEnabled(false);
    }

    public void updateTrailers(Trailers trailers){
        TrailerAdapter adapter = new TrailerAdapter(trailers.getResults(),getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mtrailer.setLayoutManager(linearLayoutManager);
        mtrailer.setAdapter(adapter);
        mtrailer.setNestedScrollingEnabled(false);
    }

    @Override
    public Loader<DetailMovie> onCreateLoader(int id, Bundle args) {
        return new MovieDataloader(getActivity(),movieId);
    }

    @Override
    public void onLoadFinished(Loader<DetailMovie> loader, DetailMovie data) {
        updateUI(data);
    }

    @Override
    public void onLoaderReset(Loader<DetailMovie> loader) {

    }

}
