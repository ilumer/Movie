package com.example.root.movie.model;



import android.database.sqlite.SQLiteDatabase;

import com.example.root.movie.data.source.local.FavDAO;

import java.util.ArrayList;
import java.util.List;

public class AccountFavourite {
    public List<MovieInfo> mList;
    public static AccountFavourite accountFavourite = null;

    private AccountFavourite() {
        this.mList = new ArrayList<>();
    }

    public static AccountFavourite getInstance(){
        if (accountFavourite==null){
            accountFavourite = new AccountFavourite();
        }
        return accountFavourite;
    }

    public void addFavMovie(MovieInfo movie){
        mList.add(movie);
    }

    public void removeFavMovie(MovieInfo movie){
        mList.remove(movie);
    }


    public List<MovieInfo> getmList(){
        return mList;
    }

    public void initList(SQLiteDatabase database){
        mList.addAll(FavDAO.getFavMovieFromDB(database));
    }
}
