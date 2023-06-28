package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    private Event e;

    @Test
    public void testCreateBookshelfEvent() {
        e = new Event("Made bookshelf called My Bookshelf");
        assertEquals("Made bookshelf called My Bookshelf", e.getDescription());
    }
}
