package com.example.root.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.movie.R;
import com.example.root.movie.adapter.ViewHolder.BaseViewHolder;
import com.example.root.movie.adapter.ViewHolder.TrailerViewHolder;
import com.example.root.movie.model.Trailers;

import java.util.List;

/**
 * Created by ilumer on 17-3-17.
 */

public class VideoAdapter extends RecyclerView.Adapter<BaseViewHolder<Trailers.Trailer>> {

    private List<Trailers.Trailer> list;

    public VideoAdapter(List<Trailers.Trailer> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder<Trailers.Trailer> onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_trailer,parent,false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<Trailers.Trailer> holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
