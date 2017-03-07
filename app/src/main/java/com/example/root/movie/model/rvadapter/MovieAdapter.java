package com.example.root.movie.model.rvadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.root.movie.R;
import com.example.root.movie.model.MovieData;
import com.example.root.movie.model.MovieInfo;
import com.example.root.movie.model.ReHolder;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<ReHolder> {
    List<MovieInfo> list;
    Context context;
    public MovieAdapter(List<MovieInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ReHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_view,parent,false),this);
    }

    @Override
    public void onBindViewHolder(ReHolder holder, int position) {
        holder.bindModel(list.get(position),context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<MovieInfo> mList){
        list.clear();
        list.addAll(mList);
        notifyDataSetChanged();
    }

    public MovieInfo getItem(int position){
        return list.get(position);
    }
}
