package com.bartoszuk.dinnerwise.model;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class RecipesDB {

    public Recipe findRecipeById(String id) {
        return new Recipe(id, "Aubergine & Couscous Salad");
    }

}
