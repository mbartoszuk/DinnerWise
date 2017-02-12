package com.bartoszuk.dinnerwise.model;

import java.util.Calendar;

/**
 * Created by Maria Bartoszuk on 01/02/2017.
 */

public final class Week {

    public int getNumberOfDays() {
        return 7;
    }

    public Date getDay(int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, 1 - calendar.get(Calendar.DAY_OF_WEEK));
        calendar.add(Calendar.DATE, dayIndex);
        return new Date(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH));
    }
}
