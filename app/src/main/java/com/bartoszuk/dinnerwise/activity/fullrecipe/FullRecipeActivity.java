package com.bartoszuk.dinnerwise.activity.fullrecipe;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;

import com.bartoszuk.dinnerwise.R;

public class FullRecipeActivity extends AppCompatActivity {

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
        return true;
    }
}
