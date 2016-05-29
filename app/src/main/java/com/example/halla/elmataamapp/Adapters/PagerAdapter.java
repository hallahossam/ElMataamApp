package com.example.halla.elmataamapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.example.halla.elmataamapp.fragments.ExpertsFragment;
import com.example.halla.elmataamapp.fragments.MenuFragment;
import com.example.halla.elmataamapp.fragments.NewsFeedFragment;
import com.example.halla.elmataamapp.fragments.RecommendationFragment;
import com.example.halla.elmataamapp.R;

/**
 * Created by halla on 12/19/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    public int[] ICONS = new int[] {
            android.R.drawable.star_big_off,
            android.R.drawable.star_big_on,
            R.drawable.recommendation,
            R.drawable.menu
    };

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NewsFeedFragment();
            case 1:
                return new ExpertsFragment();
            case 2:
                return new RecommendationFragment();
            case 3:
                return new MenuFragment();

        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Yes";
            case 1:
                return "No";

            case 2:
                return "Maybe";

            case 3:
                return "Exactly";
        }
        return super.getPageTitle(position);
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int getPageIconResId(int position) {
        return ICONS[position];
    }
}
