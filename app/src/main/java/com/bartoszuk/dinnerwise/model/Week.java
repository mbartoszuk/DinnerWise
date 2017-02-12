package com.bartoszuk.dinnerwise.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maria Bartoszuk on 01/02/2017.
 */

public final class Week {

    private final ArrayList<DayOfWeek> days = new ArrayList<>();

    public Week() {
        days.add(DayOfWeek.MONDAY);
        days.add(DayOfWeek.TUESDAY);
        days.add(DayOfWeek.WEDNESDAY);
        days.add(DayOfWeek.THURSDAY);
        days.add(DayOfWeek.FRIDAY);
        days.add(DayOfWeek.SATURDAY);
        days.add(DayOfWeek.SUNDAY);
    }

    public int getNumberOfDays() {
        return 7;
    }

    public DayOfWeek getDay(int dayIndex) {
        return days.get(dayIndex);
    }
}
