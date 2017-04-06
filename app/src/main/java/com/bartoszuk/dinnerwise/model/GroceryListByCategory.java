package com.bartoszuk.dinnerwise.model;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Maria Bartoszuk on 26/03/2017.
 */

public class GroceryListByCategory {

    private final GroceryList list;
    private final RecipesDB db;

    public GroceryListByCategory(GroceryList list, RecipesDB db) {
        this.list = list;
        this.db = db;
    }

    // Informing what categories are there (all categories) to sort by.
    public SortedSet<String> getCategories() {
        TreeSet<String> categories = new TreeSet<>();
        // Taking all the chosen days.
        for (DayOfWeek day : list.selectedDays()) {
            // Taking the recipe chosen for each day.
            GroceryListRecipe groceryListRecipe = list.recipeOn(day);
            // Taking only the recipes, which are not crossed off.
            if (!groceryListRecipe.isDiscarded()) {
                Recipe recipe = db.findRecipeById(groceryListRecipe.getRecipeId());
                // For every ingredient of the recipe, add it to the correct category.
                for (Ingredient ingredient : recipe.getIngredients()) {
                    categories.add(ingredient.getCategory());
                }
            }
        }
        return categories;
    }

    // Takes a specific category and returns all the ingredients in that category.
    public Map<GroceryListRecipe, Recipe> recipesIn(String category) {
        HashMap<GroceryListRecipe, Recipe> inCategory = new HashMap<>();
        for (DayOfWeek day : list.selectedDays()) {
            GroceryListRecipe groceryListRecipe = list.recipeOn(day);
            if (!groceryListRecipe.isDiscarded()) {
                Recipe recipe = db.findRecipeById(groceryListRecipe.getRecipeId());
                if (recipe.hasIngredientsIn(category)) {
                    inCategory.put(groceryListRecipe, recipe);
                }
            }
        }
        return inCategory;
    }
}
