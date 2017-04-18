package com.bartoszuk.dinnerwise.activity.groceries;

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
import com.bartoszuk.dinnerwise.model.DayOfWeek;
import com.bartoszuk.dinnerwise.model.GroceryList;
import com.bartoszuk.dinnerwise.model.GroceryListRecipe;
import com.bartoszuk.dinnerwise.model.Ingredient;
import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipesDB;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Maria Bartoszuk on 15/03/2017.
 */

public class GroceriesByRecipeAdapter extends BaseExpandableListAdapter {

    private static final StrikethroughSpan STRIKETHROUGH_SPAN = new StrikethroughSpan();

    private final GroceryList groceryListModel;
    private final RecipesDB recipesDB;

    private final LayoutInflater inflater;
    private final ExpandableListView listView;

    GroceriesByRecipeAdapter(GroceriesActivity activity, LayoutInflater inflater,
                             ExpandableListView listView) {
        this.groceryListModel = GroceryList.forCurrentWeek(activity);
        this.recipesDB = RecipesDB.db(activity);
        this.inflater = inflater;
        this.listView = listView;
    }

    @Override
    public int getGroupCount() {
        return groceryListModel.size();
    }

    // Finds the number of ingredients of a recipe based on its position on the grocery list.
    @Override
    public int getChildrenCount(int groupPosition) {
        GroceryListRecipe recipeItem = getGroup(groupPosition);
        Recipe recipe = recipesDB.findRecipeById(recipeItem.getRecipeId());
        return recipe.getIngredients().size();
    }

    // Gets a single recipe, adds it to the list and sorts all selected recipes.
    @Override
    public GroceryListRecipe getGroup(int groupPosition) {
        ArrayList<DayOfWeek> days = new ArrayList<>();
        for (DayOfWeek selected : groceryListModel.selectedDays()) {
            days.add(selected);
        }
        Collections.sort(days);  // Sorting the recipes to be in the week-days order.
        return groceryListModel.recipeOn(days.get(groupPosition));
    }

    // Gets the single ingredients of a recipe.
    @Override
    public Ingredient getChild(int groupPosition, int childPosition) {
        GroceryListRecipe recipeItem = getGroup(groupPosition);
        Recipe recipe = recipesDB.findRecipeById(recipeItem.getRecipeId());
        return recipe.getIngredients().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return getGroup(groupPosition).hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getChild(groupPosition, childPosition).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    // Manages the view of a recipe in the grocery list.
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.groceries_recipe_list, parent, false);
        }
        final TextView recipeTitleView = (TextView) convertView.findViewById(R.id.recipe_title);
        final GroceryListRecipe recipeItem = getGroup(groupPosition);
        Recipe recipe = recipesDB.findRecipeById(recipeItem.getRecipeId());

        // Shows the item discarded in the UI, if model says so.
        if (recipeItem.isDiscarded()) {
            isExpanded = false;
        }

        // Strikes through the text on its whole length.
        recipeTitleView.setText(recipe.getTitle(), TextView.BufferType.SPANNABLE);
        if (!isExpanded) {
            Spannable text = (Spannable) recipeTitleView.getText();
            text.setSpan(STRIKETHROUGH_SPAN, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // Strikes through and collapse when recipe is removed from the list
        final ImageButton collapseRecipeListButton = (ImageButton) convertView.findViewById(R.id.collapse_recipe_list);
        collapseRecipeListButton.setSelected(isExpanded);
        collapseRecipeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spannable text = (Spannable) recipeTitleView.getText();
                if (listView.isGroupExpanded(groupPosition)) {
                    text.setSpan(STRIKETHROUGH_SPAN, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    collapseRecipeListButton.setSelected(true);
                    recipeItem.discard();
                    listView.collapseGroup(groupPosition);
                } else {
                    text.removeSpan(STRIKETHROUGH_SPAN);
                    collapseRecipeListButton.setSelected(false);
                    recipeItem.include();
                    listView.expandGroup(groupPosition, true);
                }
            }
        });

        return convertView;
    }

    // Manages the view of an ingredient in a recipe.
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.groceries_recipe_ingredient, parent, false);
        }
        final TextView ingredientTitleView = (TextView) convertView.findViewById(R.id.ingredient_title);
        final GroceryListRecipe recipeItem = getGroup(groupPosition);
        final Ingredient ingredient = getChild(groupPosition, childPosition);
        ingredientTitleView.setText(ingredient.toString(), TextView.BufferType.SPANNABLE);

        // Check the item if model says so.
        AppCompatCheckBox checkbox = (AppCompatCheckBox) convertView.findViewById(R.id.checkbox_icon);
        checkbox.setOnCheckedChangeListener(null);  // So that the onCheckedChanged does not fire.
        boolean ingredientChecked = recipeItem.isIngredientChecked(ingredient.toString());
        checkbox.setChecked(ingredientChecked);
        checkbox.jumpDrawablesToCurrentState();

        // Strike through the text on its whole length.
        Spannable text = (Spannable) ingredientTitleView.getText();
        if (ingredientChecked) {
            text.setSpan(STRIKETHROUGH_SPAN, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            text.removeSpan(STRIKETHROUGH_SPAN);
        }

        // Strike through when item checked.
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Spannable text = (Spannable) ingredientTitleView.getText();
                if (isChecked) {
                    recipeItem.checkIngredient(ingredient.toString());
                    text.setSpan(STRIKETHROUGH_SPAN, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    recipeItem.uncheckIngredient(ingredient.toString());
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
