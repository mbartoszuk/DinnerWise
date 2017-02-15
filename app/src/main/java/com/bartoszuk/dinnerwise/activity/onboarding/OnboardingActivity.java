package com.bartoszuk.dinnerwise.activity.onboarding;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bartoszuk.dinnerwise.R;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // Setting the View Pager to control the Onboarding.
        ViewPager viewPager = (ViewPager) findViewById(R.id.onboarding_viewpager);
        viewPager.setAdapter(new OnboardingPagerAdapter(getLayoutInflater(), getAssets()));
    }
}
