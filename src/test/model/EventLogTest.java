package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventLogTest {

    private EventLog el;
    private Bookshelf bs;
    private Iterator<Event> itr;

    @BeforeEach
    public void setUp() {
        el = EventLog.getInstance();
        el.clear();
    }

    @Test
    public void testCreateBookshelf() {
        bs = new Bookshelf("Mine");
        itr = el.iterator();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Made bookshelf called Mine", itr.next().getDescription());
    }

    @Test
    public void testAddBook() {
        bs = new Bookshelf("Mine");
        bs.shelveBook(new Book("Heartstopper", "Alice Oseman", BookStatus.READ, 5));
        itr = el.iterator();
        itr.next();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Added Heartstopper to bookshelf", itr.next().getDescription());
    }

    @Test
    public void testBurnBook() {
        bs = new Bookshelf("Mine");
        bs.shelveBook(new Book("Heartstopper", "Alice Oseman", BookStatus.READ, 5));
        bs.shelveBook(new Book("Babel", "RF Kuang", BookStatus.TOBEREAD, 0));
        bs.burnBook("Heartstopper");
        itr = el.iterator();
        itr.next();
        itr.next();
        itr.next();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Removed Heartstopper from bookshelf", itr.next().getDescription());
    }

    @Test
    public void testChangeBookshelfName() {
        bs = new Bookshelf("Mine");
        bs.setName("Yours");
        itr = el.iterator();
        itr.next();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Changed name of bookshelf to Yours", itr.next().getDescription());
    }
}
