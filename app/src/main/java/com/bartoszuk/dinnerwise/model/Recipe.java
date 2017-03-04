package com.bartoszuk.dinnerwise.model;

import android.widget.ImageView;

import com.bartoszuk.dinnerwise.R;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class Recipe {

    private final String id;
    private final String title;
    private final int preparationTimeInMinutes;
    private final int numberOfServings;
    private final String description;
    private final String directions;

    public Recipe(String id, String title, int preparationTimeInMinutes, int numberOfServings, String description, String directions) {
        this.id = id;
        this.title = title;
        this.preparationTimeInMinutes = preparationTimeInMinutes;
        this.numberOfServings = numberOfServings;
        this.description = description;
        this.directions = directions;
    }

    public String getId() {
        return this.id;
    }

    public void renderThumbnailInto(ImageView view) {
        view.setImageResource(R.mipmap.img_placeholder);
    }

    public void renderFullInto(ImageView view) {
        view.setImageResource(R.drawable.full_recipe_image_placeholder);
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return description;
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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Recipe)) {
            return false;
        }
        Recipe that = (Recipe) other;
        return this.getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
