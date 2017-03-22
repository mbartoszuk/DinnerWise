package com.bartoszuk.dinnerwise.activity.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.week.WeekActivity;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {

    private final ArrayList<TextView> dotsArray = new ArrayList<>();
    private OnboardingPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // Setting the View Pager to control the Onboarding.
        final ViewPager viewPager = (ViewPager) findViewById(R.id.onboarding_viewpager);
        adapter = new OnboardingPagerAdapter(getLayoutInflater(), getAssets());
        viewPager.setAdapter(adapter);

        // Setting the Next button to move to the next view on click.
        Button nextButton = (Button) findViewById(R.id.onboarding_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem() + 1;
                if (currentItem < adapter.getCount()) {
                    viewPager.setCurrentItem(currentItem);
                } else {
                    openWeekActivity();
                }
            }
        });

        // Setting the Skip button to move straight to Week Activity.
        Button skipButton = (Button) findViewById(R.id.onboarding_skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeekActivity();
            }
        });

        // Setting up the layout of all dots.
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layout_dots);
        for (int i = 0; i < adapter.getCount(); i++) {
            TextView singleDot = new TextView(this);
            singleDot.setText(Html.fromHtml("&#8226;"));
            dotsLayout.addView(singleDot);
            dotsArray.add(singleDot);
        }

        // Listenes to the page index to know which dot to select.
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setSelectedDot(position);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasGoneThroughOnboarding()) {
            openWeekActivity();
        }
    }

    private boolean hasGoneThroughOnboarding() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return preferences.getBoolean(getString(R.string.preference_key_went_through_onboarding), false);
    }

    private void markOnboardingDone() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(getString(R.string.preference_key_went_through_onboarding), true);
        edit.apply();
    }

    // Opens the Week Activity after the last Onboarding Screen.
    private void openWeekActivity() {
        markOnboardingDone();
        startActivity(new Intent(this, WeekActivity.class));
        finish();
    }

    // Decides which dot to set as currently active, and which deselected.
    private void setSelectedDot(int selectedIndex) {
        for (int i = 0; i < adapter.getCount(); i++) {
            if (i == selectedIndex) {
                dotsArray.get(i).setTextColor(ContextCompat.getColor(getBaseContext(), R.color.primary_dark));
                dotsArray.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
            } else {
                dotsArray.get(i).setTextColor(ContextCompat.getColor(getBaseContext(), R.color.divider));
                dotsArray.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
            }
        }
    }
}
