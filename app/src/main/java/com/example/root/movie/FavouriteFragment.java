package com.example.root.movie;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.movie.helper.MovieHelper;
import com.example.root.movie.model.AccountFavourite;
import com.example.root.movie.model.FragmentCallback;
import com.example.root.movie.model.rvadapter.MovieAdapter;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavouriteFragment extends Fragment implements MainActivity.UpdateUI{

    public static final String TAG = FavouriteFragment.class.getSimpleName();
    public FragmentCallback mcallbacks;
    private Unbinder unbinder;
    @BindView(R.id.favourite_movie)
    RecyclerView rvFavMovie;
    List<MovieData.ResultsBean> mList ;
    MovieAdapter adapter = null;
    AccountFavourite accountFavourite;
    SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_movie,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountFavourite = AccountFavourite.getInstance();
        database = new MovieHelper(getActivity()).getReadableDatabase();
        rvFavMovie.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mList = accountFavourite.getmList();
        adapter = new MovieAdapter(mList,getContext());
        rvFavMovie.setAdapter(adapter);
        rvFavMovie.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mcallbacks.selectdMovie(adapter.getItem(position));
            }
        }));
        new AsyUpdateSingle().execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity){
            mcallbacks = (FragmentCallback) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mcallbacks = null;
    }

    @Override
    public void updateUI() {
        Log.e(TAG,mList.size()+"");
        adapter.notifyDataSetChanged();
    }


    public class AsyUpdateSingle extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            accountFavourite.initList(database);
            mList = accountFavourite.getmList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }


}


