package com.example.root.movie.data.source.local;

import android.provider.BaseColumns;


public class MovieContract {
    public static abstract class MovieDataBean implements BaseColumns{
        public static final String TABLE_NAME = "MovieDataBean";
        public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
    }
}
