package com.example.root.movie.favmovie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.root.movie.R;
import com.example.root.movie.adapter.MovieAdapter;
import com.example.root.movie.detailmovie.DetailMovieFragment;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.repositories.impl.MovieRepositoryImpl;
import com.example.root.movie.util.Injection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilumer on 17-3-17.
 */

public class FavMovieFragment extends Fragment implements FavMovieContract.View {

  private Unbinder unbinder;
  private List<MovieInfo> list;
  private MovieAdapter adapter;
  private GridLayoutManager layoutManager;
  private FavMovieContract.Presenter presenter;
  private MovieRepository repository;
  private BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      MovieInfo info =  intent.getParcelableExtra(DetailMovieFragment.EXTRA_MOVIE);
      if(!list.remove(info)){
        list.add(info);
      }
      if (list.size()>0){
        empty.setVisibility(View.GONE);
      }else {
        empty.setVisibility(View.VISIBLE);
      }
      adapter.notifyDataSetChanged();
    }
  };
  @BindView(R.id.fav_movie) RecyclerView favMovie;
  @BindView(R.id.empty_view) TextView empty;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_fav,container,false);
    unbinder = ButterKnife.bind(this,view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    favMovie.setHasFixedSize(true);
    layoutManager = new GridLayoutManager(getActivity(),2);
    favMovie.setLayoutManager(layoutManager);
    list = new ArrayList<>();
    adapter = new MovieAdapter(list);
    favMovie.setLayoutManager(layoutManager);
    favMovie.setAdapter(adapter);
    repository = new MovieRepositoryImpl(getActivity().getApplicationContext());
    presenter = new FavMovieFragmentPresenter(repository,this,Injection.provideSchedulerProvider());
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,new IntentFilter(DetailMovieFragment.INTENT_MOVIE_FILTER));
    presenter.loadFavMovies();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.unSubscribe();
    unbinder.unbind();
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
  }

  @Override public void displayMovie(List<MovieInfo> movies) {
    adapter.replaceAll(movies);
  }

  @Override public void displayNoMovie() {
    empty.setText("no movie");
  }

  public static FavMovieFragment instance(){
    return new FavMovieFragment();
  }

}
