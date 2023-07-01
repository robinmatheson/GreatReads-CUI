package persistence;

import exceptions.DuplicateBookException;
import exceptions.InvalidEntryException;
import model.Book;
import model.BookStatus;
import model.Bookshelf;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// source: JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Bookshelf bs = new Bookshelf("My Bookshelf");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyBookshelf() throws IOException, InvalidEntryException, DuplicateBookException {
        Bookshelf bs = new Bookshelf("My Bookshelf");
        JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookshelf.json");
        writer.open();
        writer.write(bs);
        writer.close();

        JsonReader reader = new JsonReader("./data/testWriterEmptyBookshelf.json");
        bs = reader.read();
        assertEquals("My Bookshelf", bs.getName());
        assertEquals(0, bs.getCardinality());
    }

    @Test
    void testWriterGeneralBookshelf() throws IOException, InvalidEntryException, DuplicateBookException {
        Bookshelf bs = new Bookshelf("My Bookshelf");
        bs.shelveBook(new Book("Kingdom of Ash", "Sarah J. Maas", "cr",
                0));
        bs.shelveBook(new Book("Heartstopper", "Alice Oseman", "r", 5));
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookshelf.json");
        writer.open();
        writer.write(bs);
        writer.close();

        JsonReader reader = new JsonReader("./data/testWriterGeneralBookshelf.json");
        bs = reader.read();
        assertEquals("My Bookshelf", bs.getName());
        HashMap<String, Book> books = bs.getBooks();
        assertEquals(2, books.size());
        checkBook("Kingdom of Ash", "Sarah J. Maas", BookStatus.CURRENTLYREADING, 0,
                books.get("Kingdom of Ash"));
        checkBook("Heartstopper", "Alice Oseman", BookStatus.READ, 5, books.get("Heartstopper"));

    }
}
