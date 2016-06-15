package com.example.root.movie.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    List<ReviewsModel.ResultsBean> mList;

    public ReviewAdapter(List<ReviewsModel.ResultsBean> List) {
        this.mList = List;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewHolder(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_review,parent,false));
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        ReviewsModel.ResultsBean m = mList.get(position);
        holder.contentTextViwew.setText(m.getContent());
        holder.nameTextView.setText(m.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public ReviewsModel.ResultsBean getItem(int position){
        return mList.get(position);
    }

    public void setmList(List<ReviewsModel.ResultsBean> list){
        if (mList.size()!=0){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class ReviewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.content_review)
        TextView contentTextViwew;
        @BindView(R.id.name_Reviewer)
        TextView nameTextView;
        public ReviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
