package com.bartoszuk.dinnerwise.activity.groceries;

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

public class ByCategoryFragment extends Fragment implements Refresh {

    private GroceriesActivity activity;
    private ExpandableListView listView;
    private GroceriesByCategoryAdapter listAdapter;

    public void setActivity(GroceriesActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_groceries_by_category, container, false);

        listView = (ExpandableListView) layout.findViewById(R.id.recipe_groceries_list_view);
        listAdapter = new GroceriesByCategoryAdapter(activity, inflater, listView);
        listView.setAdapter(listAdapter);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }

        return layout;
    }

    // Letting the view know that the data was change and the interface needs to be redrawn.
    @Override
    public void refresh() {
        listAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
