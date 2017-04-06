package com.bartoszuk.dinnerwise.activity.secondarymenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.bartoszuk.dinnerwise.R;

/**
 * Created by Maria Bartoszuk on 28/03/2017.
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(getString(R.string.about_title));
    }

    /** Adding the menu to the top of the activity. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.secondary_actions_menu, menu);
        return true;
    }
}
