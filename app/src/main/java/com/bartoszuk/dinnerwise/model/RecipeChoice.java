package com.bartoszuk.dinnerwise.model;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class RecipeChoice {

    final private String recipeOneId;
    final private String recipeTwoId;

    public RecipeChoice(String recipeOneId, String recipeTwoId) {
        this.recipeOneId = recipeOneId;
        this.recipeTwoId = recipeTwoId;
    }

    public String getRecipeOneId() {
        return recipeOneId;
    }

    public String getRecipeTwoId() {
        return recipeTwoId;
    }
}
