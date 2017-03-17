package com.example.root.movie.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.root.movie.R;
import com.example.root.movie.helper.YoutubeHelper;
import com.example.root.movie.model.Trailers;

import java.util.List;

/**
 * Created by ilumer on 17-3-17.
 */

public class TrailerAdapter extends PagerAdapter {
    private List<Trailers.Trailer> list;

    public TrailerAdapter(List<Trailers.Trailer> list) {
        this.list = list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Trailers.Trailer trailer = list.get(position);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.viewholder_trailer,container,false);
        ImageView trailerVideo = (ImageView) view.findViewById(R.id.movie_trailer);
        Glide.with(container.getContext())
                .load(YoutubeHelper.getImgUri(trailer.getKey()))
                .into(trailerVideo);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
