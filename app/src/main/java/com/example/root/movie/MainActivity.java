package com.example.root.movie;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.root.movie.Helper.MovieHelper;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindString(R.string.movie_favourite)
    String favTitle;
    @BindString(R.string.movie_popular)
    String popTitle;

    ViewPageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        MovieHelper movieHelper = new MovieHelper(this);
        movieHelper.getReadableDatabase();
        //pager.setOffscreenPageLimit(0);
        /*tabLayout.addTab(tabLayout.newTab().setText(popTitle));
        tabLayout.addTab(tabLayout.newTab().setText(favTitle));
        tabLayout.setupWithViewPager(pager);*/
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
    }

    public class ViewPageAdapter extends FragmentStatePagerAdapter{

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
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
    }

    public interface UpdateUI{
        void updateUI();
    }
}
