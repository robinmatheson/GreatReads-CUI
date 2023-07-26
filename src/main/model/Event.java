package model;

import java.util.Calendar;
import java.util.Date;

// source: Event class from Alarm project, CPSC 210

// Represents a bookshelf event.
public class Event {

    private static final int HASH_CONSTANT = 13;
    private final Date dateLogged;
    private final String description;

    // EFFECTS: Creates an event with the given description and the current date/time stamp
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // not used in project
    // EFFECTS: returns date of event
    public Date getDate() {
        return dateLogged;
    }

    // EFFECTS: returns the description of this event
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: returns a readable string of this Event
    @Override
    public String toString() {
        return dateLogged.toString() + ": " + description;
    }
}

