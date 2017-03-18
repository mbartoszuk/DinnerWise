package com.bartoszuk.dinnerwise.model;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Maria Bartoszuk on 18/03/2017.
 */

public class GroceryList {

    private static final GroceryList theOnlyOne = new GroceryList();

    static {
        theOnlyOne.setRecipeOn(DayOfWeek.MONDAY, 1);
        theOnlyOne.setRecipeOn(DayOfWeek.TUESDAY, 2);
    }

    public static GroceryList forCurrentWeek(Context context) {
        return theOnlyOne;
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
