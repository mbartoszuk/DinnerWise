package com.bartoszuk.dinnerwise.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class RecipeSet {

    private static final RecipeSet favourites = new RecipeSet();
    private static final RecipeSet own = new RecipeSet();

    // Some data to present
    static {
        //own.add(3);
    }

    private final List<Integer> recipesCollection = new LinkedList<>();

    public static RecipeSet favourites() {
        return favourites;
    }

    public static RecipeSet own() {
        return own;
    }

    private RecipeSet() {}

    public boolean contains(int recipe) {
        return recipesCollection.contains(recipe);
    }

    public void remove(int recipe) {
        recipesCollection.remove(new Integer(recipe));
    }

    public void add(int recipe) {
        if (!contains(recipe)) {
            recipesCollection.add(recipe);
        }
    }

    public int size(String query) {
        if (query == null) {
            return recipesCollection.size();
        }
        int count = 0;
        for (int recipeId : recipesCollection) {
            Recipe recipe = RecipesDB.db().findRecipeById(recipeId);
            if (recipe.getTitle().toLowerCase().contains(query.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    public int nth(String query, int n) {
        if (query == null) {
            return recipesCollection.get(n);
        }
        int numberOfMatchingResultsSeen = 0;
        for (int recipeId : recipesCollection) {
            Recipe recipe = RecipesDB.db().findRecipeById(recipeId);
            if (recipe.getTitle().toLowerCase().contains(query.toLowerCase())) {
                numberOfMatchingResultsSeen++;
            }
            if (n == numberOfMatchingResultsSeen - 1) {
                return recipeId;
            }
        }
        throw new RuntimeException("recipe not found");
    }
}
