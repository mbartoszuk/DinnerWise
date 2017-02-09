package com.bartoszuk.dinnerwise.activity.week;

/**
 * Created by Maria Bartoszuk on 01/02/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bartoszuk.dinnerwise.model.DayOfWeek;
import com.bartoszuk.dinnerwise.model.Week;
import com.bartoszuk.dinnerwise.R;

final class WeekListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final Week week = new Week();

    WeekListAdapter(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getGroupCount() {
        return week.getNumberOfDays();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        DayOfWeek day = week.getDay(groupPosition);
        return day.toString();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return "Placeholder for two recipes.";
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.week_list_item_header, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.week_list_item_header_text);

        String headerTitle = (String) getGroup(groupPosition);
        textView.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String headerTitle = (String) getChild(groupPosition, childPosition);
        TextView textView = new TextView(context);
        textView.setText(headerTitle);
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
