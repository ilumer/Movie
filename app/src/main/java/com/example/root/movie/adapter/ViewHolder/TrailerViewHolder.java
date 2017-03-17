package com.example.root.movie.adapter.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.root.movie.R;
import com.example.root.movie.helper.YoutubeHelper;
import com.example.root.movie.model.Trailers;

import butterknife.BindView;

/**
 * Created by ilumer on 17-3-17.
 */

public class TrailerViewHolder extends BaseViewHolder<Trailers.Trailer>{

    @BindView(R.id.movie_trailer)
    ImageView trailer;
    public TrailerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Trailers.Trailer model) {
        super.bind(model);
        Glide.with(trailer.getContext())
                .load(YoutubeHelper.getImgUri(model.getKey()))
                .into(trailer);
    }
}
