package com.bartoszuk.dinnerwise.activity.groceries;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maria Bartoszuk on 26/03/2017.
 */

public class GroceriesPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public GroceriesPagerAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Arrays.asList("RECIPES", "CATEGORIES").get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }
}
