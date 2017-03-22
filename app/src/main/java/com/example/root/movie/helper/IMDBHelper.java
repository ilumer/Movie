package com.example.root.movie.helper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.example.root.movie.data.source.remote.DBAPI;

public class IMDBHelper {
    public static int getWidth(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels/2;
    }

    public static String getImageBsUri(Context context,String baseURL){
        int widthPx = getWidth(context);
        if (widthPx > 500) {
            return "http://image.tmdb.org/t/p/w780/" + baseURL;
        } else if (widthPx > 342 && widthPx <= 500) {
            return "http://image.tmdb.org/t/p/w500/" + baseURL;
        } else if (widthPx > 185 && widthPx <= 342) {
            return "http://image.tmdb.org/t/p/w342/" + baseURL;
        } else if (widthPx > 154 && widthPx <= 185) {
            return "http://image.tmdb.org/t/p/w185/" + baseURL;
        } else if (widthPx > 92 && widthPx <= 154) {
            return "http://image.tmdb.org/t/p/w154/" + baseURL;
        } else if (widthPx > 0 && widthPx <= 92) {
            return "http://image.tmdb.org/t/p/w92/" + baseURL;
        } else  {
            return "http://image.tmdb.org/t/p/w185/" + baseURL;     // Default Value
        }
    }


    /**
     *
     * @param id 是UserInfoCache中的id
     * @param type 返回的信息的类型
     * @return 返回数据的Uri
     */
    public static String getAccountBsUri(String id,String type){
        return DBAPI.ACCOUNT_INFO+"/"+id+"/"+type;
    }

    /**
     *
     * @param id 是UserInfoCache中的id
     * @param evaluation 评价
     * @param type 返回信息的类型
     * @return 返回数据的Uri
     */

    public static String getAccountBsUri(String id,String evaluation,String type){
        return DBAPI.ACCOUNT_INFO+"/"+id+"/"+evaluation+"/"+type;

    }
}
