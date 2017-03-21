package com.example.root.movie.adapter.ViewHolder;

import android.content.Intent;
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
    public void bind(final Trailers.Trailer model) {
        super.bind(model);
        Glide.with(trailer.getContext())
                .load(YoutubeHelper.getImgUri(model.getKey()))
                .into(trailer);
        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(YoutubeHelper.getVideoUri(model.getKey()));
                trailer.getContext().startActivity(i);
            }
        });
    }
}
