package com.bartoszuk.dinnerwise.activity.ownrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.managingownrecipe.AddRecipeActivity;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class OwnRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.ownRecipes_title));
        setContentView(R.layout.activity_own_recipes);
        ListView recipeList = (ListView) findViewById(R.id.own_recipes_list);
        recipeList.setAdapter(new RecipeListAdapter(getBaseContext(), getLayoutInflater()));

        FloatingActionButton addRecipeButton = (FloatingActionButton) findViewById(R.id.add_recipe_button);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.own_recipes_secondary_menu, menu);
        return true;
    }
}
