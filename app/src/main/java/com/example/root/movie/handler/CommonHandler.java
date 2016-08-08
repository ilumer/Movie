package com.example.root.movie.handler;

import android.os.Handler;
import android.os.Message;

import com.example.root.movie.constant.MovieConstant;
import com.example.root.movie.fragment.RatedDetailFragment;
import com.example.root.movie.model.ReviewsModel;
import com.example.root.movie.model.Trailers;

/**
 * Created by root on 8/5/16.
 * handler for RateDetailFragment
 */
public class CommonHandler extends Handler {
    RatedDetailFragment fragment;
    public CommonHandler(final RatedDetailFragment fragment) {
        super();
        this.fragment = fragment;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what== MovieConstant.REVIEWINFOMESSAGE){
            ReviewsModel model = (ReviewsModel) msg.obj;
            fragment.updateReviews(model);
        }else if (msg.what== MovieConstant.TRAILERSMESSAGE){
            Trailers trailers = (Trailers) msg.obj;fragment.updateTrailers(trailers);
        }
    }
}
