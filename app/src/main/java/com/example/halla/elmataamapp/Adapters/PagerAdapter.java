package com.example.halla.elmataamapp.adapters;

import android.os.Bundle;
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
String userId;
    String [] userProfile;
    public PagerAdapter(FragmentManager fm, String userId, String [] userProfile) {
        super(fm);
        this.userId = userId;
        this.userProfile = userProfile;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        switch (position){
            case 0:
                NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
                newsFeedFragment.setArguments(bundle);
                return newsFeedFragment;
            case 1:
                RecommendationFragment recommendationFragment = new RecommendationFragment();
                recommendationFragment.setArguments(bundle);
                return recommendationFragment;
            case 2:
                TrustedFragment trustedFragment = new TrustedFragment();
                trustedFragment.setArguments(bundle);
                return trustedFragment;
            case 3:
                ProfileFragment profileFragment = new ProfileFragment();
                bundle.putStringArray("userProfile",userProfile);
                profileFragment.setArguments(bundle);
                return profileFragment;

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
