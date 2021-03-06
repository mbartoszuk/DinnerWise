package com.bartoszuk.dinnerwise.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 *
 * Tutorial source: https://developer.android.com/training/basics/data-storage/databases.html
 */

public class RecipesDB {

    private static final ConcurrentHashMap<Long, Recipe> cache = new ConcurrentHashMap<>();

    final RecipeDBHelper helper;

    public static RecipesDB db(Context context) {
        return new RecipesDB(new RecipeDBHelper(context));
    }

    private RecipesDB(RecipeDBHelper helper) {
        this.helper = helper;
    }

    public Recipe findRecipeById(long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        try (SQLiteDatabase db = helper.getReadableDatabase()) {

            // SQL Query: SELECT * FROM Recipes WHERE Recipes.id = id (from parameter).
            String[] columns = {
                    Recipe.RecipeEntry.COLUMN_NAME_TITLE,  // 0
                    Recipe.RecipeEntry.COLUMN_NAME_DESCRIPTION,  // 1
                    Recipe.RecipeEntry.COLUMN_NAME_PREPARATION_TIME_MINS,  // 2
                    Recipe.RecipeEntry.COLUMN_NAME_SERVINGS,  // 3
                    Recipe.RecipeEntry.COLUMN_NAME_INGREDIENTS,  // 4
                    Recipe.RecipeEntry.COLUMN_NAME_DIRECTIONS,  // 5
                    Recipe.RecipeEntry.COLUMN_NAME_IMAGE_RES_ID  // 6
            };
            String selection = Recipe.RecipeEntry._ID + " = ?";
            String[] selectionArgs = {Long.toString(id)};
            Cursor query = db.query(Recipe.RecipeEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            // END of SQL Query.

            if (query == null) {
                throw new RuntimeException("Failed to find recipe id = " + id);
            }
            query.moveToFirst();
            Recipe recipe = new Recipe(null);

            // Setting the values in the Object based on the query result.
            recipe.setId(id);
            recipe.setTitle(query.getString(0));
            recipe.setDescription(query.getString(1));
            recipe.setPreparationTimeInMinutes(query.getInt(2));
            recipe.setNumberOfServings(query.getInt(3));
            List<Ingredient> ingredients = new ArrayList<>();
            for (String encoded : query.getString(4).split("\n")) {
                if (!encoded.trim().equals("")) {
                    ingredients.add(Ingredient.fromDb(encoded));
                }
            }
            recipe.setIngredients(ingredients);
            recipe.setDirections(query.getString(5));
            recipe.setImageResId(query.getInt(6));
            recipe.db = this;
            cache.put(id, recipe);
            return recipe;
        }
    }

    // Adds new Recipe to the DB that was not there before.
    public void add(Recipe recipe) {
        if (recipe.getId() != 0) {
            return;
        }
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues values = valuesOf(recipe);
            recipe.setId(db.insert(Recipe.RecipeEntry.TABLE_NAME, null, values));
        }
    }

    // Update the already existing Recipe in the DB. Called from every Recipe setter after it has been added to the DB first time.
    void update(Recipe recipe) {
        if (recipe.getId() == 0) {
            return;
        }
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            ContentValues values = valuesOf(recipe);
            String selection = Recipe.RecipeEntry._ID + " = ?";
            String[] selectionArgs = {Long.toString(recipe.getId())};
            db.update(Recipe.RecipeEntry.TABLE_NAME, values, selection, selectionArgs);
        }
    }

    // Saving the recipe data from Recipe to ContentValues (Android SQL DB Row).
    @NonNull
    static ContentValues valuesOf(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(Recipe.RecipeEntry.COLUMN_NAME_TITLE, recipe.getTitle());
        values.put(Recipe.RecipeEntry.COLUMN_NAME_DESCRIPTION, recipe.getDescription());
        values.put(Recipe.RecipeEntry.COLUMN_NAME_PREPARATION_TIME_MINS, recipe.getPreparationTimeInMinutes());
        values.put(Recipe.RecipeEntry.COLUMN_NAME_SERVINGS, recipe.getNumberOfServings());
        values.put(Recipe.RecipeEntry.COLUMN_NAME_IMAGE_RES_ID, recipe.getImageResId());
        StringBuilder ingredients = new StringBuilder();
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredients.append(ingredient.dbFormat() + "\n");
        }
        values.put(Recipe.RecipeEntry.COLUMN_NAME_INGREDIENTS, ingredients.toString());
        values.put(Recipe.RecipeEntry.COLUMN_NAME_DIRECTIONS, recipe.getDirections());
        return values;
    }
}
