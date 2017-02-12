package com.bartoszuk.dinnerwise.activity.week;

/**
 * Created by Maria Bartoszuk on 01/02/2017.
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.ExpandableListView;

import com.bartoszuk.dinnerwise.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;

public class WeekActivity extends AppCompatActivity {

    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.week_activity_title));
        setContentView(R.layout.week_activity);
        listView = (ExpandableListView) findViewById(R.id.weeklyListView);
        listView.setAdapter(new WeekListAdapter(getBaseContext(), getLayoutInflater()));
        setExpandArrowPosition();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setExpandArrowPosition();
    }

    /** Sets the position of the dropdown and collapse arrow icons. */
    private void setExpandArrowPosition() {
        int width = dpi(48);
        int marginRight = dpi(16);
        int right = listView.getWidth() - marginRight;
        int left = right - width;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            listView.setIndicatorBounds(left, right);
        } else {
            listView.setIndicatorBoundsRelative(left, right);
        }
    }

    /** Converts from DPI to px. */
    private static int dpi(int dpiValue) {
        return dpiValue * Resources.getSystem().getDisplayMetrics().densityDpi
                / DisplayMetrics.DENSITY_DEFAULT;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.week_activity_secondary_menu, menu);
        return true;
    }
}
