package com.bartoszuk.dinnerwise.model;

import java.util.Arrays;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class RecipesDB {

    public Recipe findRecipeById(int id) {
        Recipe recipe = new Recipe(id);
        recipe.setTitle("Aubergine & Couscous");
        recipe.setDescription("an awesome salad");
        recipe.setNumberOfServings(1);
        recipe.setPreparationTimeInMinutes(30);
        recipe.setIngredients(Arrays.asList("aubergine", "couscous"));
        recipe.setDirections("mix aubergine and couscous");
        return recipe;
    }
}
