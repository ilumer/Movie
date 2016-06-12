package com.example.root.movie.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.root.movie.Helper.YoutubeHelper;
import com.example.root.movie.R;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    List<Trailers.ResultsBean> mList;
    Context mcontext;
    public TrailerAdapter(List<Trailers.ResultsBean> List,Context context) {
        this.mcontext = context;
        mList = List;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailerViewHolder(LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.trailers,parent,false));
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        final Trailers.ResultsBean m = mList.get(position);
        holder.mtextView.setText(m.getName());
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        YoutubeHelper.getVideoUri(m.getKey()));
                mcontext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.trailer_text)
        TextView mtextView;
        @BindView(R.id.trailer_play)
        ImageButton mButton;
        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
