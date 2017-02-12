package com.bartoszuk.dinnerwise.model;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class RecipeChoiceDB {

    public RecipeChoice findRecipeChoiceByDate (Date date) {
        return new RecipeChoice(String.format("%s-1", date), String.format("%s-2", date));
    }
}
