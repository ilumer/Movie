package com.example.root.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DetialReviewFragment extends Fragment {

    public static final String EXTRA_CONTENT =
            "com.example.root.movie.DetialReviewFragment.EXTRA_CONTENT";

    private Unbinder unbinder;

    @BindView(R.id.content_review)
    TextView mcontent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detialreview,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String content = getArguments().getString(EXTRA_CONTENT);
        mcontent.setText(content);
    }

    public static DetialReviewFragment getInstance(String text){
        Bundle args = new Bundle();
        args.putString(EXTRA_CONTENT,text);
        DetialReviewFragment fragment = new DetialReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
