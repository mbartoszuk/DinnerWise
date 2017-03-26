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

    private GroceriesActivity activity;

    public GroceriesPagerAdapter(FragmentManager fm, GroceriesActivity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Arrays.asList("RECIPES", "CATEGORIES").get(position);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ByRecipeFragment byRecipe = new ByRecipeFragment();
                byRecipe.setActivity(activity);
                return byRecipe;
            case 1:
                ByRecipeFragment byGroceries = new ByRecipeFragment();
                byGroceries.setActivity(activity);
                return byGroceries;
            default: throw new IllegalArgumentException("Invalid position " + position);
        }
    }
}
