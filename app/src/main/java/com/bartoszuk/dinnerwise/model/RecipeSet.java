package com.bartoszuk.dinnerwise.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class RecipeSet {

    private static final RecipeSet favourites = new RecipeSet();
    private static final RecipeSet own = new RecipeSet();

    // Some data to present
    static {
        Recipe first = new Recipe("1");
        first.setTitle("Aubergine and Couscous");
        first.setDescription("Amazing salad and so easy to make.");
        first.setNumberOfServings(2);
        first.setPreparationTimeInMinutes(30);
        first.setIngredients(Arrays.asList("aubergine", "couscous"));
        first.setDirections("Mix the aubergine and couscous.");
        own.add(first);

        Recipe second = new Recipe("2");
        second.setTitle("Cauliflower Soup");
        second.setDescription("Great tasting, natural and so quick.");
        second.setNumberOfServings(2);
        second.setPreparationTimeInMinutes(20);
        second.setIngredients(Arrays.asList("onion", "cauliflower", "potatoes"));
        second.setDirections("Chop, fry, add boulion and blend.");
        own.add(second);
    }

    private final List<Recipe> recipesCollection = new LinkedList<>();

    public static RecipeSet favourites() {
        return favourites;
    }

    public static RecipeSet own() {
        return own;
    }

    private RecipeSet() {}

    public boolean contains(Recipe recipe) {
        return recipesCollection.contains(recipe);
    }

    public void add(Recipe recipe) {
        if (recipe.getId() == null) {
            recipe.setId(UUID.randomUUID().toString());
        }
        recipesCollection.add(recipe);
    }

    public void remove(Recipe recipe) {
        recipesCollection.remove(recipe);
    }

    public int size() {
        return recipesCollection.size();
    }

    public Recipe nth(int n) {
        return recipesCollection.get(n);
    }
}
