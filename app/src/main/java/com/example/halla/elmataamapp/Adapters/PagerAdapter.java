package com.example.halla.elmataamapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.example.halla.elmataamapp.fragments.ProfileFragment;
import com.example.halla.elmataamapp.fragments.TrustedFragment;
import com.example.halla.elmataamapp.fragments.NewsFeedFragment;
import com.example.halla.elmataamapp.fragments.RecommendationFragment;
import com.example.halla.elmataamapp.R;

/**
 * Created by halla on 12/19/2015.l
 * ]
 */
public class PagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    public int[] ICONS = new int[] {
            R.drawable.ic_av_timer_black_24dp,
           R.drawable.recommendation,
            R.drawable.trusted,
            R.  drawable.menu
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
                return new RecommendationFragment();
            case 2:
                return new TrustedFragment();
            case 3:
                return new ProfileFragment();

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
