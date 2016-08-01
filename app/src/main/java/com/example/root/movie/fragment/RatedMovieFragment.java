package com.example.root.movie.fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.root.movie.R;
import com.example.root.movie.api.model.RateInfo;
import com.example.root.movie.api.model.RatedResults;
import com.example.root.movie.api.model.UserInfoCache;
import com.example.root.movie.constant.MovieConstant;
import com.example.root.movie.model.rvadapter.RatedAdapter;
import com.example.root.movie.model.rvadapter.RvOnClickListener;
import com.example.root.movie.net.DBAPI;
import com.example.root.movie.net.MovieOkhttp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by root on 16-7-24.
 * 推荐的作品
 */
public class RatedMovieFragment extends BaseFragment implements RvOnClickListener{

    public static final String TAG = RatedMovieFragment.class.getName();
    @BindView(R.id.toolbar)
    Toolbar mtoolbar;
    @BindView(R.id.content_list)
    RecyclerView mContent;
    List<RateInfo> mList = new ArrayList<>();
    RatedAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RatedAdapter(mList,getActivity());
        mAdapter.setMrvOnClickListener(this);
        mContent.setAdapter(mAdapter);
        final SharedPreferences pf = getActivity().getSharedPreferences(MovieConstant.SP_EXTRA_SESSIONID, Context.MODE_PRIVATE);
        String SessionId = pf.getString(MovieConstant.SP_EXTRA_SESSIONID, null);
        UserInfoCache userInfoCache = null;
        try {
            FileInputStream inputStream= getActivity().openFileInput("user");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            userInfoCache = (UserInfoCache) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException ex){
            Log.e("",ex.getMessage());
        }
        if (userInfoCache!=null&&SessionId!=null&&savedInstanceState==null) {
            new Task(this).execute(new String[]{SessionId, String.valueOf(userInfoCache.getId())});
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getUnbinder().unbind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.content_fragment ;
    }

    @Override
    public void onclick(View view) {
        int itemposition = mContent.getChildLayoutPosition(view);
        RateInfo info = mList.get(itemposition);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .hide(this)
                .add(R.id.fragment_container,RatedDetialFragment.getInstance(info))
                .addToBackStack(null)
                .commit();
    }

    protected void addList(List<RateInfo> list){
        mList.addAll(list);
    }

    protected void update(){
        mAdapter.notifyDataSetChanged();
    }

    private static class Task extends AsyncTask<String,Void,Void>{

        private RatedMovieFragment fragment;
        public Task(final RatedMovieFragment fragment) {
            super();
            this.fragment = fragment;
        }

        @Override
        protected Void doInBackground(String... Params) {
            RatedResults ratedResults = MovieOkhttp.getRated(Params[0], DBAPI.MOVIE,Params[1]);
            List<RateInfo> list ;
            if (ratedResults!=null) {
                list = ratedResults.getmList();
                if (list!=null) {
                    fragment.addList(list);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fragment.update();
        }
    }

}
