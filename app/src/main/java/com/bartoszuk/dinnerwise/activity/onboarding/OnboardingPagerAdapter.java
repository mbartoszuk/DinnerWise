package com.bartoszuk.dinnerwise.activity.onboarding;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszuk.dinnerwise.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Maria Bartoszuk on 15/02/2017.
 *
 * This class fills out the onboarding layout with proper images, titles and tips.
 */

public class OnboardingPagerAdapter extends PagerAdapter {

    private final List<Integer> title = new ArrayList<>();
    private final List<Integer> tip = new ArrayList<>();
    private final List<Drawable> image = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private final Typeface typeface;

    public OnboardingPagerAdapter(LayoutInflater layoutInflater, AssetManager assets) {

        this.layoutInflater = layoutInflater;
        this.typeface = Typeface.createFromAsset(assets, "fonts/Quicksand-Light.ttf");

        title.add(R.string.onboarding_title_one);
        title.add(R.string.onboarding_title_two);
        title.add(R.string.onboarding_title_three);
        title.add(R.string.onboarding_title_four);

        tip.add(R.string.onboarding_tip_one);
        tip.add(R.string.onboarding_tip_two);
        tip.add(R.string.onboarding_tip_three);
        tip.add(R.string.onboarding_tip_four);

        for (int i = 1; i <= 4; i++) {
            String path = String.format("images/onboarding/%d.png", i);
            try (InputStream imageStream = assets.open(path)) {
                image.add(Drawable.createFromStream(imageStream, null));
            } catch (IOException e) {
                throw new RuntimeException(String.format("Failed to load %s", path), e);
            }
        }
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.onboarding_content, container, false);

        // Setting the corresponding image from the array based on array position.
        ImageView onboardingImage = (ImageView) view.findViewById(R.id.onboarding_image);
        onboardingImage.setImageDrawable(image.get(position));

        // Setting the corresponding title from the array based on array position.
        TextView onboardingTitle = (TextView) view.findViewById(R.id.onboarding_title);
        onboardingTitle.setTypeface(typeface);
        onboardingTitle.setText(title.get(position));

        // Setting the corresponding tip from the array based on array position.
        TextView onboardingTip = (TextView) view.findViewById(R.id.onboarding_tip);
        onboardingTip.setTypeface(typeface);
        onboardingTip.setText(tip.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
