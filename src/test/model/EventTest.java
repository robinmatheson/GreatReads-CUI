package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Most Events tested in EventLogTest.java

public class EventTest {

    @Test
    public void testCreateBookshelfEvent() {
        Event e = new Event("Made bookshelf called My Bookshelf");
        assertEquals("Made bookshelf called My Bookshelf", e.getDescription());
    }
}
