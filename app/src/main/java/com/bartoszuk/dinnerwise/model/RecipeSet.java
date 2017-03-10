package com.bartoszuk.dinnerwise.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class RecipeSet {

    private static final List<Long> own = new ArrayList<>();
    private static final List<Long> favourites = new ArrayList<>();

    private final RecipesDB db;
    private final List<Long> recipesCollection;

    public static RecipeSet favourites(RecipesDB db) {
        return new RecipeSet(db, favourites);
    }

    public static RecipeSet own(RecipesDB db) {
        return new RecipeSet(db, own);
    }

    private RecipeSet(RecipesDB db, List<Long> recipesCollection) {
        this.db = db;
        this.recipesCollection = recipesCollection;
    }

    public boolean contains(long recipe) {
        return recipesCollection.contains(recipe);
    }

    public void remove(long recipe) {
        recipesCollection.remove(new Long(recipe));
    }

    public void add(long recipe) {
        if (!contains(recipe)) {
            recipesCollection.add(recipe);
        }
    }

    public int size(String query) {
        if (query == null) {
            return recipesCollection.size();
        }
        int count = 0;
        for (long recipeId : recipesCollection) {
            Recipe recipe = db.findRecipeById(recipeId);
            if (recipe.getTitle().toLowerCase().contains(query.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    public long nth(String query, int n) {
        if (query == null) {
            return recipesCollection.get(n);
        }
        int numberOfMatchingResultsSeen = 0;
        for (long recipeId : recipesCollection) {
            Recipe recipe = db.findRecipeById(recipeId);
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
