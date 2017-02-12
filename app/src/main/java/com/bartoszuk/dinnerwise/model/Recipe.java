package com.bartoszuk.dinnerwise.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bartoszuk.dinnerwise.R;

import java.io.InputStream;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class Recipe {

    private final String id;
    private final String title;

    public Recipe(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void renderInto(ImageView view) {
        view.setImageResource(R.mipmap.img_placeholder);
    }

    public String getTitle() {
        return this.title;
    }
}
