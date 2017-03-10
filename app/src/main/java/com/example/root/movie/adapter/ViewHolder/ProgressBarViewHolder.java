package com.example.root.movie.adapter.ViewHolder;

import android.view.View;
import android.widget.ProgressBar;

import com.example.root.movie.R;

import butterknife.BindView;

/**
 * Created by ilumer on 17-3-10.
 */

public class ProgressBarViewHolder extends BaseViewHolder {

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    public ProgressBarViewHolder(View itemView) {
        super(itemView);
    }
}
