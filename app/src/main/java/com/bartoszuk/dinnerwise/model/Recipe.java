package com.bartoszuk.dinnerwise.model;

import android.widget.ImageView;

import com.bartoszuk.dinnerwise.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class Recipe {

    private String id;
    private String title = "";
    private int preparationTimeInMinutes = 0;
    private int numberOfServings = 0;
    private String description = "";
    private List<String> ingredients = Collections.emptyList();
    private String directions = "";

    public Recipe() {}

    public Recipe(String id) {
        this.id = id;
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

    public List<String> getIngredients() {
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

    void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setNumberOfServings(int numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public void setPreparationTimeInMinutes(int preparationTimeInMinutes) {
        this.preparationTimeInMinutes = preparationTimeInMinutes;
    }

    public void setTitle(String title) {
        this.title = title;
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
