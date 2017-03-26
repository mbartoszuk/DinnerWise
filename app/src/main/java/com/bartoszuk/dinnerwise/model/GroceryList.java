package com.bartoszuk.dinnerwise.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.HashSet;
import java.util.Set;

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

    public static GroceryList forCurrentWeek(Context context) {
        return new GroceryList(new RecipeDBHelper(context));
    }

    private GroceryList(RecipeDBHelper helper) {
        this.helper = helper;
    }

    public int size() {
        return selectedDays().size();
    }

    public Set<DayOfWeek> selectedDays() {
        SQLiteDatabase db = helper.getReadableDatabase();
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

    public GroceryListRecipe recipeOn(DayOfWeek dayOfWeek) {
        SQLiteDatabase db = helper.getReadableDatabase();
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
        GroceryListRecipe recipe = new GroceryListRecipe(dayOfWeek, query.getLong(0));
        if (1 == query.getInt(1)) {
            recipe.discard();
        }
        recipe.loadCheckIngredientsFromDb(query.getString(2));
        recipe.setList(this);
        return recipe;
    }

    public void clearRecipeOn(DayOfWeek dayOfWeek) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String selection = RecipeEntry.COLUMN_NAME_DAY_OF_WEEK + " = ?";
        String[] selectionArgs = {Integer.toString(dayOfWeek.ordinal())};
        db.delete(RecipeEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void setRecipeOn(DayOfWeek dayOfWeek, long recipeId) {
        update(new GroceryListRecipe(dayOfWeek, recipeId));
    }

    void update(GroceryListRecipe listItem) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = RecipeEntry.COLUMN_NAME_DAY_OF_WEEK + " = ?";
        String[] selectionArgs = {Integer.toString(listItem.getDayOfWeek().ordinal())};
        int rows = db.update(RecipeEntry.TABLE_NAME, values(listItem), whereClause, selectionArgs);
        if (rows == 0) {
            db.insertWithOnConflict(RecipeEntry.TABLE_NAME, null, values(listItem), SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    ContentValues values(GroceryListRecipe listItem) {
        ContentValues values = new ContentValues();
        values.put(RecipeEntry.COLUMN_NAME_RECIPE_ID, listItem.getRecipeId());
        values.put(RecipeEntry.COLUMN_NAME_DAY_OF_WEEK, listItem.getDayOfWeek().ordinal());
        values.put(RecipeEntry.COLUMN_NAME_DISCARDED, listItem.isDiscarded() ? 1 : 0);
        values.put(RecipeEntry.COLUMN_NAME_CHECKED_INGREDIENTS, listItem.dbCheckedIngredients());
        return values;
    }
}
