package com.bartoszuk.dinnerwise.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class RecipesDB {

    private static final RecipesDB db = new RecipesDB();

    private int nextId = 1;
    private final Map<Integer, Recipe> allRecipes = new HashMap<>();

    public static RecipesDB db() {
        return db;
    }

    private RecipesDB() {
        Recipe first = new Recipe();
        first.setTitle("Aubergine & Couscous");
        first.setDescription("an awesome salad");
        first.setNumberOfServings(1);
        first.setPreparationTimeInMinutes(30);
        first.setIngredients(Arrays.asList("aubergine", "couscous"));
        first.setDirections("mix aubergine and couscous");
        add(first);

        Recipe second = new Recipe();
        second.setTitle("Cauliflower Soup");
        second.setDescription("Great tasting, natural and so quick.");
        second.setNumberOfServings(2);
        second.setPreparationTimeInMinutes(20);
        second.setIngredients(Arrays.asList("onion", "cauliflower", "potatoes"));
        second.setDirections("Chop, fry, add boulion and blend.");
        add(second);

        Recipe third = new Recipe();
        third.setTitle("Veggie stir fry");
        third.setDescription("Marvelous wok-fried veggies with rice noodles");
        third.setNumberOfServings(2);
        third.setPreparationTimeInMinutes(40);
        third.setIngredients(Arrays.asList("rice", "pepper", "egg", "mushroom"));
        third.setDirections("Chop, fry on a wok, add soy sauce.");
        add(third);
    }

    public Recipe findRecipeById(int id) {
        return allRecipes.get(id);
    }

    public void add(Recipe recipe) {
        if (recipe.getId() == 0) {
            recipe.setId(nextId);
            nextId++;
        }
        allRecipes.put(recipe.getId(), recipe);
    }
}
