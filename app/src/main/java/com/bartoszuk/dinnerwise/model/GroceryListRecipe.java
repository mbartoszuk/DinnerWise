package com.bartoszuk.dinnerwise.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maria Bartoszuk on 18/03/2017.
 */

public class GroceryListRecipe {

    private final long recipeId;
    private boolean discarded = false;
    private final Set<String> checkedIngredients = new HashSet<>();

    GroceryListRecipe(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void discard() {
        discarded = true;
    }

    public void include() {
        discarded = false;
    }

    public boolean isIngredientChecked(String ingredient) {
        return checkedIngredients.contains(ingredient);
    }

    public void checkIngredient(String ingredient) {
        checkedIngredients.add(ingredient);
    }

    public void uncheckIngredient(String ingredient) {
        checkedIngredients.remove(ingredient);
    }
}
