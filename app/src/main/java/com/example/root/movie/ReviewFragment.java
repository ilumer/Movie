package com.example.root.movie;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.movie.net.MovieOkhttp;
import com.example.root.movie.model.RecyclerItemClickListener;
import com.example.root.movie.model.ReviewAdapter;
import com.example.root.movie.model.ReviewsModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReviewFragment extends Fragment {

    @BindView(R.id.review_list)
    RecyclerView rvReviews;

    ReviewAdapter adapter;
    List<ReviewsModel.ResultsBean> list = new ArrayList<>();
    ReviewHandler handler;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review,container,false);
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
        adapter = new ReviewAdapter(list);
        handler = new ReviewHandler(adapter,list);
        ReviewThread thread = new ReviewThread(getActivity().getIntent().
                getIntExtra(DetialFragment.EXTRA_ID,0));
        thread.start();
        rvReviews.setAdapter(adapter);
        rvReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvReviews.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ReviewsModel.ResultsBean m = adapter.getItem(position);
                DetialReviewFragment fragment = DetialReviewFragment.getInstance(m.getContent());
                getActivity().getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,fragment).
                        addToBackStack("remeview").
                        commit();
            }
        }));
    }

    private static class ReviewHandler extends android.os.Handler{
        private WeakReference<ReviewAdapter> adapter;
        private WeakReference<List<ReviewsModel.ResultsBean>> mList;

        public ReviewHandler(ReviewAdapter adapter, List<ReviewsModel.ResultsBean> list) {
            this.adapter = new WeakReference<>(adapter);
            this.mList = new WeakReference<>(list);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ReviewAdapter temp = adapter.get();
            List<ReviewsModel.ResultsBean> list = mList.get();
            if (temp!=null&&list!=null){
                list = ((ReviewsModel) msg.obj).getResults();
                temp.setmList(list);
            }

        }
    }

    public class ReviewThread extends Thread{
        int id;
        public ReviewThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            super.run();
            ReviewsModel model=MovieOkhttp.getReciew(id);
            Message message = handler.obtainMessage();
            message.obj = model;
            handler.sendMessage(message);
        }
    }
}
