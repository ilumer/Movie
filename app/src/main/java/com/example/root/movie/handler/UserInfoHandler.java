package com.example.root.movie.handler;

import android.os.Handler;
import android.os.Message;

import com.example.root.movie.MainActivity;
import com.example.root.movie.api.model.UserInfoCache;
import com.example.root.movie.constant.MovieConstant;

/**
 * Created by root on 16-7-21.
 * 获取用户信息更新ui
 */
public class UserInfoHandler extends Handler {
    MainActivity mActivity;

    public UserInfoHandler(MainActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what== MovieConstant.USERINFOHANDLERMESSAGE){
            UserInfoCache mUserInfoCache = (UserInfoCache) msg.obj;
            mActivity.setNvAccountName(mUserInfoCache.getUsername());
        }

    }

}
