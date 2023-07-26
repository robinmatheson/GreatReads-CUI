package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// source: Event class from Alarm project, CPSC 210
/**
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 */
public class EventLog implements Iterable<Event> {

    // the only EventLog in the system (Singleton Design Pattern)
    private static EventLog theLog;
    private final Collection<Event> events;

    // Singleton Design Pattern
    // EFFECTS: private constructor that prevents external construction
    private EventLog() {
        events = new ArrayList<>();
    }

    // Singleton Design Pattern
    // EFFECTS: gets instance of EventLog or creates it if it doesn't already exist and returns it
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds the event 'e' to the event log
    public void logEvent(Event e) {
        events.add(e);
    }


    // MODIFIES: this
    // EFFECTS: clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns an iterator for the events
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}

