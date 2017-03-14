package com.example.root.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.root.movie.R;
import com.example.root.movie.adapter.ViewHolder.BaseViewHolder;
import com.example.root.movie.adapter.ViewHolder.LoadingErrorViewHolder;
import com.example.root.movie.adapter.ViewHolder.ProgressBarViewHolder;

/**
 * Created by ilumer on 17-3-10.
 */

abstract class ProgressAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    static final int LOADING_TYPE = -1;
    static final int LOADING_ERROR = LOADING_TYPE -1;
    private boolean isLoading = false;
    private boolean isError = false;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == LOADING_TYPE){
            return createProgressBarViewHolder(context,parent);
        } else if (viewType == LOADING_ERROR){
            return createErrorViewHolder(context,parent);
        } else{
            return onCreateExtViewHolder(context,parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getDataCount() && !isError){
            return LOADING_TYPE;
        }else if (position == getDataCount() && isError){
            return LOADING_ERROR;
        } else {
            return getExtItemViewType(position);
        }
    }

    public void loadingMore(){
        if (!isError) {
            isLoading = true;
            //Position of the newly inserted item in the data set
            notifyItemInserted(getDataCount());
        }else {
            isError = false;
            isLoading = true;
            notifyItemChanged(getDataCount());
        }
    }

    public void loadingEnd(){
        int position = getDataCount();
        isLoading = false;
        notifyItemRemoved(position);
    }

    public void loadingError(){
       isLoading = false;
        isError = true;
        notifyItemChanged(getDataCount());
    }

    @Override
    public int getItemCount() {
        return getDataCount() + (isLoading ? 1: 0) + (isError ? 1: 0);
    }

    protected BaseViewHolder createProgressBarViewHolder(Context context,ViewGroup parent){
        return new ProgressBarViewHolder(LayoutInflater.from(context).inflate(R.layout.viewholder_progressbar,parent,false));
    }

    private BaseViewHolder createErrorViewHolder(Context context,ViewGroup parent) {
        return new LoadingErrorViewHolder(LayoutInflater.from(context).inflate(R.layout.viewholder_loaderror,parent,false));
    }

    abstract int getExtItemViewType(int position);

    abstract int getDataCount();

    abstract BaseViewHolder onCreateExtViewHolder(Context context,ViewGroup parent);
}
