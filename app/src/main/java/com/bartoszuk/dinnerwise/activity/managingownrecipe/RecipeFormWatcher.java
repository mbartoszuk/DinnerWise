package com.bartoszuk.dinnerwise.activity.managingownrecipe;

import android.text.Editable;
import android.text.TextWatcher;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.model.Recipe;

import java.util.Arrays;

/**
 * Created by Maria Bartoszuk on 05/03/2017.
 */
class RecipeFormWatcher implements TextWatcher {

    private final Recipe recipe;
    private final int fieldId;

    public RecipeFormWatcher(Recipe recipe, int fieldId) {
        this.recipe = recipe;
        this.fieldId = fieldId;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (fieldId) {
            case R.id.recipe_title_input:
                recipe.setTitle(s.toString());
                break;
            case R.id.recipe_description_input:
                recipe.setDescription(s.toString());
                break;
            case R.id.recipe_preparation_time_input:
                recipe.setPreparationTimeInMinutes(Integer.parseInt(s.toString()));
                break;
            case R.id.recipe_servings_input:
                recipe.setNumberOfServings(Integer.parseInt(s.toString()));
                break;
            case R.id.recipe_ingredients_input:
                String[] ingredients = s.toString().split("\n");
                recipe.setIngredients(Arrays.asList(ingredients));
                break;
            case R.id.recipe_directions_input:
                recipe.setDirections(s.toString());
                break;
        }
    }
}
