package com.english.alin.learnenglish.activities.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Alin on 3/27/2016.
 */
public class PageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles [] = new String[] {"Reading", "Quiz"};

    public PageAdapter(FragmentManager fm, Context context){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ReadingText.newInstance();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
