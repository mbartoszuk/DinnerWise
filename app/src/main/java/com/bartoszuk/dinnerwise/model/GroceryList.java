package com.bartoszuk.dinnerwise.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Maria Bartoszuk on 18/03/2017.
 */

public class GroceryList {

    public static class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "grocery_list";
        public static final String COLUMN_NAME_RECIPE_ID = "recipe_id";
        public static final String COLUMN_NAME_DAY_OF_WEEK = "day_of_week";
        public static final String COLUMN_NAME_DISCARDED = "discarded";
        public static final String COLUMN_NAME_CHECKED_INGREDIENTS = "checked_ingredients";
    }

    private final RecipeDBHelper helper;

    private static final ConcurrentHashMap<DayOfWeek, Optional<GroceryListRecipe>> cache =
            new ConcurrentHashMap<>();

    public static GroceryList forCurrentWeek(Context context) {
        return new GroceryList(new RecipeDBHelper(context));
    }

    private GroceryList(RecipeDBHelper helper) {
        this.helper = helper;
    }

    public int size() {
        return selectedDays().size();
    }

    // Getting all the days for which a recipe was chosen.
    public Set<DayOfWeek> selectedDays() {
        if (cache.size() == DayOfWeek.values().length) {
            HashSet<DayOfWeek> selectedDays = new HashSet<>();
            for (Map.Entry<DayOfWeek, Optional<GroceryListRecipe>> entry : cache.entrySet()) {
                if (entry.getValue().isPresent()) {
                    selectedDays.add(entry.getKey());
                }
            }
            return selectedDays;
        }
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            String[] columns = {RecipeEntry.COLUMN_NAME_DAY_OF_WEEK};
            Cursor query = db.query(RecipeEntry.TABLE_NAME, columns, null, null, null, null, null);
            HashSet<DayOfWeek> days = new HashSet<>();
            if (query.moveToFirst()) {
                do {
                    days.add(DayOfWeek.values()[query.getInt(0)]);
                } while (query.moveToNext());
            }
            return days;
        }
    }

    // Returns the recipe that was chosen for the specific day of the week.
    // Also helps in sorting the grocery lists chronologically by day of week.
    public GroceryListRecipe recipeOn(DayOfWeek dayOfWeek) {
        if (cache.containsKey(dayOfWeek)) {
            return cache.get(dayOfWeek).get();
        }
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            String[] columns = {
                    RecipeEntry.COLUMN_NAME_RECIPE_ID,
                    RecipeEntry.COLUMN_NAME_DISCARDED,
                    RecipeEntry.COLUMN_NAME_CHECKED_INGREDIENTS
            };
            String selection = RecipeEntry.COLUMN_NAME_DAY_OF_WEEK + " = ?";
            String[] selectionArgs = {Integer.toString(dayOfWeek.ordinal())};
            Cursor query = db.query(RecipeEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (query == null) {
                return null;
            }
            if (!query.moveToFirst()) {
                return null;
            }
            // 0 refers to the recipe being normal
            // 1 refers to the recipe being crossed off
            GroceryListRecipe recipe = new GroceryListRecipe(dayOfWeek, query.getLong(0));
            if (1 == query.getInt(1)) {
                recipe.discard();
            }
            recipe.loadCheckIngredientsFromDb(query.getString(2));
            recipe.setList(this);
            cache.put(dayOfWeek, Optional.of(recipe));
            return recipe;
        }
    }

    // Clearing the recipe from the grocery list when it's unchecked in the Week Activity.
    public void clearRecipeOn(DayOfWeek dayOfWeek) {
        cache.put(dayOfWeek, Optional.<GroceryListRecipe>empty());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            String selection = RecipeEntry.COLUMN_NAME_DAY_OF_WEEK + " = ?";
            String[] selectionArgs = {Integer.toString(dayOfWeek.ordinal())};
            db.delete(RecipeEntry.TABLE_NAME, selection, selectionArgs);
        }
    }

    // Setting a recipe to the day it was checked on.
    public void setRecipeOn(DayOfWeek dayOfWeek, long recipeId) {
        cache.remove(dayOfWeek);
        update(new GroceryListRecipe(dayOfWeek, recipeId));
    }

    // Updating the Grocery List Recipe database (second database).
    void update(GroceryListRecipe listItem) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            String whereClause = RecipeEntry.COLUMN_NAME_DAY_OF_WEEK + " = ?";
            String[] selectionArgs = {Integer.toString(listItem.getDayOfWeek().ordinal())};
            int rows = db.update(RecipeEntry.TABLE_NAME, values(listItem), whereClause, selectionArgs);
            if (rows == 0) {
                db.insertWithOnConflict(RecipeEntry.TABLE_NAME, null, values(listItem), SQLiteDatabase.CONFLICT_REPLACE);
            }
        }
    }

    // While saving the Grocery List Recipe db, helps to translate the recipe to values in the db.
    ContentValues values(GroceryListRecipe listItem) {
        ContentValues values = new ContentValues();
        values.put(RecipeEntry.COLUMN_NAME_RECIPE_ID, listItem.getRecipeId());
        values.put(RecipeEntry.COLUMN_NAME_DAY_OF_WEEK, listItem.getDayOfWeek().ordinal());
        values.put(RecipeEntry.COLUMN_NAME_DISCARDED, listItem.isDiscarded() ? 1 : 0);
        values.put(RecipeEntry.COLUMN_NAME_CHECKED_INGREDIENTS, listItem.dbCheckedIngredients());
        return values;
    }
}
