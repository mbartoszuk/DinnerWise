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
    
    public SortedSet<String> getCategories() {
        TreeSet<String> categories = new TreeSet<>();
        for (DayOfWeek day : list.selectedDays()) {
            GroceryListRecipe groceryListRecipe = list.recipeOn(day);
            if (!groceryListRecipe.isDiscarded()) {
                Recipe recipe = db.findRecipeById(groceryListRecipe.getRecipeId());
                for (Ingredient ingredient : recipe.getIngredients()) {
                    categories.add(ingredient.getCategory());
                }
            }
        }
        return categories;
    }

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
