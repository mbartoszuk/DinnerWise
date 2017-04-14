package com.bartoszuk.dinnerwise.activity.groceries;

import android.support.v7.widget.AppCompatCheckBox;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.model.GroceryList;
import com.bartoszuk.dinnerwise.model.GroceryListByCategory;
import com.bartoszuk.dinnerwise.model.GroceryListRecipe;
import com.bartoszuk.dinnerwise.model.Ingredient;
import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipesDB;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Maria Bartoszuk on 26/03/2017.
 */

public class GroceriesByCategoryAdapter extends BaseExpandableListAdapter {

    private static final StrikethroughSpan STRIKETHROUGH_SPAN = new StrikethroughSpan();

    private final GroceryListByCategory groceryListModel;
    private final RecipesDB recipesDB;

    private final LayoutInflater inflater;
    private final ExpandableListView listView;

    GroceriesByCategoryAdapter(GroceriesActivity activity, LayoutInflater inflater,
                               ExpandableListView listView) {
        this.recipesDB = RecipesDB.db(activity);
        this.groceryListModel = new GroceryListByCategory(GroceryList.forCurrentWeek(activity), recipesDB);
        this.inflater = inflater;
        this.listView = listView;
    }

    @Override
    public int getGroupCount() {
        return groceryListModel.getCategories().size();
    }

    // Finds the number of ingredients of a recipe based on its position on the grocery list.
    @Override
    public int getChildrenCount(int groupPosition) {
        String category = getGroup(groupPosition);
        List<Pair<Ingredient, GroceryListRecipe>> ingredients = ingredientsInCategory(category);
        return ingredients.size();
    }

    // Gets a single recipe, adds it to the list and sorts all selected recipes.
    @Override
    public String getGroup(int groupPosition) {
        ArrayList<String> allCategories = new ArrayList<>();
        allCategories.addAll(groceryListModel.getCategories());
        String category = allCategories.get(groupPosition);
        return category;
    }

    // Gets the single ingredient of a recipe.
    @Override
    public Pair<Ingredient, GroceryListRecipe> getChild(int groupPosition, int childPosition) {
        String category = getGroup(groupPosition);
        List<Pair<Ingredient, GroceryListRecipe>> ingredients = ingredientsInCategory(category);
        return ingredients.get(childPosition);
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

    // Manages the view of a category in the grocery list.
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.groceries_category_list, parent, false);
        }
        final TextView recipeTitleView = (TextView) convertView.findViewById(R.id.category_title);
        final String category = getGroup(groupPosition);

        recipeTitleView.setText(category);

        return convertView;
    }

    // Manages the view of an ingredient in a recipe.
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.groceries_recipe_ingredient, parent, false);
        }
        final TextView ingredientTitleView = (TextView) convertView.findViewById(R.id.ingredient_title);
        final Pair<Ingredient, GroceryListRecipe> model = getChild(groupPosition, childPosition);
        final Ingredient ingredient = model.first;
        ingredientTitleView.setText(ingredient.toString(), TextView.BufferType.SPANNABLE);

        // Check the item if model says so.
        AppCompatCheckBox checkbox = (AppCompatCheckBox) convertView.findViewById(R.id.checkbox_icon);
        checkbox.setOnCheckedChangeListener(null);  // So that the onCheckedChanged does not fire.
        boolean ingredientChecked = model.second.isIngredientChecked(ingredient.toString());
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
                    model.second.checkIngredient(ingredient.toString());
                    text.setSpan(STRIKETHROUGH_SPAN, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    model.second.uncheckIngredient(ingredient.toString());
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

    // Creates a list of ingredients (from specific recipes), sorted alphabetically, then by recipe ID.
    private List<Pair<Ingredient, GroceryListRecipe>> ingredientsInCategory(String category) {
        List<Pair<Ingredient, GroceryListRecipe>> ingredients = new ArrayList<>();
        for (Map.Entry<GroceryListRecipe, Recipe> recipe : groceryListModel.recipesIn(category).entrySet()) {
            if (!recipe.getKey().isDiscarded()) {
                // Doesn't display the ingredient if it has been crossed off.
                for (Ingredient ingredient : recipe.getValue().getIngredients()) {
                    if (ingredient.getCategory().equals(category)) {
                        ingredients.add(Pair.create(ingredient, recipe.getKey()));
                    }
                }
            }
        }
        Collections.sort(ingredients, new Comparator<Pair<Ingredient, GroceryListRecipe>>() {

            Collator collator = Collator.getInstance(Locale.US);

            // Comparing strings of names of recipes for alphabetical sorting.
            @Override
            public int compare(Pair<Ingredient, GroceryListRecipe> left, Pair<Ingredient, GroceryListRecipe> right) {
                int ingredientComparison = collator.compare(left.first.getName(), right.first.getName());
                if (ingredientComparison != 0) {
                    return ingredientComparison;
                }
                long recipeIdComparison = left.second.getRecipeId() - right.second.getRecipeId();
                if (recipeIdComparison < 0) {
                    return -1;
                } else if (recipeIdComparison == 0) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        return ingredients;
    }
}
