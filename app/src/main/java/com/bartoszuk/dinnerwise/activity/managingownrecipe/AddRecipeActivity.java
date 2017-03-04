package com.bartoszuk.dinnerwise.activity.managingownrecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.bartoszuk.dinnerwise.R;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class AddRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.manageRecipe_title));
        setContentView(R.layout.activity_manage_recipe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_recipe_secondary_menu, menu);
        return true;
    }
}
