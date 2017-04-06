package com.bartoszuk.dinnerwise.activity.favourites;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.LogOut;
import com.bartoszuk.dinnerwise.activity.fullrecipe.FullRecipeActivity;
import com.bartoszuk.dinnerwise.activity.groceries.GroceriesActivity;
import com.bartoszuk.dinnerwise.activity.ownrecipes.OwnRecipesActivity;
import com.bartoszuk.dinnerwise.activity.ownrecipes.RecipeListAdapter;
import com.bartoszuk.dinnerwise.activity.week.WeekActivity;
import com.bartoszuk.dinnerwise.model.RecipeSet;
import com.bartoszuk.dinnerwise.model.RecipesDB;
import com.roughike.bottombar.BottomBarTab;

public class FavouritesActivity extends LogOut {

    private RecipeSet favourites;
    private RecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecipesDB db = RecipesDB.db(getApplicationContext());
        favourites = RecipeSet.favourites(db);

        setTitle(getString(R.string.favourites_title));
        setContentView(R.layout.activity_favourites);

        // Changing the count label accordingly when the data is changed.
        TextView recipesCount = (TextView) findViewById(R.id.recipe_count_label);
        recipesCount.setText(favourites.size(null) + " recipes");

        ListView recipeList = (ListView) findViewById(R.id.favourites_list);
        adapter = new RecipeListAdapter(db, favourites, this, getLayoutInflater());
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                TextView recipesCount = (TextView) findViewById(R.id.recipe_count_label);
                recipesCount.setText(favourites.size(null) + " recipes");
            }
        });
        recipeList.setAdapter(adapter);

        // Setting the message for the empty list.
        recipeList.setEmptyView(findViewById(R.id.zero_favourites_message_text));

        // Own Recipes Tab
        BottomBarTab ownRecipesButton = (BottomBarTab) findViewById(R.id.tab_ownrecipes);
        ownRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OwnRecipesActivity.class);
                startActivity(intent);
            }
        });

        // Groceries Tab
        BottomBarTab groceriesButton = (BottomBarTab) findViewById(R.id.tab_groceries);
        groceriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GroceriesActivity.class);
                startActivity(intent);
            }
        });
    }

    // Find the Week Activity on the stack and goes back to it (to the beginning of the stack).
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WeekActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // Managing the actions done in context to Favourites activity - ex. removing a recipe.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RecipeListAdapter.SHOW_RECIPE_REQUEST) {
            int addToFavouritesResult = data.getIntExtra(FullRecipeActivity.RECIPE_FAVOURITE_RESULT, 0);
            if (addToFavouritesResult == FullRecipeActivity.RECIPE_REMOVED_FROM_FAVOURITES) {
                adapter.notifyDataSetChanged();
                //Display the toast accordingly.
                Toast.makeText(getApplicationContext(), R.string.recipe_unfavourited_toast, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favourites_secondary_menu, menu);

        // Search.
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        // Display a message if no recipes are found.
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                TextView recipesCount = (TextView) findViewById(R.id.recipe_count_label);
                recipesCount.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
                TextView noRecepiesFound = (TextView) findViewById(R.id.zero_favourites_message_text);
                noRecepiesFound.setText(hasFocus ? R.string.no_search_results_found : R.string.zero_favourites_message_text);
            }
        });

        return true;
    }
}
