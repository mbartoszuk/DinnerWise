package com.bartoszuk.dinnerwise.model;

import android.content.Context;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class RecipeChoice {

    public static final long NO_RECIPE_CHOSEN = 0;

    private final Context context;
    private final Date date;
    final private long recipeOneId;
    final private long recipeTwoId;

    RecipeChoice(Context context, Date date, long recipeOneId, long recipeTwoId) {
        this.context = context;
        this.date = date;
        this.recipeOneId = recipeOneId;
        this.recipeTwoId = recipeTwoId;
    }

    // Returns getRecipeOneId(), getRecipeTwoId() or NO_RECIPE_CHOSEN.
    public long getChosenRecipeId() {
        GroceryList groceryList = GroceryList.forCurrentWeek(context);
        GroceryListRecipe recipe = groceryList.recipeOn(date.getDayOfWeek());
        if (recipe == null) {
            return NO_RECIPE_CHOSEN;
        } else {
            return recipe.getRecipeId();
        }
    }

    public void setChosenRecipeId(long chosenRecipeId) {
        GroceryList currentList = GroceryList.forCurrentWeek(context);
        if (chosenRecipeId != NO_RECIPE_CHOSEN) {
            currentList.setRecipeOn(date.getDayOfWeek(), chosenRecipeId);
        } else {
            currentList.clearRecipeOn(date.getDayOfWeek());
        }
    }

    public long getRecipeOneId() {
        return recipeOneId;
    }

    public long getRecipeTwoId() {
        return recipeTwoId;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RecipeChoice)) {
            return false;
        }
        RecipeChoice that = (RecipeChoice) other;
        return this.date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }
}
