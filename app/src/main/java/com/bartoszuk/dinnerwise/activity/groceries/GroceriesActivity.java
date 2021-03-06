package com.bartoszuk.dinnerwise.activity.groceries;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.LogOut;
import com.bartoszuk.dinnerwise.activity.favourites.FavouritesActivity;
import com.bartoszuk.dinnerwise.activity.ownrecipes.OwnRecipesActivity;
import com.bartoszuk.dinnerwise.activity.week.WeekActivity;
import com.roughike.bottombar.BottomBarTab;

/**
 * Created by Maria Bartoszuk on 15/03/2017.
 */

public class GroceriesActivity extends LogOut {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.groceries_title));
        setContentView(R.layout.activity_groceries);

        //Removing the shadow from the action bar to blend with the tabs.
        getSupportActionBar().setElevation(0);

        TabLayout tabs = (TabLayout) findViewById(R.id.groceries_sorting_tabs);
        ByRecipeFragment byRecipe = new ByRecipeFragment();
        byRecipe.setActivity(this);
        ByCategoryFragment byCategory = new ByCategoryFragment();
        byCategory.setActivity(this);
        final Fragment[] fragments = {byRecipe, byCategory};
        ViewPager viewPager = (ViewPager) findViewById(R.id.groceries_view_pager);
        viewPager.setAdapter(new GroceriesPagerAdapter(getSupportFragmentManager(), byRecipe, byCategory));
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Refresh fragment = (Refresh) fragments[position];
                fragment.refresh();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Own Recipes Tab
        BottomBarTab ownRecipesButton = (BottomBarTab) findViewById(R.id.tab_ownrecipes);
        ownRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OwnRecipesActivity.class);
                startActivity(intent);
            }
        });

        // Favourites Tab
        BottomBarTab favouritesButton = (BottomBarTab) findViewById(R.id.tab_favourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FavouritesActivity.class);
                startActivity(intent);
            }
        });
    }

    // Find the Week Activity on the stack and goes back to it (to the beginning of the stack).
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WeekActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // Adding the menu to the top of the activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.groceries_secondary_menu, menu);
        return true;
    }
}
