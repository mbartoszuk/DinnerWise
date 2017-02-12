package com.bartoszuk.dinnerwise.activity.week;

import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CompoundButton;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 *
 * This class is a listiner tha manages the checkboxes. It helps to keep one checkbox checked at a time.
 */
final class IfChecked implements CompoundButton.OnCheckedChangeListener {

    private final AppCompatCheckBox checkbox;

    static CompoundButton.OnCheckedChangeListener thenUncheck(AppCompatCheckBox checkbox) {
        return new IfChecked(checkbox);
    }

    private IfChecked(AppCompatCheckBox checkbox) {
        this.checkbox = checkbox;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            checkbox.setChecked(false);
        }
    }
}
