package com.example.root.movie.model.rvadapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.root.movie.R;
import com.example.root.movie.api.model.RateInfo;
import com.example.root.movie.api.model.RatedResults;
import com.example.root.movie.helper.IMDBHelper;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 16-7-25.
 */
public class RatedAdapter extends RecyclerView.Adapter<RatedAdapter.RatedViewHolder> {

    List<RateInfo> mList;
    RvOnClickListener mrvOnClickListener;
    Context mcontext;

    public RatedAdapter(List<RateInfo> list, Context context) {
        super();
        this.mList = list;
        this.mcontext = context;
    }

    @Override
    public RatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RatedViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.data_itemview,parent,false));
    }

    @Override
    public void onBindViewHolder(RatedViewHolder holder, final int position) {
        RateInfo info = mList.get(position);
        holder.mName.setText(info.getTitle());
        holder.mVoterPer.setText(String.format(holder.perFormat,info.getVote_average()));
        holder.mVoteNum.setText(String.format(holder.numFormat,info.getVote_count()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mrvOnClickListener.onclick(view);
            }
        });
    }

    public void setMrvOnClickListener(RvOnClickListener listener){
        this.mrvOnClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class RatedViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.view)
        CardView itemView;
        @BindView(R.id.rated_image)
        ImageView image;
        @BindView(R.id.image_button)
        ImageButton imageButton;
        @BindView(R.id.movie_vote_per)
        TextView mVoterPer;
        @BindView(R.id.movie_vote_num)
        TextView mVoteNum;
        @BindView(R.id.collect)
        Button mCollect;
        @BindView(R.id.Movie_name)
        TextView mName;
        @BindString(R.string.movie_vote_per)
        String perFormat;
        @BindString(R.string.movie_vote_num)
        String numFormat;

        public RatedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
