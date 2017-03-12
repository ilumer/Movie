package com.example.root.movie;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.root.movie.PopMovie.PopMovieFragment;
import com.example.root.movie.fragment.RatedDetailFragment;
import com.example.root.movie.handler.UserInfoHandler;
import com.example.root.movie.data.source.local.MovieHelper;
import com.example.root.movie.model.FragmentCallback;
import com.example.root.movie.model.MovieInfo;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FragmentCallback{

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nvView)
    NavigationView nvView;
    @BindView(R.id.Drawer)
    DrawerLayout drawerLayout;
    @Nullable@BindView(R.id.fragment_container)
    FrameLayout container;
    @BindString(R.string.movie_favourite)
    String favTitle;
    @BindString(R.string.movie_popular)
    String popTitle;
    @BindString(R.string.drawer_open)
    String drawer_open;
    @BindString(R.string.drawer_close)

    String drawer_close;
    TextView mHeadname;
    private ActionBarDrawerToggle drawerToggle;
    private final UserInfoHandler handler = new UserInfoHandler(this);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            toolbar.setElevation(0);
        }
        setSupportActionBar(toolbar);
        setUpDrawerContent(nvView);
        drawerToggle = setUpDrawerToggle();
        drawerLayout.addDrawerListener(drawerToggle);
        View headerLayout = nvView.getHeaderView(0);
        mHeadname = (TextView) headerLayout.findViewById(R.id.name);
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(pager);
        if (container!=null){
            mode = Mode.multiple;
        }
        movieHelper.close();
    }

    public  void setNvAccountName(String name){
        mHeadname.setText(name);
    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpDrawerContent(NavigationView view){
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private ActionBarDrawerToggle setUpDrawerToggle(){
        return new ActionBarDrawerToggle(this,drawerLayout, toolbar,
                R.string.drawer_open,R.string.drawer_open);
    }

    public void selectDrawerItem(MenuItem item){
        drawerLayout.closeDrawers();
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
                return new PopMovieFragment();
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
    public void selectdMovie(MovieInfo m) {
        if (mode==Mode.single){
            Intent i = new Intent(this,DetialActivity.class);
            //i.putExtra(RatedDetailFragment.EXTRA_ID,m);
            startActivity(i);
        }else if (mode==Mode.multiple){
            FragmentManager manager = getSupportFragmentManager();
            RatedDetailFragment fragment = (RatedDetailFragment) manager.findFragmentById(R.id.fragment_container);
            if (fragment!=null){
                manager.beginTransaction()
                        .remove(fragment).commit();
            }
            manager.beginTransaction()
                    .add(R.id.fragment_container,RatedDetailFragment.getInstance(m.getId()))
                    .commit();
        }
    }

    public interface UpdateUI{
        void updateUI();
    }
}
