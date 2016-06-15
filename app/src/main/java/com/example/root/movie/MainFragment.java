package com.example.root.movie;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.example.root.movie.model.MovieAdapter;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.Net.MovieOkhttp;
import com.example.root.movie.model.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainFragment extends Fragment {
    public static final String ACTIVITY_EXTRA_ID = "com.example.root.movie.activity.ID";
    public static final String ACTIVITY_EXTRA_MOVIE = "com.example.root.movie.activity.MOVIE";
    public int page = 1;
    @BindView(R.id.movie_recyclerView)
    RecyclerView movieList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindString(R.string.toast_notInternet)
    String toastMessage;
    List<MovieData.ResultsBean> mList = new ArrayList<>();
    MovieAdapter adapter = null;
    GridLayoutManager gridLayoutManager=
            new GridLayoutManager(getActivity(),2);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLatest();
            }
        });
        movieList.setLayoutManager(gridLayoutManager);
        adapter = new MovieAdapter(mList);
        movieList.setAdapter(adapter);
        movieList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(),DetialActivity.class);
                i.putExtra(ACTIVITY_EXTRA_ID,adapter.getItem(position).getId());
                i.putExtra(ACTIVITY_EXTRA_MOVIE,adapter.getItem(position));
                getActivity().startActivity(i);
            }
        }));
        movieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                    int visibleItemcount = gridLayoutManager.getChildCount();
                    int totalcount = gridLayoutManager.getItemCount();
                    int pastvisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemcount+pastvisibleItem)>=totalcount){
                        loadMore();
                    }
                }
            }
        });
        loadLatest();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort:{
                Collections.sort(mList, new Comparator<MovieData.ResultsBean>() {
                    @Override
                    public int compare(MovieData.ResultsBean m1, MovieData.ResultsBean t1) {
                        return ((Double)m1.getVote_average()).compareTo(t1.getVote_average());
                    }
                });
                Collections.reverse(mList);
                Log.e("sort","average");
                adapter.notifyDataSetChanged();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class  AsyncGetData extends AsyncTask<Integer,Void,Integer>{
        public static final int GET_LATEST = 1;
        public static final int GET_MORE = 2;
        //public static final int SORT_LATEST =3;
        public static final int FIR_PAGE =1;
        public List<MovieData.ResultsBean> temp;
        @Override
        protected Integer doInBackground(Integer... mode) {
            switch (mode[0]){
                case GET_LATEST:{
                    if ((temp= new MovieOkhttp(getActivity()).
                            getPopularMovieResults(FIR_PAGE))!=null) {
                        mList.clear();
                        mList.addAll(temp);
                    }
                    page=1;
                    break;
                }
                case GET_MORE:{
                    page=page+1;
                    if ((temp=new MovieOkhttp(getActivity()).
                            getPopularMovieResults(page))!=null) {
                        mList.addAll(temp);
                    }
                    break;
                }
            }
            return mode[0];
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (swipeRefresh.isRefreshing()){
                swipeRefresh.setRefreshing(false);
            }
            if(temp!=null) {
                switch (result) {
                    case GET_LATEST: {
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case GET_MORE: {
                        adapter.notifyItemRangeChanged(mList.size() - temp.size(), temp.size());
                        break;
                    }
                }
            }else {
                Toast.makeText(getActivity(),toastMessage,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadLatest(){
        new AsyncGetData().execute(AsyncGetData.GET_LATEST);
    }

    public void loadMore(){
        new AsyncGetData().execute(AsyncGetData.GET_MORE);
    }
}
