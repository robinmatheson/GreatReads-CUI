package persistence;

import model.Book;
import model.BookStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

// source: JsonSerializationDemo
public class JsonTest {
    protected void checkBook(String title, String author, BookStatus status, int rating, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(status, book.getStatus());
        assertEquals(rating, book.getRating());
    }
}
