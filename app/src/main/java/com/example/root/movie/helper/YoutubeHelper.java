package com.example.root.movie.helper;


import android.net.Uri;

public class YoutubeHelper {
    public static final String YOUTUBEBASEURI="https://www.youtube.com/watch?v=";

    public static Uri getVideoUri(String key){
        return Uri.parse(YOUTUBEBASEURI+key);
    }

    public static Uri getImgUri(String key){
        return Uri.parse(String.format("http://img.youtube.com/vi/%1$s/0.jpg",key));
    }
}
