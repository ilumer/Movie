package com.example.root.movie.Net;

import android.content.Context;
import android.net.Uri;

import com.example.root.movie.model.DetialMovie;
import com.example.root.movie.model.MovieData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieOkhttp {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private Context mcontext;

    public MovieOkhttp(Context context) {
        this.mcontext = context;
    }

    public MovieData getMovieData(int page) throws Exception {
        String uri = Uri.parse(DBAPI.BASEPOPLULAR_URI).buildUpon()
                .appendQueryParameter("api_key",DBAPI.API_KEY)
                .appendQueryParameter("page",String.valueOf(page))
                .build().toString();
        Request request = new Request.Builder()
                .url(uri)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("unexpected code"+response);
        }
        return gson.fromJson(response.body().string(),MovieData.class);
    }

    public List<MovieData.ResultsBean> getPopularMovieResults(int page){
        List<MovieData.ResultsBean> Results = null;
        try{
            Results = getMovieData(page).getResults();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Results;
    }

    public DetialMovie getDetialMovieInfo(int id){
        String response = null;
        try{
            response = getData(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return gson.fromJson(response,DetialMovie.class);
    }

    public String getData(int id) throws Exception{
        String uri = DBAPI.BASEMOVIEINFO_URI+id+"?api_key="+DBAPI.API_KEY;
        Request request = new Request.Builder().url(uri).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("unexpected code"+response);
        }
        return response.body().string();
    }
}
