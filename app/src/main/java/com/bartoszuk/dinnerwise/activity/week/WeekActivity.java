package com.bartoszuk.dinnerwise.activity.week;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.LogOut;
import com.bartoszuk.dinnerwise.activity.favourites.FavouritesActivity;
import com.bartoszuk.dinnerwise.activity.groceries.GroceriesActivity;
import com.bartoszuk.dinnerwise.activity.ownrecipes.OwnRecipesActivity;
import com.bartoszuk.dinnerwise.model.RecipesDB;
import com.roughike.bottombar.BottomBarTab;

/**
 * Created by Maria Bartoszuk on 01/02/2017.
 */

public class WeekActivity extends LogOut {

    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.week_activity_title));
        setContentView(R.layout.week_activity);

        listView = (ExpandableListView) findViewById(R.id.weeklyListView);
        listView.setAdapter(new WeekListAdapter(RecipesDB.db(getApplicationContext()), this, getBaseContext(), getLayoutInflater()));
        setExpandArrowPosition();

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

        // Groceries Tab
        BottomBarTab groceriesButton = (BottomBarTab) findViewById(R.id.tab_groceries);
        groceriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GroceriesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setExpandArrowPosition();
    }

    // Setting the position of the dropdown and collapses arrow icons.
    private void setExpandArrowPosition() {
        int width = dpi(48);
        int marginRight = dpi(16);
        int right = listView.getWidth() - marginRight;
        int left = right - width;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            listView.setIndicatorBounds(left, right);
        } else {
            listView.setIndicatorBoundsRelative(left, right);
        }
    }

    // Converting from DPI to px.
    private static int dpi(int dpiValue) {
        return dpiValue * Resources.getSystem().getDisplayMetrics().densityDpi
                / DisplayMetrics.DENSITY_DEFAULT;
    }

    // Adding the menu to the top of the activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.week_activity_secondary_menu, menu);
        return true;
    }
}
