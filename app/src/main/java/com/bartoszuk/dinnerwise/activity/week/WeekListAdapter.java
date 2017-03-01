package com.bartoszuk.dinnerwise.activity.week;

/**
 * Created by Maria Bartoszuk on 01/02/2017.
 *
 * This class acts as a form of controller between the Week Activity view and model.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.fullrecipe.FullRecipeActivity;
import com.bartoszuk.dinnerwise.model.Date;
import com.bartoszuk.dinnerwise.model.Recipe;
import com.bartoszuk.dinnerwise.model.RecipeChoice;
import com.bartoszuk.dinnerwise.model.RecipeChoiceDB;
import com.bartoszuk.dinnerwise.model.RecipesDB;
import com.bartoszuk.dinnerwise.model.Week;

final class WeekListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final Week week = new Week();
    private final RecipesDB recipesDB = new RecipesDB();
    private final RecipeChoiceDB recipeChoiceDB = new RecipeChoiceDB();
    private final WeekActivity weekActivity;

    WeekListAdapter(WeekActivity weekActivity, Context context, LayoutInflater layoutInflater) {
        this.weekActivity = weekActivity;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return "Placeholder for the choice between two recipes.";
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.recipe_daily_options, null);
        }

        Date date = getGroup(groupPosition);

        RecipeChoice recipeChoice = recipeChoiceDB.findRecipeChoiceByDate(date);
        Recipe leftRecipe = recipesDB.findRecipeById(recipeChoice.getRecipeOneId());
        CardView leftCard = (CardView) convertView.findViewById(R.id.recipe_option_left);
        ImageView leftImage = (ImageView) leftCard.findViewById(R.id.recipe_photo);
        leftRecipe.renderInto(leftImage);

        TextView leftTitle = (TextView) leftCard.findViewById(R.id.recipe_title);
        leftTitle.setText(leftRecipe.getTitle());

        Recipe rightRecipe = recipesDB.findRecipeById(recipeChoice.getRecipeTwoId());
        CardView rightCard = (CardView) convertView.findViewById(R.id.recipe_option_right);
        ImageView rightImage = (ImageView) rightCard.findViewById(R.id.recipe_photo);
        rightRecipe.renderInto(rightImage);

        ImageButton arrowButton = (ImageButton) leftCard.findViewById(R.id.arrow_forward_icon);
        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FullRecipeActivity.class);
                weekActivity.startActivity(intent);
            }
        });

        TextView rightTitle = (TextView) rightCard.findViewById(R.id.recipe_title);
        rightTitle.setText(rightRecipe.getTitle());

        final AppCompatCheckBox leftCheckbox =
                (AppCompatCheckBox) leftCard.findViewById(R.id.checkbox_icon);
        final AppCompatCheckBox rightCheckbox =
                (AppCompatCheckBox) rightCard.findViewById(R.id.checkbox_icon);
        rightCheckbox.setOnCheckedChangeListener(IfChecked.thenUncheck(leftCheckbox));
        leftCheckbox.setOnCheckedChangeListener(IfChecked.thenUncheck(rightCheckbox));

        equalizeWidth(leftCard, rightCard);

        // Remove the divider from the last expandable tab.
        View divider = convertView.findViewById(R.id.divider_expandable_child);
        if (groupPosition == (week.getNumberOfDays() - 1)) {
            divider.setVisibility(View.GONE);
        } else {
            divider.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public Date getGroup(int groupPosition) {
        return week.getDay(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return week.getNumberOfDays();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.week_list_item_header, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.week_list_item_header_text);
        Date date = getGroup(groupPosition);
        textView.setText(date.getDayOfWeek().toString());
        int colorResourceId = isExpanded ? R.color.primary : R.color.black;
        int color = ResourcesCompat.getColor(context.getResources(), colorResourceId, null);
        textView.setTextColor(color);

        return convertView;
    }

    private void equalizeWidth(CardView left, CardView right) {
        int leftMargins = findLeftAndRightMarginsOfPixel(left);
        int rightMargins = findLeftAndRightMarginsOfPixel(right);
        int cardWidth = (getScreenWidthPixels() - leftMargins - rightMargins) / 2;
        left.getLayoutParams().width = cardWidth;  // Set the width of the card.
        right.getLayoutParams().width = cardWidth;
    }

    private int findLeftAndRightMarginsOfPixel(CardView cardView) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
        return params.leftMargin + params.rightMargin;
    }

    private int getScreenWidthPixels() {
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

}
