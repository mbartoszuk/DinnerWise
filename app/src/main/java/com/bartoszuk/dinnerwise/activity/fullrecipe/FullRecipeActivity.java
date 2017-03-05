package com.bartoszuk.dinnerwise.activity.fullrecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.model.RecipeSet;
import com.bartoszuk.dinnerwise.model.Recipe;

import java.util.Arrays;

public class FullRecipeActivity extends AppCompatActivity {

    private Recipe recipeModel = new Recipe("id");
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
        setContentView(R.layout.activity_full_recipe);

        setTitle(recipeModel.getTitle());

        recipeModel.renderFullInto((ImageView) findViewById(R.id.full_recipe_image));

        TextView preparationTimeServings = (TextView) findViewById(R.id.preparation_time_servings_text);
        preparationTimeServings.setText(recipeModel.getPreparationTimeInMinutes() + " minutes  |  " + recipeModel.getNumberOfServings() + " servings");

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.full_recipe_secondary_menu, menu);
        displayFavouritesIcon(menu.findItem(R.id.action_addToFavorites));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle favourites icon selection
        switch (item.getItemId()) {
            case R.id.action_addToFavorites:
                if (favouriteRecipes.contains(recipeModel)) {
                    favouriteRecipes.remove(recipeModel);
                } else {
                    favouriteRecipes.add(recipeModel);
                }
                displayFavouritesIcon(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Displays the filled / empty favourites icon based on the belonging to the list.
    public void displayFavouritesIcon(MenuItem item) {
        if (favouriteRecipes.contains(recipeModel)) {
            item.setIcon(R.drawable.ic_favorite_24dp);
        } else {
            item.setIcon(R.drawable.ic_favorite_outline_24dp);
        }
    }
}
