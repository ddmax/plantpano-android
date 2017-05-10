package net.ddmax.plantpano.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import net.ddmax.plantpano.R;
import net.ddmax.plantpano.adapter.NotificationFragmentPagerAdapter;
import net.ddmax.plantpano.base.BaseActivity;
import net.ddmax.plantpano.ui.fragment.NotificationFragment;

import butterknife.BindView;

public class NotificationActivity extends BaseActivity {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.top_tabs) TabLayout mTabLayout;
    @BindView(R.id.view_pager) ViewPager mViewPager;
    private NotificationFragmentPagerAdapter pagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_notification;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        // Setup fragments
        pagerAdapter = new NotificationFragmentPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(pagerAdapter);
        // Setup TabLayout
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("消息通知");
        setSupportActionBar(mToolbar);
    }

    // Switch between fragments
    private void switchFragment(Fragment fragment, boolean addToBackStack) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                R.anim.abc_fade_in, R.anim.abc_fade_out)
                .replace(R.id.view_pager, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        invalidateOptionsMenu();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        final NotificationFragment fragment = new NotificationFragment();
//        switchFragment(fragment, true);
    }
}
