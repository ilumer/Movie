package com.example.root.movie;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.root.movie.helper.MovieHelper;
import com.example.root.movie.model.FragmentCallback;
import com.example.root.movie.model.MovieData;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FragmentCallback{

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @Nullable@BindView(R.id.fragment_container)
    FrameLayout container;
    @BindString(R.string.movie_favourite)
    String favTitle;
    @BindString(R.string.movie_popular)
    String popTitle;

    ViewPageAdapter adapter;
    Mode mode = Mode.single;
    enum Mode{
        single,
        multiple
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        final String[] title = {popTitle,favTitle};
        adapter = new ViewPageAdapter(getSupportFragmentManager(),title);
        pager.setAdapter(adapter);
        MovieHelper movieHelper = new MovieHelper(this);
        movieHelper.getReadableDatabase();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==1) {
                    adapter.finishUpdate(pager);
                    FavouriteFragment favouriteFragment = (FavouriteFragment) adapter.instantiateItem(pager, position);
                    if (favouriteFragment != null) {
                        favouriteFragment.updateUI();
                    }
                }
                // how to update fragment
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(pager);
        if (container!=null){
            mode = Mode.multiple;
        }
    }

    public class ViewPageAdapter extends FragmentStatePagerAdapter{

        public final String[] title ;

        public ViewPageAdapter(FragmentManager fm,String[] title) {
            super(fm);
            this.title = title;
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return new MainFragment();
            }else {
                return new FavouriteFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    @Override
    public void selectdMovie(MovieData.ResultsBean m) {
        if (mode==Mode.single){
            Intent i = new Intent(this,DetialActivity.class);
            i.putExtra(DetialFragment.EXTRA_MOVIE_RESULTBEAN,m);
            startActivity(i);
        }else if (mode==Mode.multiple){
            FragmentManager manager = getSupportFragmentManager();
            DetialFragment  fragment = (DetialFragment) manager.findFragmentById(R.id.fragment_container);
            if (fragment!=null){
                manager.beginTransaction()
                        .remove(fragment).commit();
            }
            manager.beginTransaction()
                    .add(R.id.fragment_container,DetialFragment.newInstance(m))
                    .commit();
        }
    }

    public interface UpdateUI{
        void updateUI();
    }
}
