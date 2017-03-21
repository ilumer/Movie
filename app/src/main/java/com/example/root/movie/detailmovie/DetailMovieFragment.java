package com.example.root.movie.detailmovie;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.movie.R;
import com.example.root.movie.adapter.VideoAdapter;
import com.example.root.movie.helper.IMDBHelper;
import com.example.root.movie.model.Trailers;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.repositories.impl.MovieRepositoryImpl;
import com.example.root.movie.util.Injection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ilumer on 17-3-16.
 */

public class DetailMovieFragment extends Fragment implements DetailMovieContract.View {

  public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
  Unbinder unbinder;
  DetailMovieContract.Presenter presenter;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.background_picture) ImageView backgroundMovie;
  @BindView(R.id.poster) ImageView posterMovie;
  @BindView(R.id.release_date) TextView releaseDate;
  @BindView(R.id.movie_title) TextView movieTitle;
  @BindView(R.id.user_score) TextView userScore;
  @BindView(R.id.movie_overview) TextView movieOverView;
  @BindView(R.id.movie_trailer) RecyclerView videos;
  @BindView(R.id.favorite) FloatingActionButton favorite;
  @BindString(R.string.movie_user_score) String userScoreFormat;
  @BindString(R.string.movie_release_date) String movieReleaseDate;
  @BindString(R.string.app_name) String appName;

  LinearLayoutManager manager;
  VideoAdapter videoAdapter;
  List<Trailers.Trailer> trailers = new ArrayList<>();

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    if (Build.VERSION.SDK_INT >= 21) {
      toolbar.setElevation(0);
    }
    toolbar.setTitle(appName);
    manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    videoAdapter = new VideoAdapter(trailers);
    videos.setAdapter(videoAdapter);
    videos.setLayoutManager(manager);
    videos.setNestedScrollingEnabled(false);
    MovieRepository repository = new MovieRepositoryImpl(getActivity().getApplicationContext());
    presenter =
        new DetailMovieFragmentPresenter(this, Injection.provideSchedulerProvider(), repository);
    int id = getArguments().getInt(EXTRA_MOVIE_ID);
    presenter.loadDetailMovie(id);
  }

  @Override public void onStop() {
    super.onStop();
    presenter.unSubscribe();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  public static DetailMovieFragment newInstance(int movieId) {
    Bundle args = new Bundle();
    args.putInt(EXTRA_MOVIE_ID, movieId);
    DetailMovieFragment fragment = new DetailMovieFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void showMovieBackdrop(String url) {
    Glide.with(this).load(IMDBHelper.getImageBsUri(getActivity(), url)).into(backgroundMovie);
  }

  @Override public void showTitle(String str) {
    movieTitle.setText(str);
  }

  @Override public void showDate(String date) {
    releaseDate.setText(String.format(movieReleaseDate, date));
  }

  @Override public void showTrailer(List<Trailers.Trailer> trailerList) {
    trailers.addAll(trailerList);
    videoAdapter.notifyDataSetChanged();
  }

  @Override public void failLoad() {

  }

  @Override public void failLoadTrailers() {

  }

  @Override public void showOverView(String overView) {
    movieOverView.setText(overView);
  }

  @OnClick(R.id.favorite) void submit() {
    favorite.setImageResource(R.drawable.ic_favorite_border);
  }

  @Override public void showUserScore(double score) {
    userScore.setText(String.format(userScoreFormat,score));
  }

  @Override public void loadMoviePost(String url) {
    Glide.with(this)
        .load(IMDBHelper.getImageBsUri(getActivity(),url))
        .into(posterMovie);
  }
}
