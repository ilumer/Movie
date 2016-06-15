package com.example.root.movie.DAO;

import android.provider.BaseColumns;


public class Movie {
    public static abstract class MovieDataBean implements BaseColumns{
        public static final String TABLE_NAME = "MovieDataBean";
        /*public static final String TABLE_COLUMN_ENTRY_ID = "PRIMARY_ID";*/
        public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        public static final String COLUMN_NAME_ADULT = "adult";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_MOVIE_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_NAME_MOVIE_LANGUAGE = "language";
        public static final String COLUMN_NAME_MOVIE_TITLE = "title";
        public static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_NAME_WIDTH = "width";
        public static final String COLUMN_NAME_HEIGHT = "height";
        public static final String COLUMN_NAME_POPULARITY = "popularity";
        public static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
        public static final String COLUMN_NAME_IS_VIDEO = "is_video";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
    }
}
