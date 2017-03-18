package com.bartoszuk.dinnerwise.model;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class RecipeChoice {

    public final long NO_RECIPE_CHOSEN = 0;

    final private long recipeOneId;
    final private long recipeTwoId;
    private long chosenRecipeId = NO_RECIPE_CHOSEN;

    public RecipeChoice(long recipeOneId, long recipeTwoId) {
        this.recipeOneId = recipeOneId;
        this.recipeTwoId = recipeTwoId;
    }

    // Returns getRecipeOneId(), getRecipeTwoId() or NO_RECIPE_CHOSEN.
    public long getChosenRecipeId() {
        return chosenRecipeId;
    }

    public void setChosenRecipeId(long chosenRecipeId) {
        this.chosenRecipeId = chosenRecipeId;
    }

    public long getRecipeOneId() {
        return recipeOneId;
    }

    public long getRecipeTwoId() {
        return recipeTwoId;
    }
}
