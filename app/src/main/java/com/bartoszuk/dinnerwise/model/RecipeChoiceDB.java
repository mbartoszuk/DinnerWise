package com.bartoszuk.dinnerwise.model;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 *
 * Remembers the recipe chosen for the day.
 * Saves data in the GroceryListRecipe.
 */

public class RecipeChoiceDB {

    private final Context context;

    private static final Map<Date, RecipeChoice> choiceByDate = new HashMap<>();

    public RecipeChoiceDB(Context context) {
        this.context = context;
    }

    public RecipeChoice findRecipeChoiceByDate (Date date) {
        if (!choiceByDate.containsKey(date)) {
            choiceByDate.put(date, new RecipeChoice(context, date, date.getDayOfWeek().ordinal() * 2 + 1, date.getDayOfWeek().ordinal() * 2 + 2));
        }
        return choiceByDate.get(date);
    }
}
