package com.example.root.movie.Helper;


import android.net.Uri;

public class YoutubeHelper {
    public static final String YOUTUBEBASEURI="https://www.youtube.com/watch?v=";

    public static Uri getVideoUri(String key){
        return Uri.parse(YOUTUBEBASEURI+key);
    }
}
