package com.bartoszuk.dinnerwise.activity.groceries;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.bartoszuk.dinnerwise.R;

/**
 * Created by Maria Bartoszuk on 26/03/2017.
 */

public class ByRecipeFragment extends Fragment {

    private GroceriesActivity activity;
    private ExpandableListView listView;
    private GroceriesByRecipeAdapter listAdapter;

    public void setActivity(GroceriesActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_groceries_by_recipe, container, false);

        listView = (ExpandableListView) layout.findViewById(R.id.recipe_groceries_list_view);
        listAdapter = new GroceriesByRecipeAdapter(activity, inflater, listView);
        listAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                collapseDisabledGroupsAndExpandOthers();
            }
        });
        listView.setAdapter(listAdapter);
        ignoreClicksOnGroupItems();
        collapseDisabledGroupsAndExpandOthers();

        return layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
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
}
