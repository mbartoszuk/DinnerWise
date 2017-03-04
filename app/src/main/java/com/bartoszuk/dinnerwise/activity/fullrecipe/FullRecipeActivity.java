package com.bartoszuk.dinnerwise.activity.fullrecipe;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.model.FavouriteRecipes;
import com.bartoszuk.dinnerwise.model.Recipe;

public class FullRecipeActivity extends AppCompatActivity {

    private Recipe recipeModel = new Recipe("id", "title");
    private FavouriteRecipes favouriteRecipes = new FavouriteRecipes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.fullRecipe_title));
        setContentView(R.layout.activity_full_recipe);
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
