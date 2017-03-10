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

import com.example.root.movie.data.source.local.MovieHelper;
import com.example.root.movie.model.AccountFavourite;
import com.example.root.movie.model.FragmentCallback;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.model.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavouriteFragment extends Fragment implements MainActivity.UpdateUI{

    @Override
    public void updateUI() {

    }
}


