package com.bartoszuk.dinnerwise.model;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Maria Bartoszuk on 18/03/2017.
 */

public class GroceryList {

    private static final GroceryList currentGroceryList = new GroceryList();

    public static GroceryList forCurrentWeek(Context context) {
        return currentGroceryList;
    }

    private final HashMap<DayOfWeek, GroceryListRecipe> recipes = new HashMap<>();

    public int size() {
        return recipes.size();
    }

    public Iterable<DayOfWeek> selectedDays() {
        return recipes.keySet();
    }

    public GroceryListRecipe recipeOn(DayOfWeek dayOfWeek) {
        return recipes.get(dayOfWeek);
    }

    public void clearRecipeOn(DayOfWeek dayOfWeek) {
        recipes.remove(dayOfWeek);
    }

    public void setRecipeOn(DayOfWeek dayOfWeek, long recipeId) {
        recipes.put(dayOfWeek, new GroceryListRecipe(recipeId));
    }
}
