package model;

import java.util.Calendar;
import java.util.Date;

// Represents a log event 
// ATTRIBUTION: from AlarmSystem
public class Event {
    private Date dateLogged;
    private String description;

    // EFFECTS: creates an event with the given description and the current date/time stamp
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // EFFECTS: returns the date of this event (includes time)
    public Date getDate() {
        return dateLogged;
    }

    // EFFECTS: returns the description of this event
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns the string representation of this event
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
