package net.ddmax.plantpano.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.ddmax.plantpano.R;
import net.ddmax.plantpano.adapter.MainViewPagerAdapter;
import net.ddmax.plantpano.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager) private ViewPager mViewPager;
    @BindView(R.id.navigation) private BottomNavigationView mNavigation;
    private MenuItem prevItem;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setUpViewPager();
        setUpNavigation();

    }

    @Override
    public void initToolBar() {

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

    private void setUpNavigation() {
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_dashboard:
                        mViewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_notifications:
                        mViewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });
    }

}
