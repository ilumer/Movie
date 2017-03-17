package com.example.root.movie.model.rvadapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.root.movie.helper.YoutubeHelper;
import com.example.root.movie.R;
import com.example.root.movie.model.Trailers;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    List<Trailers.Trailer> mList;
    Context mcontext;
    public TrailerAdapter(List<Trailers.Trailer> List, Context context) {
        this.mcontext = context;
        mList = List;
    }


    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailerViewHolder(LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.viewholder_trailer,parent,false));
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        final Trailers.Trailer m = mList.get(position);
        Glide.with(mcontext)
                .load(YoutubeHelper.getImgUri(m.getKey()))
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        YoutubeHelper.getVideoUri(m.getKey()));
                if (i.resolveActivity(mcontext.getPackageManager())!=null) {
                    mcontext.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.movie_trailer)
        ImageView imageView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
