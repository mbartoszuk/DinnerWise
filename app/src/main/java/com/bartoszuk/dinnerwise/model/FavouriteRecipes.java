package com.bartoszuk.dinnerwise.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class FavouriteRecipes {

    private static final Set<Recipe> favouriteRecipesCollection = new HashSet<>();

    public boolean contains(Recipe recipe) {
        return favouriteRecipesCollection.contains(recipe);
    }

    public void add(Recipe recipe) {
        favouriteRecipesCollection.add(recipe);
    }

    public void remove(Recipe recipe) {
        favouriteRecipesCollection.remove(recipe);
    }
}
