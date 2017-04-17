package net.ddmax.plantpano.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import net.ddmax.plantpano.R;
import net.ddmax.plantpano.adapter.MainViewPagerAdapter;
import net.ddmax.plantpano.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.navigation) BottomNavigationView mNavigation;
    private MenuItem prevItem;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setUpBottomNavigation();
        setUpViewPager();

    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
    }

    private void setUpViewPager() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevItem != null) {
                    prevItem.setChecked(false);
                } else {
                    mNavigation.getMenu().getItem(0).setChecked(false);
                }
                MenuItem currentItem = mNavigation.getMenu().getItem(position);
                currentItem.setChecked(true);
                prevItem = currentItem;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
    }

    private void setUpBottomNavigation() {
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
                        return true;
//                    case R.id.navigation_dashboard:
//                        mViewPager.setCurrentItem(1);
//                        return true;
                    case R.id.navigation_notifications:
                        mViewPager.setCurrentItem(1);
                        return true;
                }
                return false;
            }
        });
    }

}
