package com.bartoszuk.dinnerwise.activity.week;

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
import android.widget.CompoundButton;
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

/**
 * Created by Maria Bartoszuk on 01/02/2017.
 *
 * This class acts as a form of a controller between the Week Activity view and model.
 */

final class WeekListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final Week week = new Week();
    private final RecipesDB recipesDB;
    private final RecipeChoiceDB recipeChoiceDB;
    private final WeekActivity weekActivity;

    WeekListAdapter(RecipesDB recipesDB, WeekActivity weekActivity, Context context, LayoutInflater layoutInflater) {
        this.recipesDB = recipesDB;
        this.weekActivity = weekActivity;
        this.context = context;
        this.recipeChoiceDB = new RecipeChoiceDB(context);
        this.layoutInflater = layoutInflater;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    // Child refers to each view with two recipe choices.
    @Override
    public RecipeChoice getChild(int groupPosition, int childPosition) {
        Date date = getGroup(groupPosition);
        RecipeChoice choice = recipeChoiceDB.findRecipeChoiceByDate(date);
        return choice;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    // One recipes view for each day of the week.
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.recipe_daily_options, null);
        }

        RecipeChoice recipeChoice = getChild(groupPosition, childPosition);

        // Setting up the left recipe choice with its layout.
        final Recipe leftRecipe = recipesDB.findRecipeById(recipeChoice.getRecipeOneId());
        CardView leftCard = (CardView) convertView.findViewById(R.id.recipe_option_left);
        ImageView leftImage = (ImageView) leftCard.findViewById(R.id.recipe_photo);
        leftRecipe.renderThumbnailInto(leftImage);

        TextView leftTitle = (TextView) leftCard.findViewById(R.id.recipe_title);
        leftTitle.setText(leftRecipe.getTitle());

        ImageButton leftArrowButton = (ImageButton) leftCard.findViewById(R.id.arrow_forward_icon);
        leftArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FullRecipeActivity.class);
                intent.putExtra(FullRecipeActivity.RECIPE_ID_TO_OPEN, leftRecipe.getId());
                weekActivity.startActivity(intent);
            }
        });

        // Setting up the right recipe choice with its layout.
        final Recipe rightRecipe = recipesDB.findRecipeById(recipeChoice.getRecipeTwoId());
        CardView rightCard = (CardView) convertView.findViewById(R.id.recipe_option_right);
        ImageView rightImage = (ImageView) rightCard.findViewById(R.id.recipe_photo);
        rightRecipe.renderThumbnailInto(rightImage);

        TextView rightTitle = (TextView) rightCard.findViewById(R.id.recipe_title);
        rightTitle.setText(rightRecipe.getTitle());

        ImageButton rightArrowButton = (ImageButton) rightCard.findViewById(R.id.arrow_forward_icon);
        rightArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FullRecipeActivity.class);
                intent.putExtra(FullRecipeActivity.RECIPE_ID_TO_OPEN, rightRecipe.getId());
                weekActivity.startActivity(intent);
            }
        });


        final AppCompatCheckBox leftCheckbox = (AppCompatCheckBox) leftCard.findViewById(R.id.checkbox_icon);
        // Ensuring that if there is anything checked in the reused view (standard Android
        // practice to save memory), the choice does not have impact on the model.
        leftCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
        // Setting the actual choice of the user.
        leftCheckbox.setChecked(leftRecipe.getId() == recipeChoice.getChosenRecipeId());
        final AppCompatCheckBox rightCheckbox =
                (AppCompatCheckBox) rightCard.findViewById(R.id.checkbox_icon);

        // Ensuring that if there is anything checked in the reused view (standard Android
        // practice to save memory), the choice does not have impact on the model.
        rightCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
        // Setting the actual choice of the user.
        rightCheckbox.setChecked(rightRecipe.getId() == recipeChoice.getChosenRecipeId());

        // Updating the model with the chosen view check, unchecking the other one if necessary.
        rightCheckbox.setOnCheckedChangeListener(new IfChecked(leftCheckbox, recipeChoice, rightRecipe));
        leftCheckbox.setOnCheckedChangeListener(new IfChecked(rightCheckbox, recipeChoice, leftRecipe));

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

    // Group refers to the title + arrow bar (visible when the child is hidden).
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

    // Setting up the layout of the group view.
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

    // Setting the same width for both cards.
    private void equalizeWidth(CardView left, CardView right) {
        int leftMargins = findLeftAndRightMarginsOfPixel(left);
        int rightMargins = findLeftAndRightMarginsOfPixel(right);
        int cardWidth = (getScreenWidthPixels() - leftMargins - rightMargins) / 2;
        left.getLayoutParams().width = cardWidth;  // Set the width of the card.
        right.getLayoutParams().width = cardWidth;
    }

    // Figuring out the margin of both cards together with their margins.
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
