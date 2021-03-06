package com.bartoszuk.dinnerwise.activity.secondarymenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.bartoszuk.dinnerwise.R;

/**
 * Created by Maria Bartoszuk on 29/03/2017.
 */

public class PreferencesActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        setTitle(getString(R.string.preferences_title));

        //Removing the shadow from the action bar to blend with the tabs.
        getSupportActionBar().setElevation(0);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.preferences_view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.secondary_actions_menu, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView;

            switch (sectionNumber) {
                case 0:
                    rootView = inflater.inflate(R.layout.fragment_preferences_by_foodgroups, container, false);
                    // Setting the default of the food groups buttons to be checked.
                    ((ToggleButton) rootView.findViewById(R.id.button_foodgroup_meat)).setChecked(true);
                    ((ToggleButton) rootView.findViewById(R.id.button_foodgroup_fish)).setChecked(true);
                    ((ToggleButton) rootView.findViewById(R.id.button_foodgroup_eggs)).setChecked(true);
                    ((ToggleButton) rootView.findViewById(R.id.button_foodgroup_dairy)).setChecked(true);
                    ((ToggleButton) rootView.findViewById(R.id.button_foodgroup_grains)).setChecked(true);
                    ((ToggleButton) rootView.findViewById(R.id.button_foodgroup_nuts)).setChecked(true);
                    return rootView;
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_preferences_by_allergies, container, false);
                    return rootView;
                default:
                    throw new RuntimeException();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show total of 2 tab pages.
            return 2;
        }

        // Fill in the names of the two tabs.
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "FOOD GROUPS";
                case 1:
                    return "ALLERGIES";
            }
            return null;
        }
    }
}
