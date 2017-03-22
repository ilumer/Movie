package com.example.root.movie.data.source.remote;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.model.Trailers;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieOkhttp {
    private final static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(new StethoInterceptor())
            .build();
    private final static Gson gson = new Gson();
    private final static String TAG = MovieOkhttp.class.getName();
    private Context mcontext;

    public MovieOkhttp(Context context) {
        this.mcontext = context;
    }

    public static List<MovieInfo> getMovieData(int page) throws IOException{
        String uri = Uri.parse(DBAPI.BASEPOPLULAR_URI).buildUpon()
                .appendQueryParameter("api_key",DBAPI.API_KEY)
                .appendQueryParameter("page",String.valueOf(page))
                .build().toString();
        Request request = new Request.Builder()
                .url(uri)
                .build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                throw new IOException("unexpected code"+response);
            }
            return gson.fromJson(response.body().string(),MovieData.class).getResults();
        } finally {
            if (response != null)
                response.close();
        }
    }

    public List<MovieInfo> getPopularMovieResults(int page){
        List<MovieInfo> Results = null;
        try{
            Results = getMovieData(page);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Results;
    }

    public static DetailMovie getDetialMovieInfo(int id) throws IOException{
        HttpUrl url = HttpUrl.parse(DBAPI.BASEMOVIEINFO_URI+id)
                .newBuilder()
                .addQueryParameter("api_key",DBAPI.API_KEY)
                .build();
        String response = null;
        response = getContent(url);
        return gson.fromJson(response,DetailMovie.class);
    }

    public static List<Trailers.Trailer> getTrailers(String imdbId) throws IOException{
        String uri = DBAPI.BASEMOVIEINFO_URI+imdbId+DBAPI.BASEVIDEO_TYPE+"?api_key="+DBAPI.API_KEY;
        Request request = new Request.Builder().url(uri).build();
        Response response = client.newCall(request).execute();
        return gson.fromJson(response.body().string(),Trailers.class).getResults();
    }

    public static String getToken(){
        String uri = DBAPI.NEW_TOKEN+"?api_key="+DBAPI.API_KEY;
        Request request = new Request.Builder().url(uri).build();
        Response response = null;
        JSONObject object = null;
        String content = null;
        try{
            response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                Log.e(TAG,"unexpected code" +response);
            }

            object = new JSONObject(response.body().string());
            content = (String) object.get("request_token");
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
        return content;
    }

    public static String getSessionId(String Token){
        HttpUrl url = HttpUrl.parse(DBAPI.NEW_SESSION).newBuilder()
                .addQueryParameter("api_key",DBAPI.API_KEY)
                .addQueryParameter("request_token",Token)
                .build();

        String sessionId = null;
        try{
            JSONObject object = new JSONObject(getContent(url));
            sessionId = (String) object.get("session_id");
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
        return sessionId;
    }

    /*
    同步get
     */

    public static String getContent(HttpUrl url) throws IOException{
        Request request = new Request.Builder().
                url(url).
                build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                Log.e(TAG,"unexpected code" +response);
            }
        }catch (IOException e){
            Log.e(TAG,e.toString());
        }
        return response.body().string();
    }
}
