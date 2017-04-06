package com.bartoszuk.dinnerwise.activity.fullrecipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.managingownrecipe.AddRecipeActivity;
import com.bartoszuk.dinnerwise.model.Ingredient;
import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipeSet;
import com.bartoszuk.dinnerwise.model.RecipesDB;

public class FullRecipeActivity extends AppCompatActivity {

    public static final String RECIPE_ID_TO_OPEN = "recipe_id_to_open";

    public static final String RECIPE_EDITING_RESULT = "recipe_editing_result";
    public static final int RECIPE_EDITED = 1;
    public static final int RECIPE_DELETED = 2;

    public static final String RECIPE_FAVOURITE_RESULT = "recipe_favourite_result";
    public static final int RECIPE_ADDED_TO_FAVOURITES = 1;
    public static final int RECIPE_REMOVED_FROM_FAVOURITES = 2;

    private static final int EDIT_REQUEST_CODE = 241;

    private Recipe recipeModel = new Recipe();
    private RecipesDB db;
    private RecipeSet own;
    private RecipeSet favouriteRecipes;

    private Intent resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = RecipesDB.db(getApplicationContext());
        own = RecipeSet.own(db);
        favouriteRecipes = RecipeSet.favourites(db);

        long id = getIntent().getLongExtra(RECIPE_ID_TO_OPEN, 0);
        if (id != 0) {
            recipeModel = db.findRecipeById(id);
        }

        setContentView(R.layout.activity_full_recipe);
    }

    // Setting up / refreshing the data in the view.
    @Override
    protected void onStart() {
        super.onStart();
        renderModel();
        resultData = new Intent();
        setResult(RESULT_CANCELED, resultData);
    }

    // Displaying the recipe data in the interface.
    private void renderModel() {
        // Refresh.
        if (recipeModel.getId() != 0) {
            recipeModel = db.findRecipeById(recipeModel.getId());
        }

        // Recipe title.
        setTitle(recipeModel.getTitle());

        // Recipe image.
        recipeModel.renderFullInto((ImageView) findViewById(R.id.full_recipe_image));

        // Recipe preparation time.
        TextView preparationTimeServings = (TextView) findViewById(R.id.preparation_time_servings_text);
        preparationTimeServings.setText(recipeModel.getPreparationTimeInMinutes() + " minutes  |  "
                + recipeModel.getNumberOfServings() + " servings");

        // Recipe ingredients - displaying as a list with dots.
        TextView ingredients = (TextView) findViewById(R.id.ingredients_text);
        StringBuilder ingredientsContent = new StringBuilder();
        for (Ingredient ingredient : recipeModel.getIngredients()) {
            ingredientsContent.append("• " + ingredient + "\n");
        }
        ingredients.setText(ingredientsContent.toString());

        // Recipe description.
        TextView description = (TextView) findViewById(R.id.description_text);
        description.setText(recipeModel.getDescription());

        // Recipe directions.
        TextView directions = (TextView) findViewById(R.id.directions_text);
        directions.setText(recipeModel.getDirections());
    }

    // Displaying the action bar according to where the full recipe is used.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (own.contains(recipeModel.getId())) {
            getMenuInflater().inflate(R.menu.own_recipe_secondary_menu, menu);
        } else {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.full_recipe_secondary_menu, menu);
            displayFavouritesIcon(menu.findItem(R.id.action_addToFavorites));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handling favourites icon selection, recipe edit or deleting.
        switch (item.getItemId()) {
            case R.id.action_addToFavorites:
                if (favouriteRecipes.contains(recipeModel.getId())) {
                    favouriteRecipes.remove(recipeModel.getId());
                    resultData.putExtra(RECIPE_FAVOURITE_RESULT, RECIPE_REMOVED_FROM_FAVOURITES);
                } else {
                    favouriteRecipes.add(recipeModel.getId());
                    resultData.putExtra(RECIPE_FAVOURITE_RESULT, RECIPE_ADDED_TO_FAVOURITES);
                }
                displayFavouritesIcon(item);
                return true;
            case R.id.action_edit:
                Intent intent = new Intent(this, AddRecipeActivity.class);
                intent.putExtra(AddRecipeActivity.RECIPE_ID_TO_EDIT, recipeModel.getId());
                startActivityForResult(intent, EDIT_REQUEST_CODE);
                return true;
            case R.id.action_delete:
                // Create the confirmation dialog to delete the own recipe.
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_own_recipe_confirmation)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Delete the chosen recipe.
                                own.remove(recipeModel.getId());
                                Intent resultData = new Intent();
                                resultData.putExtra(RECIPE_EDITING_RESULT, RECIPE_DELETED);
                                setResult(RESULT_OK, resultData);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Nothing happens.
                            }
                        });
                // Display the confirmation dialog to delete the own recipe.
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        renderModel();
        // Recipe was edited successfully.
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), R.string.recipe_edited_toast, Toast.LENGTH_SHORT).show();
            resultData.putExtra(RECIPE_EDITING_RESULT, RECIPE_EDITED);
            setResult(RESULT_OK, resultData);
        }

        // Recipe was discarded.
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), R.string.recipe_discarded_toast, Toast.LENGTH_SHORT).show();
        }
    }

    // Displays the filled / empty favourites icon based on the belonging to the list.
    public void displayFavouritesIcon(MenuItem item) {
        if (favouriteRecipes.contains(recipeModel.getId())) {
            item.setIcon(R.drawable.ic_favorite_24dp);
        } else {
            item.setIcon(R.drawable.ic_favorite_outline_24dp);
        }
    }
}
