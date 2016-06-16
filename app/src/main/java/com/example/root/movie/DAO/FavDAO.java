package com.example.root.movie.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.root.movie.model.MovieData;

import java.util.ArrayList;
import java.util.List;

public class FavDAO {
    public static List<MovieData.ResultsBean> getFavMovieFromDB(SQLiteDatabase database){
        Cursor cursor = database.rawQuery("select * from "+Movie.MovieDataBean.TABLE_NAME,null);
        List<MovieData.ResultsBean> mList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                MovieData.ResultsBean r = new MovieData.ResultsBean();
                r.setPoster_path(
                        cursor.getString
                                (cursor.getColumnIndexOrThrow
                                        (Movie.MovieDataBean.COLUMN_NAME_POSTER_PATH)));
                r.setAdult(intConvertBool(cursor.getInt(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_ADULT))));
                r.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_OVERVIEW)));
                r.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_DATE)));
                r.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_MOVIE_ID)));
                r.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_MOVIE_ORIGINAL_TITLE)));
                r.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_MOVIE_LANGUAGE)));
                r.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_MOVIE_TITLE)));
                r.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_BACKDROP_PATH)));
                r.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_POPULARITY)));
                r.setVote_count(cursor.getInt(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_VOTE_COUNT)));
                r.setVideo(intConvertBool(cursor.getInt(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_IS_VIDEO))));
                r.setVote_average(cursor.getDouble(cursor.getColumnIndexOrThrow(Movie.MovieDataBean.COLUMN_NAME_VOTE_AVERAGE)));
                mList.add(r);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }

    public static void deleteFavMovieByDB(SQLiteDatabase database,int id){
        database.delete
                (Movie.MovieDataBean.TABLE_NAME,
                        Movie.MovieDataBean.COLUMN_NAME_MOVIE_ID+" =?",
                        new String[]{String.valueOf(id)});
    }

    public static void InsertFavMovieToDB(SQLiteDatabase database, MovieData.ResultsBean m){
        int isAdult = boolConvertInt(m.isAdult());
        int isVideo = boolConvertInt(m.isVideo());
        ContentValues values = new ContentValues();
        values.put(Movie.MovieDataBean.COLUMN_NAME_POSTER_PATH,m.getPoster_path());
        values.put(Movie.MovieDataBean.COLUMN_NAME_ADULT,isAdult);
        values.put(Movie.MovieDataBean.COLUMN_NAME_OVERVIEW,m.getOverview());
        values.put(Movie.MovieDataBean.COLUMN_NAME_DATE,m.getRelease_date());
        values.put(Movie.MovieDataBean.COLUMN_NAME_MOVIE_ID,m.getId());
        values.put(Movie.MovieDataBean.COLUMN_NAME_MOVIE_ORIGINAL_TITLE,m.getTitle());
        values.put(Movie.MovieDataBean.COLUMN_NAME_MOVIE_LANGUAGE,m.getOriginal_language());
        values.put(Movie.MovieDataBean.COLUMN_NAME_MOVIE_TITLE,m.getTitle());
        values.put(Movie.MovieDataBean.COLUMN_NAME_BACKDROP_PATH,m.getBackdrop_path());
        values.put(Movie.MovieDataBean.COLUMN_NAME_POPULARITY,m.getPopularity());
        values.put(Movie.MovieDataBean.COLUMN_NAME_VOTE_COUNT,m.getVote_count());
        values.put(Movie.MovieDataBean.COLUMN_NAME_IS_VIDEO,isVideo);
        values.put(Movie.MovieDataBean.COLUMN_NAME_VOTE_AVERAGE,m.getVote_average());
        values.putNull(Movie.MovieDataBean.COLUMN_NAME_HEIGHT);
        values.putNull(Movie.MovieDataBean.COLUMN_NAME_WIDTH);
        database.insert(Movie.MovieDataBean.TABLE_NAME,null,values);
    }

    public static int boolConvertInt(boolean flag){
        return flag?1:0;
    }

    public static boolean intConvertBool(int flag){
        return flag==1;
    }
}
