package com.example.root.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.root.movie.R;
import com.example.root.movie.adapter.ViewHolder.BaseViewHolder;
import com.example.root.movie.adapter.ViewHolder.ProgressBarViewHolder;

/**
 * Created by ilumer on 17-3-10.
 */

abstract class ProgressAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int LOADING_TYPE = 0x00;
    private boolean isLoading = false;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == LOADING_TYPE){
            return createProgressBarViewHolder(context,parent);
        } else {
            return onCreateExtViewHolder(context,parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getDataCount()){
            return LOADING_TYPE;
        }else {
            return getExtItemViewType(position);
        }
    }

    public void loadingMore(){
        isLoading = true;
        notifyItemInserted(getItemCount());
        // size = index +1
    }

    public void loadingEnd(){
        notifyItemRemoved(getItemCount());
        isLoading = false;
    }

    @Override
    public int getItemCount() {
        return getDataCount() + (isLoading ? 1: 0);
    }

    protected BaseViewHolder createProgressBarViewHolder(Context context,ViewGroup parent){
        return new ProgressBarViewHolder(LayoutInflater.from(context).inflate(R.layout.viewholder_progressbar,parent,false));
    }

    abstract int getExtItemViewType(int position);

    abstract int getDataCount();

    abstract BaseViewHolder onCreateExtViewHolder(Context context,ViewGroup parent);
}
