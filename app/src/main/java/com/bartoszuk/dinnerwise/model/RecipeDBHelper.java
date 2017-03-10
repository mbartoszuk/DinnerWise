package com.bartoszuk.dinnerwise.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maria Bartoszuk on 10/03/2017.
 */

public class RecipeDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
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
                + Recipe.RecipeEntry.COLUMN_NAME_DIRECTIONS + " TEXT);");
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
}
