package com.example.root.movie.detialmovie;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.movie.R;
import com.example.root.movie.helper.IMDBHelper;
import com.example.root.movie.model.Trailers;
import com.example.root.movie.repositories.MovieRepository;
import com.example.root.movie.repositories.impl.MovieRepositoryImpl;
import com.example.root.movie.util.Injection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ilumer on 17-3-16.
 */

public class DetailMovieFragment extends Fragment implements DetailMovieContract.View{

    public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    Unbinder unbinder;
    DetailMovieContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.movie_screen)
    ImageView movie;
    @BindView(R.id.release_date)
    TextView date;
    @BindView(R.id.movie_title)
    TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >=21) {
            toolbar.setElevation(0);
        }
        MovieRepository repository = new MovieRepositoryImpl(getActivity().getApplicationContext());
        presenter = new DetailMovieFragmentPresenter(
                this,
                Injection.provideSchedulerProvider(),
                repository
        );
        int id = getArguments().getInt(EXTRA_MOVIE_ID);
        presenter.loadDetailMovie(id);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unSubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static DetailMovieFragment newInstance(int movieId){
        Bundle args = new Bundle();
        args.putInt(EXTRA_MOVIE_ID,movieId);
        DetailMovieFragment fragment = new DetailMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showMovie(String url) {
        Glide.with(this)
                .load(IMDBHelper.getImageBsUri(getActivity(),url))
                .into(movie);
    }

    @Override
    public void showTitle(String str) {
        title.setText(str);
    }

    @Override
    public void showDate(String date) {
        title.setText(date);
    }

    @Override
    public void showTrailer(List<Trailers.Trailer> trailerList) {

    }
}
