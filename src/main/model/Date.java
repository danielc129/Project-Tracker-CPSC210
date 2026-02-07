package model;

// Represents a date with a day, month, and year
public class Date {

    // REQUIRES: 1 <= month <= 12, day is valid for the given month (for example, 1 <= day <= 31 for month = 1)
    // EFFECTS: creates a date object with the given date of the month, the month, and the year
    public Date(int day, int month, int year) {

    }

    // EFFECTS: return the day of the month
    public int getDay() {
        return -1;
    }

    // EFFECTS: return the month as an integer
    public int getMonth() {
        return -1;
    }

    // EFFECTS: return the month in string form (for example, month = 1 would return "January")
    public String getMonthAsString() {
        return null;
    }

    // EFFECTS: returns a string in the format "getMonthAsString() day, year"
    public String getDateAsString() {
        return null;
    }

    // EFFECTS: return the year
    public int getYear() {
        return -1;
    }

    // EFFECTS: returns -1 if this is before other, 0 if this is the same date as other, and 1 if this is after other
    public int compareTo(Date other) {
        return -1;
    }
}
