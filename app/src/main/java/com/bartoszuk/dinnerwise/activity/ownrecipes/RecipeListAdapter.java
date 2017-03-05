package com.bartoszuk.dinnerwise.activity.ownrecipes;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipeSet;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class RecipeListAdapter extends BaseAdapter {

    private final RecipeSet ownRecipes = RecipeSet.own();
    private final Context context;
    private final LayoutInflater layoutInflater;

    RecipeListAdapter(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return ownRecipes.size();
    }

    @Override
    public Recipe getItem(int position) {
        return ownRecipes.nth(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.recipe_list_item, null);
        }
        Recipe recipe = getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.recipe_list_item_title);
        title.setText(recipe.getTitle());

        TextView description = (TextView) convertView.findViewById(R.id.recipe_list_item_description);
        description.setText(recipe.getDescription());

        return convertView;
    }
}
