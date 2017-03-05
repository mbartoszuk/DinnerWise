package com.bartoszuk.dinnerwise.model;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class RecipeChoice {

    final private int recipeOneId;
    final private int recipeTwoId;

    public RecipeChoice(int recipeOneId, int recipeTwoId) {
        this.recipeOneId = recipeOneId;
        this.recipeTwoId = recipeTwoId;
    }

    public int getRecipeOneId() {
        return recipeOneId;
    }

    public int getRecipeTwoId() {
        return recipeTwoId;
    }
}
