package com.bartoszuk.dinnerwise.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maria Bartoszuk on 18/03/2017.
 */

public class GroceryListRecipe {

    private final DayOfWeek dayOfWeek;
    private final long recipeId;
    private boolean discarded = false;
    private final Set<String> checkedIngredients = new HashSet<>();

    // For saving into the database.
    private GroceryList list;

    GroceryListRecipe(DayOfWeek dayOfWeek, long recipeId) {
        this.dayOfWeek = dayOfWeek;
        this.recipeId = recipeId;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void discard() {
        discarded = true;
        if (list != null) {
            list.update(this);
        }
    }

    public void include() {
        discarded = false;
        if (list != null) {
            list.update(this);
        }
    }

    public boolean isIngredientChecked(String ingredient) {
        return checkedIngredients.contains(ingredient);
    }

    public void checkIngredient(String ingredient) {
        checkedIngredients.add(ingredient);
        if (list != null) {
            list.update(this);
        }
    }

    public void uncheckIngredient(String ingredient) {
        checkedIngredients.remove(ingredient);
        if (list != null) {
            list.update(this);
        }
    }

    void setList(GroceryList list) {
        this.list = list;
    }

    String dbCheckedIngredients() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (String ingredient : checkedIngredients) {
            if (!first) {
                builder.append("|");
            }
            builder.append(ingredient);
            first = false;
        }

        return builder.toString();
    }

    void loadCheckIngredientsFromDb(String encoded) {
        for (String ingredient : encoded.split("\\|")) {
            checkedIngredients.add(ingredient);
        }

    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GroceryListRecipe)) {
            return false;
        }
        GroceryListRecipe that = (GroceryListRecipe) other;
        return this.recipeId == that.recipeId;
    }

    @Override
    public int hashCode() {
        return (int) (recipeId % 100000007L);
    }
}
