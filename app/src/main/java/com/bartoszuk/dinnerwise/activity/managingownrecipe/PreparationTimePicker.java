package com.bartoszuk.dinnerwise.activity.managingownrecipe;

import android.widget.EditText;

import com.bartoszuk.dinnerwise.R;

import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;

/**
 * Created by Maria Bartoszuk on 05/03/2017.
 */

public class PreparationTimePicker extends TimeDurationPickerDialogFragment {

    @Override
    protected int setTimeUnits() {
        return TimeDurationPicker.HH_MM;
    }

    @Override
    protected long getInitialDuration() {
        EditText preparationTimeInput =
            (EditText) getActivity().findViewById(R.id.recipe_preparation_time_input);
        String input = preparationTimeInput.getText().toString().trim();
        int durationMinutes;
        try {
            durationMinutes = Integer.parseInt(input);
        } catch (NumberFormatException e) {  // Exception if input is empty.
            durationMinutes = 0;  // 0 minutes default - if nothing in the input.
        }
        return durationMinutes * 60 * 1000;  // In milliseconds.
    }

    @Override
    public void onDurationSet(TimeDurationPicker view, long durationMillis) {
        EditText preparationTimeInput =
                (EditText) getActivity().findViewById(R.id.recipe_preparation_time_input);
        long durationMinutes = durationMillis / (1000 * 60);
        preparationTimeInput.setText(Long.toString(durationMinutes));
    }
}
