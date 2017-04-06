package com.bartoszuk.dinnerwise.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class RecipeSet {

    private final RecipesDB db;
    private final String columnName;

    // Saving all the favourites.
    public static RecipeSet favourites(RecipesDB db) {
        return new RecipeSet(db, Recipe.RecipeEntry.COLUMN_NAME_FAVOURITES);
    }

    // Saving all the own recipes.
    public static RecipeSet own(RecipesDB db) {
        return new RecipeSet(db, Recipe.RecipeEntry.COLUMN_NAME_OWN);
    }

    private RecipeSet(RecipesDB db, String columnName) {
        this.db = db;
        this.columnName = columnName;
    }

    // Asks if RecipeSet contains the recipe (specified by ID).
    public boolean contains(long recipeID) {
        Cursor query = db.helper.getReadableDatabase().rawQuery("SELECT 1 "
                + "FROM " + Recipe.RecipeEntry.TABLE_NAME + " "
                + "WHERE " + Recipe.RecipeEntry._ID + " = ? AND " + columnName + " = 1",
                new String[]{Long.toString(recipeID)});
        try {
            return query.moveToFirst();
        } finally {
            query.close();
        }
    }

    // Removes the recipe (specified by ID) from Recipe Set's column (favourites or own recipes).
    public void remove(long recipeID) {
        ContentValues values = new ContentValues();
        values.put(columnName, 0);
        String selection = Recipe.RecipeEntry._ID + " = ?";
        String[] args = new String[]{ Long.toString(recipeID) };
        db.helper.getReadableDatabase().update(Recipe.RecipeEntry.TABLE_NAME, values, selection, args);
    }

    // Adds the recipe (specified by ID) to Recipe Set's column (favourites or own recipes).
    public void add(long recipeID) {
        ContentValues values = new ContentValues();
        values.put(columnName, 1);
        String selection = Recipe.RecipeEntry._ID + " = ?";
        String[] args = new String[]{ Long.toString(recipeID) };
        db.helper.getReadableDatabase().update(Recipe.RecipeEntry.TABLE_NAME, values, selection, args);
    }

    // Counts the number of recipes in the list - favourites, own recipes or their searches.
    public int size(String title) {
        String titleQuery = " ";
        String[] args = new String[]{};
        if (title != null) {
            titleQuery = " AND " + Recipe.RecipeEntry.COLUMN_NAME_TITLE + " LIKE ? ";
            args = new String[]{"%" + title + "%"};
        }
        Cursor query = db.helper.getReadableDatabase().rawQuery("SELECT COUNT(*) "
                        + "FROM " + Recipe.RecipeEntry.TABLE_NAME + " "
                        + "WHERE " + columnName + " = 1"  + titleQuery,
                args);
        try {
            query.moveToFirst();
            return query.getInt(0);
        } finally {
            query.close();
        }
    }

    // Specifies the ID of a selected element, ex. in a list.
    public long nth(String title, int n) {
        String titleQuery = " ";
        String[] args = new String[]{Integer.toString(n)};
        if (title != null) {
            titleQuery = " AND " + Recipe.RecipeEntry.COLUMN_NAME_TITLE + " LIKE ?";
            args = new String[]{"%" + title + "%", Integer.toString(n)};
        }
        Cursor query = db.helper.getReadableDatabase().rawQuery("SELECT " + Recipe.RecipeEntry._ID + " "
                        + "FROM " + Recipe.RecipeEntry.TABLE_NAME + " "
                        + "WHERE " + columnName + " = 1" + titleQuery
                        + "ORDER BY " + Recipe.RecipeEntry._ID + " ASC "
                        + "LIMIT 1 OFFSET ?",
                args);
        try {
            query.moveToFirst();
            return query.getLong(0);
        } finally {
            query.close();
        }
    }
}
