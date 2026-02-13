package model;

import java.util.ArrayList;

// Represents a date with a day, month, and year
public class Date {
    private int day;
    private int month;
    private int year;

    // REQUIRES: 1 <= month <= 12, day is valid for the given month (for example, 1 <= day <= 31 for month = 1)
    // EFFECTS: creates a date object with the given date of the month, the month, and the year
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // EFFECTS: return the day of the month
    public int getDay() {
        return this.day;
    }

    // EFFECTS: return the month as an integer
    public int getMonth() {
        return this.month;
    }

    // EFFECTS: return the month in string form (for example, month = 1 would return "January")
    public String getMonthAsString() {
        ArrayList<String> monthsAsString = new ArrayList<>();
        monthsAsString.add("January");
        monthsAsString.add("February");
        monthsAsString.add("March");
        monthsAsString.add("April");
        monthsAsString.add("May");
        monthsAsString.add("June");
        monthsAsString.add("July");
        monthsAsString.add("August");
        monthsAsString.add("September");
        monthsAsString.add("October");
        monthsAsString.add("November");
        monthsAsString.add("December");
        return monthsAsString.get(month - 1);
    }

    // EFFECTS: returns a string in the format "getMonthAsString() day, year"
    public String getDateAsString() {
        return getMonthAsString() + " " + this.day + ", " + this.year;
    }

    // EFFECTS: return the year
    public int getYear() {
        return this.year;
    }

    // EFFECTS: returns -1 if this is before other, 0 if this is the same date as other, and 1 if this is after other
    public int compareTo(Date other) {
        if (this.year < other.year) {
            return -1;
        } else if (this.year == other.year && this.month < other.month) {
            return -1;
        } else if (this.year == other.year && this.month == other.month && this.day < other.day) {
            return -1;
        } else if (this.year == other.year && this.month == other.month && this.day == other.day) {
            return 0;
        } else {
            return 1;
        }
    }
}
