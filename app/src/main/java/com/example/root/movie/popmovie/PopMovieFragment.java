package com.example.root.movie.popmovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.movie.R;
import com.example.root.movie.adapter.MovieAdapter;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.repositories.impl.MovieRepositoryImpl;
import com.example.root.movie.util.ui.EndlessRecyclerOnScrollListener;
import com.example.root.movie.util.Injection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PopMovieFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener,PopMovieContract.View {
    public static final String TAG = PopMovieFragment.class.getSimpleName();

    @BindView(R.id.movie_recyclerView)
    RecyclerView movieList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindString(R.string.toast_notInternet)
    String toastMessage;

    List<MovieInfo> mList = new ArrayList<>();
    MovieAdapter movieAdapter = new MovieAdapter(mList);
    GridLayoutManager gridLayoutManager;
    private Unbinder unbinder;
    private PopMovieFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_main,container,false);
        unbinder=ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(this);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        movieList.setLayoutManager(gridLayoutManager);
        movieList.setAdapter(movieAdapter);
        movieList.setHasFixedSize(true);
        movieList.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                presenter.loadMoreMovies();
            }
        });
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return movieAdapter.getSpanSize(position);
            }
        });
        presenter = new PopMovieFragmentPresenter(this,new MovieRepositoryImpl(getActivity().getApplicationContext()), Injection.provideSchedulerProvider());
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
        onRefresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu,menu);
    }

    public static PopMovieFragment instance(){
      return new PopMovieFragment();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unSubscribe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort:{
                Collections.sort(mList, new Comparator<MovieInfo>() {
                    @Override
                    public int compare(MovieInfo m1, MovieInfo t1) {
                        return ((Double)m1.getVoteAverage()).compareTo(t1.getVoteAverage());
                    }
                });
                Collections.reverse(mList);
                return true;
            }
            case R.id.login:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void displayMovies(List<MovieInfo> movies) {
        mList.addAll(movies);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayNoMovies() {
    }

    @Override
    public void onRefresh() {
        presenter.refreshMovies();
    }

    @Override
    public void replaceMovies(List<MovieInfo> movies) {
        movieAdapter.replaceAll(movies);
    }

    @Override
    public void stopRefreshing() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void stopLoadMore() {
        movieAdapter.loadingEnd();
    }

    @Override
    public void failLoadMore() {
        movieAdapter.loadingError();
    }

    @Override
    public void loadingMore() {
        movieAdapter.loadingMore();
    }
}
