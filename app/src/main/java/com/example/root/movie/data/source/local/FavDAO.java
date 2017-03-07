package com.example.root.movie.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.MovieInfo;

import java.util.ArrayList;
import java.util.List;

public class FavDAO {
    public static List<MovieInfo> getFavMovieFromDB(SQLiteDatabase database){
        Cursor cursor = database.rawQuery("select * from "+ MovieContract.MovieDataBean.TABLE_NAME,null);
        List<MovieInfo> mList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                MovieInfo r = new MovieInfo();
                r.setPosterPath(
                        cursor.getString
                                (cursor.getColumnIndexOrThrow
                                        (MovieContract.MovieDataBean.COLUMN_NAME_POSTER_PATH)));
                r.setAdult( intConvertBool( cursor.getInt( cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_ADULT))));
                r.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_OVERVIEW)));
                r.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_DATE)));
                r.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_ID)));
                r.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_ORIGINAL_TITLE)));
                r.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_LANGUAGE)));
                r.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_TITLE)));
                r.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_BACKDROP_PATH)));
                r.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_POPULARITY)));
                r.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_VOTE_COUNT)));
                r.setVideo(intConvertBool(cursor.getInt(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_IS_VIDEO))));
                r.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(MovieContract.MovieDataBean.COLUMN_NAME_VOTE_AVERAGE)));
                mList.add(r);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }

    public static void deleteFavMovieByDB(SQLiteDatabase database,int id){
        database.delete
                (MovieContract.MovieDataBean.TABLE_NAME,
                        MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_ID+" =?",
                        new String[]{String.valueOf(id)});
    }

    public static void InsertFavMovieToDB(SQLiteDatabase database, MovieInfo m){
        int isAdult = boolConvertInt(m.isAdult());
        int isVideo = boolConvertInt(m.isVideo());
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_POSTER_PATH,m.getPosterPath());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_ADULT,isAdult);
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_OVERVIEW,m.getOverview());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_DATE,m.getReleaseDate());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_ID,m.getId());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_ORIGINAL_TITLE,m.getTitle());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_LANGUAGE,m.getOriginalLanguage());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_TITLE,m.getTitle());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_BACKDROP_PATH,m.getBackdropPath());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_POPULARITY,m.getPopularity());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_VOTE_COUNT,m.getVoteCount());
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_IS_VIDEO,isVideo);
        values.put(MovieContract.MovieDataBean.COLUMN_NAME_VOTE_AVERAGE,m.getVoteAverage());
        values.putNull(MovieContract.MovieDataBean.COLUMN_NAME_HEIGHT);
        values.putNull(MovieContract.MovieDataBean.COLUMN_NAME_WIDTH);
        database.insert(MovieContract.MovieDataBean.TABLE_NAME,null,values);
    }

    public static int boolConvertInt(boolean flag){
        return flag?1:0;
    }

    public static boolean intConvertBool(int flag){
        return flag==1;
    }
}
