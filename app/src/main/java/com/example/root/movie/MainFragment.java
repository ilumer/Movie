package com.example.root.movie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.example.root.movie.activity.LoginActivity;
import com.example.root.movie.helper.IMDBHelper;
import com.example.root.movie.model.ControlPage;
import com.example.root.movie.model.FragmentCallback;
import com.example.root.movie.model.MovieAdapter;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.net.MovieOkhttp;
import com.example.root.movie.model.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainFragment extends Fragment implements ControlPage{
    public static final String TAG = MainFragment.class.getSimpleName();
    public static final String CURRENT_PAGE = "com.example.root.movie.activity.PAGE";
    public static final String RECYCLERVIEW_LATOUTSTATE = "com.example..root.activity.LAYOUTSTATE";
    public static final String RECYCLERVIEW_LAYOUTCONTENT = "com.example.root.activity.content";
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
    private Parcelable mGridLayoutState = null;
    private FragmentCallback mcallbacks;
    private AsyncGetData asyncGetData;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            List<MovieData.ResultsBean> temp = savedInstanceState.getParcelableArrayList(RECYCLERVIEW_LAYOUTCONTENT);
            if (temp != null) {
                mList.addAll(temp);
            }
            mGridLayoutState = savedInstanceState.getParcelable(RECYCLERVIEW_LATOUTSTATE);
            page = savedInstanceState.getInt(CURRENT_PAGE);
        }
        if (mGridLayoutState!=null){
            gridLayoutManager.onRestoreInstanceState(mGridLayoutState);
        }
    }

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
    public void onSaveInstanceState(Bundle outState) {
        mGridLayoutState = gridLayoutManager.onSaveInstanceState();
        outState.putInt(CURRENT_PAGE,page);
        outState.putParcelable(RECYCLERVIEW_LATOUTSTATE,mGridLayoutState);
        outState.putParcelableArrayList(RECYCLERVIEW_LAYOUTCONTENT,(ArrayList<? extends Parcelable>)mList);
        super.onSaveInstanceState(outState);
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
                loadLatest();
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
                            loadMore();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGridLayoutState==null){
            loadLatest();
        }
        Log.e(TAG, IMDBHelper.getWidth(getActivity())+"");
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
        if (asyncGetData!=null) {
            asyncGetData.cancel(true);
        }
    }

    private static class  AsyncGetData extends AsyncTask<Integer,Void,Integer>{
        public static final int GET_LATEST = 1;
        public static final int GET_MORE = 2;
        public static final int FIR_PAGE =1;
        private List<MovieData.ResultsBean> temp;
        private List<MovieData.ResultsBean> mList;
        private Context mContext;
        private ControlPage page;
        private SwipeRefreshLayout mSwipeRefreshLayout;
        private MovieAdapter mAdapter;
        private String toastMessage;

        public AsyncGetData(List<MovieData.ResultsBean> list,
                            Context context,
                            SwipeRefreshLayout swipeRefreshLayout,
                            MovieAdapter adapter,
                            String message,
                            ControlPage apage) {
            this.mList = list;
            this.mContext = context;
            this.mSwipeRefreshLayout = swipeRefreshLayout;
            this.mAdapter = adapter;
            this.toastMessage = message;
            this.page = apage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... mode) {
            if (!isCancelled()) {
                switch (mode[0]) {
                    case GET_LATEST: {
                        if ((temp = new MovieOkhttp(mContext).
                                getPopularMovieResults(FIR_PAGE)) != null) {
                            mList.clear();
                            mList.addAll(temp);
                        }
                        page.backOriginalValue();
                        break;
                    }
                    case GET_MORE: {
                        page.add();
                        if ((temp = new MovieOkhttp(mContext).
                                getPopularMovieResults(page.getPage())) != null) {
                            mList.addAll(temp);
                        }
                        break;
                    }
                }
            }
            return mode[0];
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (!isCancelled()) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (temp != null) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    private void loadLatest(){
        Log.e(TAG,"refresh");
        swipeRefresh.setRefreshing(true);
        generateAsyncTask();
        asyncGetData.execute(AsyncGetData.GET_LATEST);
    }

    public void loadMore(){
        Log.e(TAG,"more");
        swipeRefresh.setRefreshing(true);
        generateAsyncTask();
        asyncGetData.execute(AsyncGetData.GET_MORE);
    }

    public void generateAsyncTask(){
        asyncGetData = new AsyncGetData(mList,getActivity(),swipeRefresh,adapter,toastMessage,this);
    }
}
