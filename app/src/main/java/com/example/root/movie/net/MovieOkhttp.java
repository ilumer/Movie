package com.example.root.movie.net;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.root.movie.api.model.RatedResults;
import com.example.root.movie.api.model.UserInfoCache;
import com.example.root.movie.constant.MovieConstant;
import com.example.root.movie.handler.CommonHandler;
import com.example.root.movie.helper.IMDBHelper;
import com.example.root.movie.model.DetailMovie;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.model.ReviewsModel;
import com.example.root.movie.model.Trailers;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
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

    public static List<MovieInfo> getMovieData(int page){
        String uri = Uri.parse(DBAPI.BASEPOPLULAR_URI).buildUpon()
                .appendQueryParameter("api_key",DBAPI.API_KEY)
                .appendQueryParameter("page",String.valueOf(page))
                .build().toString();
        Request request = new Request.Builder()
                .url(uri)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                throw new IOException("unexpected code"+response);
            }
            return gson.fromJson(response.body().string(),MovieData.class).getResults();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
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

    public static DetailMovie getDetialMovieInfo(int id){
        HttpUrl url = HttpUrl.parse(DBAPI.BASEMOVIEINFO_URI+id)
                .newBuilder()
                .addQueryParameter("api_key",DBAPI.API_KEY)
                .build();
        String response = null;
        try{
            response = getContent(url);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return gson.fromJson(response,DetailMovie.class);
    }

    public static List<Trailers.ResultsBean> getTrailers(int id){
        String uri = DBAPI.BASEMOVIEINFO_URI+id+DBAPI.BASEVIDEO_TYPE+"?api_key="+DBAPI.API_KEY;
        Request request = new Request.Builder().url(uri).build();
        List<Trailers.ResultsBean> trailers = null;
        Response response ;
        try{
            response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                Log.e(TAG,"unexpected code"+response);
            }
            trailers = gson.fromJson(response.body().string(),Trailers.class).getResults();
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
        return trailers;
    }

    public static ReviewsModel getReciew(int id){
        String uri = DBAPI.BASEMOVIEINFO_URI+id+DBAPI.BASEREVIEWS_TYPE+"?api_key="+DBAPI.API_KEY;
        Request request = new Request.Builder().url(uri).build();
        ReviewsModel reviews = null;
        Response response ;
        try{
            response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                Log.e(TAG,"unexpected code"+response);
            }
            reviews = gson.fromJson(response.body().string(),ReviewsModel.class);
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
        return reviews;
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

    public static UserInfoCache getAccountInfo(String SessionId){
        HttpUrl url = HttpUrl.parse(DBAPI.ACCOUNT_INFO).
                newBuilder().
                addQueryParameter("api_key",DBAPI.API_KEY).
                addQueryParameter("session_id",SessionId).
                build();
        String content = null;
        UserInfoCache mUserInfo = null;
        try{
            content = getContent(url);
            mUserInfo = gson.fromJson(content,UserInfoCache.class);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return mUserInfo;
    }

    public static RatedResults getRated(String SessionID,String type,String id){
        HttpUrl url = HttpUrl.parse(
                IMDBHelper.getAccountBsUri(id,DBAPI.EVALUATION_RATED,type))
                .newBuilder()
                .addQueryParameter("api_key",DBAPI.API_KEY)
                .addQueryParameter("session_id",SessionID)
                .build();
        String content ;
        RatedResults ratedResults = null;
        try{
            content = getContent(url);
            Log.e(TAG,content);
            ratedResults = gson.fromJson(content,RatedResults.class);
        }catch (IOException ex){
            Log.e(TAG,ex.getCause().toString());
        }
        return ratedResults;
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

    public static void getReviews(final CommonHandler handler, int id){
        HttpUrl url = HttpUrl.parse(DBAPI.BASEMOVIEINFO_URI+id+DBAPI.BASEREVIEWS_TYPE)
                .newBuilder()
                .addQueryParameter("api_key",DBAPI.API_KEY)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ReviewsModel model;
                model = gson.fromJson(response.body().string(),ReviewsModel.class);
                handler.obtainMessage(MovieConstant.REVIEWINFOMESSAGE,model).sendToTarget();
            }
        });

    }

    public static void getTrailers(final CommonHandler handler,int id){
        HttpUrl url = HttpUrl.parse(DBAPI.BASEMOVIEINFO_URI+id+DBAPI.BASEVIDEO_TYPE)
                .newBuilder()
                .addQueryParameter("api_key",DBAPI.API_KEY)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Trailers trailers = gson.fromJson(response.body().string(),Trailers.class);
                handler.obtainMessage(MovieConstant.TRAILERSMESSAGE,trailers).sendToTarget();
            }
        });
    }
}
