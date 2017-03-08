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
import com.bartoszuk.dinnerwise.model.RecipeSet;
import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipesDB;

import java.util.Arrays;

public class FullRecipeActivity extends AppCompatActivity {

    public static final String RECIPE_ID_TO_OPEN = "recipe_id_to_open";

    public static final String RECIPE_EDITING_RESULT = "recipe_editing_result";
    public static final int RECIPE_EDITED = 1;
    public static final int RECIPE_DELETED = 2;

    private static final int EDIT_REQUEST_CODE = 241;

    private Recipe recipeModel = new Recipe(4);
    private RecipesDB db = RecipesDB.db();
    private RecipeSet own = RecipeSet.own();
    private RecipeSet favouriteRecipes = RecipeSet.favourites();

    public FullRecipeActivity() {
        recipeModel.setTitle("title");
        recipeModel.setPreparationTimeInMinutes(30);
        recipeModel.setNumberOfServings(2);
        recipeModel.setDescription("description");
        recipeModel.setIngredients(Arrays.asList("1 cup of flour", "3 eggs"));
        recipeModel.setDirections("first do this, then do that");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra(RECIPE_ID_TO_OPEN, 0);
        if (id != 0) {
            recipeModel = db.findRecipeById(id);
        }

        setContentView(R.layout.activity_full_recipe);
    }

    @Override
    protected void onStart() {
        super.onStart();
        renderModel();
    }

    private void renderModel() {
        setTitle(recipeModel.getTitle());

        recipeModel.renderFullInto((ImageView) findViewById(R.id.full_recipe_image));

        TextView preparationTimeServings = (TextView) findViewById(R.id.preparation_time_servings_text);
        preparationTimeServings.setText(recipeModel.getPreparationTimeInMinutes() + " minutes  |  "
                + recipeModel.getNumberOfServings() + " servings");

        TextView ingredients = (TextView) findViewById(R.id.ingredients_text);
        StringBuilder ingredientsContent = new StringBuilder();
        for (String ingredient : recipeModel.getIngredients()) {
            ingredientsContent.append("• " + ingredient + "\n");
        }
        ingredients.setText(ingredientsContent.toString());

        TextView description = (TextView) findViewById(R.id.description_text);
        description.setText(recipeModel.getDescription());

        TextView directions = (TextView) findViewById(R.id.directions_text);
        directions.setText(recipeModel.getDirections());
    }

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
        // Handle favourites icon selection
        switch (item.getItemId()) {
            case R.id.action_addToFavorites:
                if (favouriteRecipes.contains(recipeModel.getId())) {
                    favouriteRecipes.remove(recipeModel.getId());
                } else {
                    favouriteRecipes.add(recipeModel.getId());
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
                                RecipeSet.own().remove(recipeModel.getId());
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
        // Was edited successfully.
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            renderModel();
            Toast.makeText(getApplicationContext(), R.string.recipe_edited_toast, Toast.LENGTH_SHORT).show();
            Intent resultData = new Intent();
            resultData.putExtra(RECIPE_EDITING_RESULT, RECIPE_EDITED);
            setResult(RESULT_OK, resultData);
        }

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
