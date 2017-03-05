package com.bartoszuk.dinnerwise.activity.managingownrecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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

    // References:
    // - https://github.com/svenwiegand/time-duration-picker#timedurationpickerdialog-and-timedurationpickerdialogfragment
    // - https://developer.android.com/guide/topics/ui/controls/pickers.html
    public void showPreparationTimePicker(View v) {
        new PreparationTimePicker().show(getFragmentManager(), "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_recipe_secondary_menu, menu);
        return true;
    }
}
