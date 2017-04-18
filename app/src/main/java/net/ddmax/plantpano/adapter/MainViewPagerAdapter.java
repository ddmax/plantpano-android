package net.ddmax.plantpano.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.ddmax.plantpano.ui.fragment.ExploreFragment;
import net.ddmax.plantpano.ui.fragment.HomeFragment;
import net.ddmax.plantpano.ui.fragment.ProfileFragment;

/**
 * @author ddMax
 * @since 2017-02-28 11:45 PM.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new Fragment[3];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = HomeFragment.newInstance();
                    break;
//                case 1:
//                    fragments[position] = ExploreFragment.newInstance();
//                    break;
                case 1:
                    fragments[position] = ProfileFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
