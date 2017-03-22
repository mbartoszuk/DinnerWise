package com.bartoszuk.dinnerwise.activity.groceries;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;

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

    private ExpandableListView listView;
    private GroceriesListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.groceries_title));
        setContentView(R.layout.activity_groceries);

        //Removing the shadow from the action bar to blend with the tabs.
        getSupportActionBar().setElevation(0);

        listView = (ExpandableListView) findViewById(R.id.recipe_groceries_list_view);
        listAdapter = new GroceriesListAdapter(this, getLayoutInflater(), listView);
        listAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                collapseDisabledGroupsAndExpandOthers();
            }
        });
        listView.setAdapter(listAdapter);
        ignoreClicksOnGroupItems();
        collapseDisabledGroupsAndExpandOthers();

        BottomBarTab ownRecipesButton = (BottomBarTab) findViewById(R.id.tab_ownrecipes);
        ownRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OwnRecipesActivity.class);
                startActivity(intent);
            }
        });

        BottomBarTab favouritesButton = (BottomBarTab) findViewById(R.id.tab_favourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FavouritesActivity.class);
                startActivity(intent);
            }
        });
    }

    // Find the Week Activity on the stack and goes back to it (to the beginning of the tack).
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WeekActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // All recipe ingredients are visible for all recipes that are not dismissed.
    // The list only collapses if the recipe is dismissed from groceries.
    private void collapseDisabledGroupsAndExpandOthers() {
        for (int groupPosition = 0; groupPosition < listAdapter.getGroupCount(); groupPosition++) {
            boolean discarded = listAdapter.getGroup(groupPosition).isDiscarded();
            // if discarded but expanded (incorrect) or not discarded but collapsed (incorrect)
            if (discarded == listView.isGroupExpanded(groupPosition)) {
                if (discarded) {
                    listView.collapseGroup(groupPosition);
                } else {
                    listView.expandGroup(groupPosition, false);
                }
            }
        }
    }

    // Groups are expanded by default and only expand/collapse on a custom button.
    private void ignoreClicksOnGroupItems() {
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    /** Adding the menu to the top of the activity. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.groceries_secondary_menu, menu);
        return true;
    }
}
