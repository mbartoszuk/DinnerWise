package com.bartoszuk.dinnerwise.activity.ownrecipes;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.fullrecipe.FullRecipeActivity;
import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipeSet;
import com.bartoszuk.dinnerwise.model.RecipesDB;

/**
 * Created by Maria Bartoszuk on 04/03/2017.
 */

public class RecipeListAdapter extends BaseAdapter implements Filterable {

    public static final int SHOW_RECIPE_REQUEST = 401;

    private final RecipesDB db = RecipesDB.db();
    private final RecipeSet recipeSet;
    private String query = null;

    private final Activity activity;
    private final LayoutInflater layoutInflater;

    public RecipeListAdapter(RecipeSet recipeSet, Activity activity, LayoutInflater layoutInflater) {
        this.recipeSet = recipeSet;

        this.activity = activity;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return recipeSet.size(query);
    }

    @Override
    public Recipe getItem(int position) {
        int id = recipeSet.nth(query, position);
        return db.findRecipeById(id);
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
        final Recipe recipe = getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.recipe_list_item_title);
        title.setText(recipe.getTitle());

        TextView description = (TextView) convertView.findViewById(R.id.recipe_list_item_description);
        description.setText(recipe.getDescription());

        ImageButton arrowForward = (ImageButton) convertView.findViewById(R.id.arrow_forward_icon);
        arrowForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FullRecipeActivity.class);
                intent.putExtra(FullRecipeActivity.RECIPE_ID_TO_OPEN, recipe.getId());
                activity.startActivityForResult(intent, SHOW_RECIPE_REQUEST);
            }
        });

        return convertView;
    }

    //To use for search.
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                results.count = recipeSet.size(constraint.toString());
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                query = constraint.toString();
                notifyDataSetChanged();
            }
        };
    }
}
