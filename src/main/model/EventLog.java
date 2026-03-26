package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


// Singleton class that represents a log of events that were logged during the runtime of this application
// ATTRIBUTION: from AlarmSystem
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    // EFFECTS: creates the event log singleton instance, preventing external construction
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: returns the instance of EventLog, creating it if it doesn't already exist
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds the given event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns iterator for this event log
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
