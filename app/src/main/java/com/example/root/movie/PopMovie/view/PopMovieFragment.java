package com.example.root.movie.PopMovie.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.movie.MainActivity;
import com.example.root.movie.R;
import com.example.root.movie.activity.LoginActivity;
import com.example.root.movie.data.source.local.MovieContract;
import com.example.root.movie.model.ControlPage;
import com.example.root.movie.model.FragmentCallback;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.model.rvadapter.MovieAdapter;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.RecyclerItemClickListener;
import com.example.root.movie.PopMovie.presenter.PopMovieFragmentPresenter;
import com.example.root.movie.repositories.impl.PopMoviesRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PopMovieFragment extends Fragment implements ControlPage , MoviesFragmentView {
    public static final String TAG = PopMovieFragment.class.getSimpleName();

    @BindView(R.id.movie_recyclerView)
    RecyclerView movieList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindString(R.string.toast_notInternet)
    String toastMessage;

    List<MovieInfo> mList = new ArrayList<>();
    MovieAdapter adapter = null;
    GridLayoutManager gridLayoutManager=
            new GridLayoutManager(getActivity(),2);
    private FragmentCallback mcallbacks;
    private Unbinder unbinder;
    private PopMovieFragmentPresenter presenter;
    public int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        unbinder=ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNetMovies();
            }
        });

        movieList.setHasFixedSize(true);
        movieList.setLayoutManager(gridLayoutManager);
        adapter = new MovieAdapter(mList,getContext());
        movieList.setAdapter(adapter);
        movieList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mcallbacks.selectdMovie(adapter.getItem(position));
            }
        }));
        movieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0) {
                    if (!swipeRefresh.isRefreshing()) {
                        int visiableItem = recyclerView.getChildCount();
                        int totalItemCount = gridLayoutManager.getItemCount();
                        int firstVisiableItem = gridLayoutManager.findFirstVisibleItemPosition();
                        if ((visiableItem + firstVisiableItem) >= totalItemCount) {
                            //loadMore();
                        }
                    }
                }
            }
        });

        presenter = new PopMovieFragmentPresenter(this,new PopMoviesRepository(getActivity().getApplicationContext()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu,menu);
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
                Log.e("sort","average");
                return true;
            }
            case R.id.login:{
                Intent i = new Intent(getActivity(),LoginActivity.class);
                getActivity().startActivityForResult(i,1);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mcallbacks = (FragmentCallback) activity;
        }
    }

    @Override
    public void add() {
        page++;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void backOriginalValue() {
        page = 1;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mcallbacks = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void displayMovies(List<MovieInfo> movies) {
        mList.addAll(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayNoMovies() {

    }
}
