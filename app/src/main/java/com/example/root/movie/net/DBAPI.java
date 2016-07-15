package com.example.root.movie.net;

public class DBAPI {

    private DBAPI(){
        throw new AssertionError();
    }
    public static final String API_KEY = "";
    public static final String BASEPOPLULAR_URI = "http://api.themoviedb.org/3/movie/popular";
    public static final String BASEMOVIEINFO_URI = "https://api.themoviedb.org/3/movie/";
    public static final String BASEVIDEO_TYPE = "/videos";
    public static final String BASEREVIEWS_TYPE = "/reviews";

    /*-------token api-------*/

    public static final String NEW_TOKEN = "http://api.themoviedb.org/3/authentication/token/new";
    public static final String ASK_PREMISSION = "https://www.themoviedb.org/authenticate/";
    public static final String NEW_SESSION = "http://api.themoviedb.org/3/authentication/session/new";
    /* 如果在申请权限时没有登录会显示登录页面所以没有记录登录的api */

    /*--------account --------*/
    public static final String SING_UP = "https://www.themoviedb.org/account/signup";
}
