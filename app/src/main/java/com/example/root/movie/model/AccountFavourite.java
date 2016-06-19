package com.example.root.movie.model;



import android.database.sqlite.SQLiteDatabase;

import com.example.root.movie.dao.FavDAO;

import java.util.ArrayList;
import java.util.List;

public class AccountFavourite {
    public List<MovieData.ResultsBean> mList;
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

    public void addFavMovie(MovieData.ResultsBean movie){
        mList.add(movie);
    }

    public void removeFavMovie(MovieData.ResultsBean movie){
        mList.remove(movie);
    }


    public List<MovieData.ResultsBean> getmList(){
        return mList;
    }

    public void initList(SQLiteDatabase database){
        mList.addAll(FavDAO.getFavMovieFromDB(database));
    }
}
