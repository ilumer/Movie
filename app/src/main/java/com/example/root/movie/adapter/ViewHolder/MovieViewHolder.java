package com.example.root.movie.adapter.ViewHolder;

import android.content.Intent;
import android.graphics.Movie;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.movie.DetialActivity;
import com.example.root.movie.R;
import com.example.root.movie.data.source.local.MovieHelper;
import com.example.root.movie.fragment.RatedDetailFragment;
import com.example.root.movie.helper.IMDBHelper;
import com.example.root.movie.model.MovieInfo;


import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * Created by ilumer on 17-3-10.
 * 展示Movie的ViewHolder
 */

public class MovieViewHolder extends BaseViewHolder<MovieInfo>{

    @BindView(R.id.movie)
    ImageView movie;
    @BindView(R.id.movie_title)
    TextView movieTitle;

    public MovieViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final MovieInfo model) {
        super.bind(model);
        Glide.with(movie.getContext())
                .load(IMDBHelper.getImageBsUri(movie.getContext(),model.getBackdropPath()))
                .into(movie);
        movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(movie.getContext(), DetialActivity.class);
                i.putExtra(RatedDetailFragment.EXTRA_ID,model.getId());
                movie.getContext().startActivity(i);
            }
        });
    }
}
