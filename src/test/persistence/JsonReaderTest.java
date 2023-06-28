package persistence;

import model.Book;
import model.BookStatus;
import model.Bookshelf;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// source: JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Bookshelf bs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //expected
        }
    }

    @Test
    void testReaderEmptyBookshelf() {
        try {
            Bookshelf bs = new Bookshelf("My Bookshelf");
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyWorkRoom.json");
            writer.open();
            writer.write(bs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
            Bookshelf newBS = reader.read();
            assertEquals("My Bookshelf", newBS.getName());
            assertEquals(0, newBS.getCardinality());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReaderGeneralBookshelf() {
        try {
            Bookshelf bs = new Bookshelf("My Bookshelf");
            bs.shelveBook(new Book("Kingdom of Ash", "Sarah J. Maas", BookStatus.CURRENTLYREADING,
                    0));
            bs.shelveBook(new Book("Heartstopper", "Alice Oseman", BookStatus.READ, 5));
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralWorkRoom.json");
            writer.open();
            writer.write(bs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
            Bookshelf newBS = reader.read();
            assertEquals("My Bookshelf", newBS.getName());
            ArrayList<Book> books = newBS.getBooks();
            assertEquals(2, books.size());
            checkBook("Kingdom of Ash", "Sarah J. Maas", BookStatus.CURRENTLYREADING, 0,
                    books.get(0));
            checkBook("Heartstopper", "Alice Oseman", BookStatus.READ, 5, books.get(1));
        } catch (IOException e) {
            fail();
        }
    }
}
