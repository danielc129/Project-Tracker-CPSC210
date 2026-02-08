package model;

import model.exceptions.IllegalMonthException;

// Represents a date with a day, month, and year
public class Date {
    private int day;
    private int month;
    private int year;

    // REQUIRES: 1 <= month <= 12, day is valid for the given month (for example, 1 <= day <= 31 for month = 1)
    // EFFECTS: creates a date object with the given date of the month, the month, and the year
    public Date(int day, int month, int year) throws IllegalMonthException {
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
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9: 
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            default:
                return "December";
        }
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
