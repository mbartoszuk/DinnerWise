package com.bartoszuk.dinnerwise.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;

/**
 * Created by Maria Bartoszuk on 10/03/2017.
 */

public class RecipeDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Recipes.db";

    public RecipeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating the DB with all it's columns and their names.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Recipe.RecipeEntry.TABLE_NAME + " ("
                + Recipe.RecipeEntry._ID + " INTEGER PRIMARY KEY, "
                + Recipe.RecipeEntry.COLUMN_NAME_TITLE + " TEXT, "
                + Recipe.RecipeEntry.COLUMN_NAME_DESCRIPTION + " TEXT, "
                + Recipe.RecipeEntry.COLUMN_NAME_PREPARATION_TIME_MINS + " INTEGER, "
                + Recipe.RecipeEntry.COLUMN_NAME_SERVINGS + " INTEGER, "
                + Recipe.RecipeEntry.COLUMN_NAME_INGREDIENTS + " TEXT, "
                + Recipe.RecipeEntry.COLUMN_NAME_DIRECTIONS + " TEXT,"
                + Recipe.RecipeEntry.COLUMN_NAME_FAVOURITES + " INTEGER, "
                + Recipe.RecipeEntry.COLUMN_NAME_OWN + " INTEGER);");
        insert(db, "Aubergine & Couscous", "an awesome salad", 30, 1, "mix",
                Ingredient.vegetable("aubergine", Quantity.pieces(1)),
                Ingredient.grain("cous cous", Quantity.pieces(2)));
        insert(db, "Cauliflower soup", "Great tasting, natural and so quick.", 20, 2,
                "Chop, fry, add boulion and blend.",
                Ingredient.vegetable("onion", Quantity.pieces(1)),
                Ingredient.vegetable("cauliflower", Quantity.pieces(1)));
        db.execSQL("CREATE TABLE " + GroceryList.RecipeEntry.TABLE_NAME + " ("
                + GroceryList.RecipeEntry._ID + " INTEGER PRIMARY KEY, "
                + GroceryList.RecipeEntry.COLUMN_NAME_RECIPE_ID + " INTEGER, "
                + GroceryList.RecipeEntry.COLUMN_NAME_DAY_OF_WEEK + " INTEGER, "
                + GroceryList.RecipeEntry.COLUMN_NAME_DISCARDED + " INTEGER, "
                + GroceryList.RecipeEntry.COLUMN_NAME_CHECKED_INGREDIENTS + " TEXT);");
    }

    // Delete the previous table and add an updated one.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over.
        db.execSQL("DROP TABLE IF EXISTS " + Recipe.RecipeEntry.TABLE_NAME + ";");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void insert(SQLiteDatabase db, String title, String description, int preparationTimeMin,
                        int servings, String directions, Ingredient... ingredients) {
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPreparationTimeInMinutes(preparationTimeMin);
        recipe.setNumberOfServings(servings);
        recipe.setIngredients(Arrays.asList(ingredients));
        recipe.setDirections(directions);
        ContentValues values = RecipesDB.valuesOf(recipe);
        db.insert(Recipe.RecipeEntry.TABLE_NAME, null, values);
    }
}
