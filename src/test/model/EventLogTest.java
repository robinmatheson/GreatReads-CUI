package model;

import exceptions.DuplicateBookException;
import exceptions.InvalidEntryException;
import exceptions.InvalidGoalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventLogTest {

    //test class for Event seems redundant when EventLogTest implicitly tests Event anyway

    private EventLog el;
    private Bookshelf bs;
    private Iterator<Event> itr;
    private JsonWriter writer;
    private JsonReader reader;
    private static final String JSON_STORE = "./data/bookshelf.json";

    @BeforeEach
    public void setUp() {
        el = EventLog.getInstance();
        el.clear();
    }

    @Test
    public void testCreateBookshelfLog() {
        bs = new Bookshelf("Mine");
        itr = el.iterator();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Made bookshelf called Mine", itr.next().getDescription());
    }

    @Test
    public void testAddBookLog() throws DuplicateBookException, InvalidEntryException {
        bs = new Bookshelf("Mine");
        bs.shelveBook(new Book("Heartstopper", "Alice Oseman", "r", 5));
        itr = el.iterator();
        itr.next();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Added Heartstopper to bookshelf", itr.next().getDescription());
    }

    @Test
    public void testBurnBookLog() throws DuplicateBookException, InvalidEntryException {
        bs = new Bookshelf("Mine");
        bs.shelveBook(new Book("Heartstopper", "Alice Oseman", "r", 5));
        bs.shelveBook(new Book("Babel", "RF Kuang", "tbr", 0));
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
    public void testChangeBookshelfNameLog() {
        bs = new Bookshelf("Mine");
        bs.setName("Yours");
        itr = el.iterator();
        itr.next();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Changed name of bookshelf to Yours", itr.next().getDescription());
    }

    @Test
    public void testSaveToFileLog() {
        bs = new Bookshelf("Mine");
        writer = new JsonWriter(JSON_STORE);
        try {
            writer.open();
            writer.write(bs);
            writer.close();
            // expected
        } catch (FileNotFoundException e) {
            fail();
        }
        itr = el.iterator();
        itr.next();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Saved bookshelf to file.", itr.next().getDescription());
    }

    @Test
    public void testLoadFromFileLog() {
        // make bookshelf to save to file
        bs = new Bookshelf("First");
        writer = new JsonWriter(JSON_STORE);
        try {
            writer.open();
            writer.write(bs);
            writer.close();
            // expected
        } catch (FileNotFoundException e) {
            fail();
        } catch (Exception e) {
            fail();
        }

        // make second bookshelf
         bs = new Bookshelf("Second");
        assertEquals("Second", bs.getName());

        // load First bookshelf from file
        reader = new JsonReader(JSON_STORE);
        try {
            bs = reader.read();
        } catch (IOException e) {
            fail();
        } catch (Exception e) {
            fail();
        }
        assertEquals("First", bs.getName());
        itr = el.iterator();
        itr.next();
        itr.next();
        itr.next();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Loaded bookshelf from file.", itr.next().getDescription());
    }

    @Test
    public void testSetGoalLog() throws InvalidGoalException {
        bs = new Bookshelf("Mine");
        bs.setGoal(31);
        itr = el.iterator();
        itr.next();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Set reading goal to 31 books.", itr.next().getDescription());
    }

    @Test
    public void testChangeStatusLog() throws InvalidEntryException {
        Book book = new Book("Heartstopper", "Alice Oseman", "r", 5);
        book.changeStatus("cr");
        itr = el.iterator();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Changed status of Heartstopper", itr.next().getDescription());
    }

    @Test
    public void testChangeRatingLog() throws InvalidEntryException {
        Book book = new Book("Heartstopper", "Alice Oseman", "r", 5);
        book.changeRating(1);
        itr = el.iterator();
        itr.next();
        assertTrue(itr.hasNext());
        assertEquals("Changed rating of Heartstopper", itr.next().getDescription());
    }
}
