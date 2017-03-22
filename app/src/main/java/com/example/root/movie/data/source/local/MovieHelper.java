package com.example.root.movie.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieHelper extends SQLiteOpenHelper{

    private static volatile MovieHelper helper;

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "movie.db";
    public static final String TEXT_TYPE = " TEXT ";
    public static final String INTEGER_TYPE = " INTEGER ";
    public static final String REAL_TYPE = " REAL ";
    public static final String COMMA_SEP = ",";
    public static final String BRACKETS_SEP_LEFT = " ( ";
    public static final String BRACKETS_SEP_RIGHT = " ) ";

    public static final String SQL_CREATE_FAVOURITE =
            "CREATE TABLE " + MovieContract.MovieDataBean.TABLE_NAME +BRACKETS_SEP_LEFT +
                    MovieContract.MovieDataBean._ID + INTEGER_TYPE + "primary key" + COMMA_SEP +
                    MovieContract.MovieDataBean.COLUMN_NAME_POSTER_PATH+ TEXT_TYPE + COMMA_SEP +
                    MovieContract.MovieDataBean.COLUMN_NAME_MOVIE_ID + INTEGER_TYPE +BRACKETS_SEP_RIGHT;

    public static final String SQL_DELETE_FAVOURITE = "DROP TABLE IF EXISTS " + MovieContract.MovieDataBean.TABLE_NAME;
    public MovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_FAVOURITE);
        onCreate(sqLiteDatabase);
    }

    public static MovieHelper getInstance(Context context){
        if (helper==null){
            synchronized (MovieHelper.class){
                if (helper == null){
                    helper = new MovieHelper(context);
                }
            }
        }
        return helper;
    }
}
