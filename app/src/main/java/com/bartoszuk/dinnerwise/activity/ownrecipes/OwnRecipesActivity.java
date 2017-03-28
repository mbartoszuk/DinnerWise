package com.bartoszuk.dinnerwise.activity.ownrecipes;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.LogOut;
import com.bartoszuk.dinnerwise.activity.favourites.FavouritesActivity;
import com.bartoszuk.dinnerwise.activity.fullrecipe.FullRecipeActivity;
import com.bartoszuk.dinnerwise.activity.groceries.GroceriesActivity;
import com.bartoszuk.dinnerwise.activity.managingownrecipe.AddRecipeActivity;
import com.bartoszuk.dinnerwise.activity.week.WeekActivity;
import com.bartoszuk.dinnerwise.model.RecipeSet;
import com.bartoszuk.dinnerwise.model.RecipesDB;
import com.roughike.bottombar.BottomBarTab;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class OwnRecipesActivity extends LogOut {

    private static final int ADD_RECIPE_REQUEST = 501;

    private RecipeSet own;
    private RecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecipesDB db = RecipesDB.db(getApplicationContext());
        own = RecipeSet.own(db);

        setTitle(getString(R.string.ownRecipes_title));
        setContentView(R.layout.activity_own_recipes);

        // Change the count label when the data is changed.
        TextView recipesCount = (TextView) findViewById(R.id.recipe_count_label);
        recipesCount.setText(own.size(null) + " recipes");

        ListView recipeList = (ListView) findViewById(R.id.own_recipes_list);
        adapter = new RecipeListAdapter(db, own, this, getLayoutInflater());
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                TextView recipesCount = (TextView) findViewById(R.id.recipe_count_label);
                recipesCount.setText(own.size(null) + " recipes");
            }
        });
        recipeList.setAdapter(adapter);

        // Setting the message for the empty list.
        recipeList.setEmptyView(findViewById(R.id.zero_own_recipes_message_text));

        FloatingActionButton addRecipeButton = (FloatingActionButton) findViewById(R.id.add_recipe_button);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddRecipeActivity.class);
                startActivityForResult(intent, ADD_RECIPE_REQUEST);
            }
        });

        BottomBarTab favouritesButton = (BottomBarTab) findViewById(R.id.tab_favourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FavouritesActivity.class);
                startActivity(intent);
            }
        });

        BottomBarTab groceriesButton = (BottomBarTab) findViewById(R.id.tab_groceries);
        groceriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GroceriesActivity.class);
                startActivity(intent);
            }
        });
    }

    // Find the Week Activity on the stack and goes back to it (to the beginning of the tack).
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WeekActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
        if (requestCode == ADD_RECIPE_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), R.string.recipe_saved_toast, Toast.LENGTH_SHORT)
                    .show();
        }
        if (requestCode == ADD_RECIPE_REQUEST && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), R.string.recipe_discarded_toast, Toast.LENGTH_SHORT).show();
        }
        // Was edited successfully.
        if (requestCode == RecipeListAdapter.SHOW_RECIPE_REQUEST && resultCode == RESULT_OK) {
            switch (data.getIntExtra(FullRecipeActivity.RECIPE_EDITING_RESULT, 0)) {
                case FullRecipeActivity.RECIPE_DELETED:
                    Toast.makeText(getApplicationContext(), R.string.recipe_deleted_toast, Toast.LENGTH_SHORT).show();
                    break;
                case FullRecipeActivity.RECIPE_EDITED:
                    break;
                default:
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.own_recipes_secondary_menu, menu);

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
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                TextView recipesCount = (TextView) findViewById(R.id.recipe_count_label);
                recipesCount.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
                TextView noRecepiesFound = (TextView) findViewById(R.id.zero_own_recipes_message_text);
                noRecepiesFound.setText(hasFocus ? R.string.no_search_results_found : R.string.zero_own_recipes_message_text);
            }
        });

        return true;
    }
}
