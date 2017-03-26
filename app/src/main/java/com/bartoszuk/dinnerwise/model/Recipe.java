package com.bartoszuk.dinnerwise.model;

import android.provider.BaseColumns;
import android.widget.ImageView;

import com.bartoszuk.dinnerwise.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class Recipe {

    // db is set to null if the recipe is not saved to database yet. Has the id of 0.
    // db is set to non-null value if the recipe has been saved to the database.
    RecipesDB db;

    private long id = 0;
    private String title = "";
    private String description = "";
    private int preparationTimeInMinutes = 0;
    private int numberOfServings = 0;
    private List<Ingredient> ingredients = Collections.emptyList();
    private String directions = "";

    public static class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_PREPARATION_TIME_MINS = "preparation_time_mins";
        public static final String COLUMN_NAME_SERVINGS = "servings";
        public static final String COLUMN_NAME_INGREDIENTS = "ingredients";
        public static final String COLUMN_NAME_DIRECTIONS = "directions";
        public static final String COLUMN_NAME_FAVOURITES = "favourites";
        public static final String COLUMN_NAME_OWN = "own";
    }

    public Recipe() {}

    Recipe(RecipesDB db) {
        this.db = db;
    }

    public long getId() {
        return this.id;
    }

    // Thumbnail image for the Week view.
    public void renderThumbnailInto(ImageView view) {
        view.setImageResource(R.mipmap.img_placeholder);
    }

    // Full image for the full recipe view.
    public void renderFullInto(ImageView view) {
        view.setImageResource(R.drawable.full_recipe_image_placeholder);
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public int getNumberOfServings() {
        return numberOfServings;
    }

    public int getPreparationTimeInMinutes() {
        return preparationTimeInMinutes;
    }

    void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
        if (db != null) {
            db.update(this);
        }
    }

    public void setDescription(String description) {
        this.description = description;
        if (db != null) {
            db.update(this);
        }
    }

    public void setDirections(String directions) {
        this.directions = directions;
        if (db != null) {
            db.update(this);
        }
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        if (db != null) {
            db.update(this);
        }
    }

    public void setNumberOfServings(int numberOfServings) {
        this.numberOfServings = numberOfServings;
        if (db != null) {
            db.update(this);
        }
    }

    public void setPreparationTimeInMinutes(int preparationTimeInMinutes) {
        this.preparationTimeInMinutes = preparationTimeInMinutes;
        if (db != null) {
            db.update(this);
        }
    }

    // Compares two recipes by their ids.
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Recipe)) {
            return false;
        }
        Recipe that = (Recipe) other;
        return this.getId() == that.getId();
    }

    // Addition to equals().
    @Override
    public int hashCode() {
        return (int) (getId() % 12341253L);
    }
}
