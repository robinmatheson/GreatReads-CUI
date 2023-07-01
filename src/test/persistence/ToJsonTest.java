package persistence;

import exceptions.DuplicateBookException;
import exceptions.InvalidEntryException;
import exceptions.InvalidGoalException;
import model.Book;
import model.BookStatus;
import model.Bookshelf;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToJsonTest {

    @Test
    public void testToJsonBook() throws InvalidEntryException {
        Book book = new Book("A", "B", "tbr", 0);
        JSONObject obj = book.toJson();
        obj.has("title");
        obj.has("author");
        obj.has("status");
        obj.has("rating");
        assertEquals("A", obj.get("title"));
        assertEquals("B", obj.get("author"));
        assertEquals(BookStatus.TOBEREAD, obj.get("status"));
        assertEquals(0, obj.getInt("rating"));
    }

    @Test
    public void testToJsonBookshelf() throws InvalidEntryException, DuplicateBookException {
        Bookshelf bs = new Bookshelf("Mine");
        bs.setGoal(13);
        bs.shelveBook(new Book("A", "B", "tbr", 0));
        bs.shelveBook(new Book("C", "D", "cr", 3));
        JSONObject obj1 = bs.toJson();
        obj1.has("name");
        obj1.has("books");
        obj1.has("goal");
        assertEquals("Mine", obj1.get("name"));
        assertEquals(13, obj1.get("goal"));
        assertTrue(obj1.get("books") instanceof JSONArray);
        assertEquals(2, ((JSONArray) obj1.get("books")).length());
    }

}
