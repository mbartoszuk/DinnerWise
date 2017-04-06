package com.bartoszuk.dinnerwise.model;

import java.util.Calendar;

/**
 * Created by Maria Bartoszuk on 12/02/2017.
 */

public class Date {

    private final int year;
    private final int month;
    private final int day;

    public Date (int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public DayOfWeek getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        // In java.util.Calendar 1 - SUNDAY, 2 - MONDAY, etc...
        return DayOfWeek.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return (year == date.year) && (month == date.month) && (day == date.day);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return year * 1000 + month * 100 + day;
    }

    @Override
    public String toString() {
        return String.format("%d-%d-%d", year, month, day);
    }
}
