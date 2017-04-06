package com.bartoszuk.dinnerwise.activity.week;

import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CompoundButton;

import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipeChoice;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 *
 * This class is a listener tha manages the checkboxes. It helps to keep one checkbox checked at a time.
 */
final class IfChecked implements CompoundButton.OnCheckedChangeListener {

    private final AppCompatCheckBox otherCheckbox;
    private final RecipeChoice choice;
    private final Recipe recipe;

    IfChecked(AppCompatCheckBox otherCheckbox, RecipeChoice choice, Recipe recipe) {
        this.otherCheckbox = otherCheckbox;
        this.choice = choice;
        this.recipe = recipe;
    }

    // Unchecking the box opposite to the one clicked.
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            otherCheckbox.setChecked(false);
            choice.setChosenRecipeId(recipe.getId());
        } else if (choice.getChosenRecipeId() == recipe.getId()) {
            choice.setChosenRecipeId(RecipeChoice.NO_RECIPE_CHOSEN);
        }
    }
}
