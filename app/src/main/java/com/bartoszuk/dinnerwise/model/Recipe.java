package com.bartoszuk.dinnerwise.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private int imageResId = R.drawable.full_recipe_image_placeholder;

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
        public static final String COLUMN_NAME_IMAGE_RES_ID = "image_res_id";
    }

    public Recipe() {}

    Recipe(RecipesDB db) {
        this.db = db;
    }

    public long getId() {
        return this.id;
    }

    // Thumbnail image for the Week view.
    //
    // Technique from "Loading large bitmaps efficiently" - Android Developers.
    // https://developer.android.com/topic/performance/graphics/load-bitmap.html
    public void renderThumbnailInto(ImageView view) {
        view.setImageBitmap(decodeSampledBitmapFromResource(view.getResources(), imageResId, 400, 400));
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // Full image for the full recipe view.
    public void renderFullInto(ImageView view) {
        view.setImageResource(getImageResId());
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

    public boolean hasIngredientsIn(String category) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getCategory().equals(category)) {
                return true;
            }
        }
        return false;
    }

    int getImageResId() {
        return imageResId;
    }

    void setId(long id) {
        this.id = id;
    }

    void setImageResId(int imageResId) {
        this.imageResId = imageResId;
        if (db != null) {
            db.update(this);
        }
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
