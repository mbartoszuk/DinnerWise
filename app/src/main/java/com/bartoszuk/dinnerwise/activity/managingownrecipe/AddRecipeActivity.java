package com.bartoszuk.dinnerwise.activity.managingownrecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipeSet;
import com.bartoszuk.dinnerwise.model.RecipesDB;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class AddRecipeActivity extends AppCompatActivity {

    public static final String RECIPE_ID_TO_EDIT = "recipe_id_to_edit";

    private final RecipesDB db = RecipesDB.db();
    private final RecipeSet own = RecipeSet.own();
    private Recipe newRecipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra(RECIPE_ID_TO_EDIT, 0);
        if (id != 0) {
            newRecipe = db.findRecipeById(id);
        }

        setTitle(getString(R.string.manageRecipe_title));
        setContentView(R.layout.activity_manage_recipe);

        EditText title = (EditText) findViewById(R.id.recipe_title_input);
        title.setText(newRecipe.getTitle());
        title.addTextChangedListener(new RecipeFormWatcher(newRecipe, R.id.recipe_title_input));

        EditText description = (EditText) findViewById(R.id.recipe_description_input);
        description.setText(newRecipe.getDescription());
        description.addTextChangedListener(new RecipeFormWatcher(newRecipe, R.id.recipe_description_input));

        EditText prepTime = (EditText) findViewById(R.id.recipe_preparation_time_input);
        prepTime.setText(Integer.toString(newRecipe.getPreparationTimeInMinutes()));
        prepTime.addTextChangedListener(new RecipeFormWatcher(newRecipe, R.id.recipe_preparation_time_input));

        EditText servings = (EditText) findViewById(R.id.recipe_servings_input);
        servings.setText(Integer.toString(newRecipe.getNumberOfServings()));
        servings.addTextChangedListener(new RecipeFormWatcher(newRecipe, R.id.recipe_servings_input));

        EditText ingredients = (EditText) findViewById(R.id.recipe_ingredients_input);
        StringBuilder ingredientLines = new StringBuilder();
        for (String ingredient : newRecipe.getIngredients()) {
            ingredientLines.append(ingredient + "\n");
        }
        ingredients.setText(ingredientLines.toString());
        ingredients.addTextChangedListener(new RecipeFormWatcher(newRecipe, R.id.recipe_ingredients_input));

        EditText directions = (EditText) findViewById(R.id.recipe_directions_input);
        directions.setText(newRecipe.getDirections());
        directions.addTextChangedListener(new RecipeFormWatcher(newRecipe, R.id.recipe_directions_input));

        Button saveRecipe = (Button) findViewById(R.id.button_save_recipe);
        saveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.add(newRecipe);
                own.add(newRecipe.getId());
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    // References:
    // - https://github.com/svenwiegand/time-duration-picker#timedurationpickerdialog-and-timedurationpickerdialogfragment
    // - https://developer.android.com/guide/topics/ui/controls/pickers.html
    public void showPreparationTimePicker(View v) {
        new PreparationTimePicker().show(getFragmentManager(), "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_recipe_secondary_menu, menu);
        MenuItem delete = menu.findItem(R.id.action_delete);
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setResult(RESULT_CANCELED);
                finish();
                return true;
            }
        });
        return true;
    }

}
