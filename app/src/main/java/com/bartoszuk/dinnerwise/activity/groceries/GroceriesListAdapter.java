package com.bartoszuk.dinnerwise.activity.groceries;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bartoszuk.dinnerwise.R;

/**
 * Created by Maria Bartoszuk on 15/03/2017.
 */

public class GroceriesListAdapter extends BaseExpandableListAdapter {

    private static final StrikethroughSpan STRIKETHROUGH_SPAN = new StrikethroughSpan();

    private final GroceriesActivity activity;
    private final LayoutInflater inflater;
    private final ExpandableListView listView;
    private final Resources resources;

    GroceriesListAdapter(GroceriesActivity activity, LayoutInflater inflater,
                         ExpandableListView listView, Resources resources) {
        this.activity = activity;
        this.inflater = inflater;
        this.listView = listView;
        this.resources = resources;
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 2;
    }

    @Override
    public String getGroup(int groupPosition) {
        return String.format("Recipe name %d", groupPosition + 1);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return String.format("Ingredient %d", childPosition + 1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * getChildrenCount(groupPosition) + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.groceries_recipe_list, parent, false);
        }
        final TextView recipeTitleView = (TextView) convertView.findViewById(R.id.recipe_title);
        final String recipeTitle = getGroup(groupPosition);
        recipeTitleView.setText(recipeTitle, TextView.BufferType.SPANNABLE);
        if (!isExpanded) {
            Spannable text = (Spannable) recipeTitleView.getText();
            text.setSpan(STRIKETHROUGH_SPAN, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // Strike through and collapse when recipe removed from the list
        final ImageButton collapseRecipeListButton = (ImageButton) convertView.findViewById(R.id.collapse_recipe_list);
        collapseRecipeListButton.setSelected(isExpanded);
        collapseRecipeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spannable text = (Spannable) recipeTitleView.getText();
                if (listView.isGroupExpanded(groupPosition)) {
                    text.setSpan(STRIKETHROUGH_SPAN, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    collapseRecipeListButton.setSelected(true);
                    listView.collapseGroup(groupPosition);
                } else {
                    text.removeSpan(STRIKETHROUGH_SPAN);
                    collapseRecipeListButton.setSelected(false);
                    listView.expandGroup(groupPosition, true);
                }
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.groceries_recipe_ingredient, parent, false);
        }
        final TextView ingredientTitleView = (TextView) convertView.findViewById(R.id.ingredient_title);
        String ingredientName = getChild(groupPosition, childPosition);
        ingredientTitleView.setText(ingredientName, TextView.BufferType.SPANNABLE);

        // Strike through when item checked.
        AppCompatCheckBox checkbox = (AppCompatCheckBox) convertView.findViewById(R.id.checkbox_icon);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Spannable text = (Spannable) ingredientTitleView.getText();
                if (isChecked) {
                    text.setSpan(STRIKETHROUGH_SPAN, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    text.removeSpan(STRIKETHROUGH_SPAN);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
