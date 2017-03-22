package com.bartoszuk.dinnerwise.model;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class RecipeChoice {

    public final long NO_RECIPE_CHOSEN = 0;

    private final Context context;
    private final Date date;
    final private long recipeOneId;
    final private long recipeTwoId;
    private long chosenRecipeId = NO_RECIPE_CHOSEN;

    RecipeChoice(Context context, Date date, long recipeOneId, long recipeTwoId) {
        this.context = context;
        this.date = date;
        this.recipeOneId = recipeOneId;
        this.recipeTwoId = recipeTwoId;
    }

    // Returns getRecipeOneId(), getRecipeTwoId() or NO_RECIPE_CHOSEN.
    public long getChosenRecipeId() {
        return chosenRecipeId;
    }

    public void setChosenRecipeId(long chosenRecipeId) {
        this.chosenRecipeId = chosenRecipeId;

        GroceryList currentList = GroceryList.forCurrentWeek(context);
        currentList.setRecipeOn(date.getDayOfWeek(), chosenRecipeId);
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
