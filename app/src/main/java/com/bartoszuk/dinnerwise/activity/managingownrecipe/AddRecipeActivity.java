package com.bartoszuk.dinnerwise.activity.managingownrecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.model.Ingredient;
import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipeSet;
import com.bartoszuk.dinnerwise.model.RecipesDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class AddRecipeActivity extends AppCompatActivity {

    public static final String RECIPE_ID_TO_EDIT = "recipe_id_to_edit";

    private RecipesDB db;
    private RecipeSet own;
    private Recipe newRecipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = RecipesDB.db(getApplicationContext());
        own = RecipeSet.own(db);

        setContentView(R.layout.activity_manage_recipe);

        long id = getIntent().getLongExtra(RECIPE_ID_TO_EDIT, 0);
        if (id != 0) {
            newRecipe = db.findRecipeById(id);
        }

        Button saveRecipe = (Button) findViewById(R.id.button_save_recipe);
        saveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateModel();
                db.add(newRecipe);
                own.add(newRecipe.getId());
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    // Model rendering (in onStart to reload after gone back to this activity).
    @Override
    protected void onStart() {
        super.onStart();

        // ID = 0 means we are adding a new recipe. Otherwise we are editing existing one.
        if (newRecipe.getId() == 0) {
            setTitle(getString(R.string.manageRecipe_title));
        } else {
            setTitle(getString(R.string.editRecipe_title));
            newRecipe = db.findRecipeById(newRecipe.getId());
        }

        // Recipe title input.
        EditText title = (EditText) findViewById(R.id.recipe_title_input);
        title.setText(newRecipe.getTitle());

        // Recipe description input.
        EditText description = (EditText) findViewById(R.id.recipe_description_input);
        description.setText(newRecipe.getDescription());

        // Recipe preparation time input.
        EditText prepTime = (EditText) findViewById(R.id.recipe_preparation_time_input);
        int preparationTimeInMinutes = newRecipe.getPreparationTimeInMinutes();
        if (preparationTimeInMinutes > 0) {
            prepTime.setText(Integer.toString(preparationTimeInMinutes));
        }

        // Recipe servings input.
        EditText servings = (EditText) findViewById(R.id.recipe_servings_input);
        int numberOfServings = newRecipe.getNumberOfServings();
        if (numberOfServings > 0) {
            servings.setText(Integer.toString(numberOfServings));
        }

        // Recipe ingredients input.
        EditText ingredients = (EditText) findViewById(R.id.recipe_ingredients_input);
        // Each ingredient is on a different line.
        StringBuilder ingredientLines = new StringBuilder();
        for (Ingredient ingredient : newRecipe.getIngredients()) {
            ingredientLines.append(ingredient + "\n");
        }
        ingredients.setText(ingredientLines.toString());

        // Recipe directions input.
        EditText directions = (EditText) findViewById(R.id.recipe_directions_input);
        directions.setText(newRecipe.getDirections());
    }

    // Fills in all the model fields based on the form in the user interface.
    private void updateModel() {

        EditText title = (EditText) findViewById(R.id.recipe_title_input);
        newRecipe.setTitle(title.getText().toString());

        EditText description = (EditText) findViewById(R.id.recipe_description_input);
        newRecipe.setDescription(description.getText().toString());

        EditText prepTime = (EditText) findViewById(R.id.recipe_preparation_time_input);
        try {
            newRecipe.setPreparationTimeInMinutes(Integer.parseInt(prepTime.getText().toString()));
        } catch (NumberFormatException e) {}

        EditText servings = (EditText) findViewById(R.id.recipe_servings_input);
        try {
            newRecipe.setNumberOfServings(Integer.parseInt(servings.getText().toString()));
        } catch (NumberFormatException e) {}

        EditText ingredients = (EditText) findViewById(R.id.recipe_ingredients_input);
        List<Ingredient> ingredientList = new ArrayList<>();
        for (String ingredientText : ingredients.getText().toString().split("\n")) {
            ingredientList.add(Ingredient.userInput(ingredientText));
        }
        newRecipe.setIngredients(ingredientList);

        EditText directions = (EditText) findViewById(R.id.recipe_directions_input);
        newRecipe.setDirections(directions.getText().toString());
    }

    // References:
    // - https://github.com/svenwiegand/time-duration-picker#timedurationpickerdialog-and-timedurationpickerdialogfragment
    // - https://developer.android.com/guide/topics/ui/controls/pickers.html
    public void showPreparationTimePicker(View v) {
        new PreparationTimePicker().show(getFragmentManager(), "dialog");
    }
}
