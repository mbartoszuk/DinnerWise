package com.bartoszuk.dinnerwise.activity.groceries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;

import com.bartoszuk.dinnerwise.R;

/**
 * Created by Maria Bartoszuk on 15/03/2017.
 */

public class GroceriesActivity extends AppCompatActivity {

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
        listAdapter = new GroceriesListAdapter(this, getLayoutInflater(), listView, getResources());
        listView.setAdapter(listAdapter);
        ignoreClicksOnGroupItems();
        expandAllGroups();
    }

    // All recipe ingredients are immediately visible. The list only collapses if the recipe
    // is dismissed from groceries.
    private void expandAllGroups() {
        for (int groupPosition = 0; groupPosition < listAdapter.getGroupCount(); groupPosition++) {
            listView.expandGroup(groupPosition, false);
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
