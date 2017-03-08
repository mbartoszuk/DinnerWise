package com.bartoszuk.dinnerwise.activity.ownrecipes;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.fullrecipe.FullRecipeActivity;
import com.bartoszuk.dinnerwise.activity.managingownrecipe.AddRecipeActivity;
import com.bartoszuk.dinnerwise.model.RecipeSet;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class OwnRecipesActivity extends AppCompatActivity {

    private static final int ADD_RECIPE_REQUEST = 561;
    static final int SHOW_RECIPE_REQUEST = 562;

    private final RecipeSet own = RecipeSet.own();
    private RecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.ownRecipes_title));
        setContentView(R.layout.activity_own_recipes);

        // Change the count label when the data is changed.
        TextView recipesCount = (TextView) findViewById(R.id.recipe_count_label);
        recipesCount.setText(own.size() + " recipes");

        ListView recipeList = (ListView) findViewById(R.id.own_recipes_list);
        adapter = new RecipeListAdapter(this, getLayoutInflater());
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                TextView recipesCount = (TextView) findViewById(R.id.recipe_count_label);
                recipesCount.setText(own.size() + " recipes");
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_RECIPE_REQUEST && resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), R.string.recipe_saved_toast, Toast.LENGTH_SHORT)
                    .show();
        }
        if (requestCode == ADD_RECIPE_REQUEST && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), R.string.recipe_discarded_toast, Toast.LENGTH_SHORT).show();
        }
        // Was edited successfully.
        if (requestCode == SHOW_RECIPE_REQUEST && resultCode == RESULT_OK) {
            switch (data.getIntExtra(FullRecipeActivity.RECIPE_EDITING_RESULT, 0)) {
                case FullRecipeActivity.RECIPE_DELETED:
                    Toast.makeText(getApplicationContext(), R.string.recipe_deleted_toast, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    break;
                case FullRecipeActivity.RECIPE_EDITED:
                    adapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.own_recipes_secondary_menu, menu);
        return true;
    }
}
