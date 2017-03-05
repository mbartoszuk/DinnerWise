package com.bartoszuk.dinnerwise.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class FavouriteRecipes {

    private static final List<Recipe> favouriteRecipesCollection = new LinkedList<>();

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
