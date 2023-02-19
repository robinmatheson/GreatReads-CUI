package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.BookStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookTest {

    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;

    @BeforeEach
    public void setUp() {
        book1 = new Book("Throne of Glass", "Sarah J. Maas", READ, 4);
        book2 = new Book("Love On the Brain", "Ali Hazelwood", READ, 5);
        book3 = new Book("The Secret History", "Donna Tartt", TOBEREAD, 0);
        book4 = new Book("Heartstopper", "Alice Oseman", CURRENTLYREADING, 0);
    }

    @Test
    public void testBook() {
        assertEquals("Throne of Glass", book1.getTitle());
        assertEquals("Ali Hazelwood", book2.getAuthor());
        assertEquals(TOBEREAD, book3.getStatus());
        assertEquals(0, book4.getRating());
    }


    @Test
    public void testChangeNewStatus() {
        book4.changeStatus(TOBEREAD);
        assertEquals(TOBEREAD, book4.getStatus());
    }

    @Test
    public void testChangeSameStatus() {
        book2.changeStatus(READ);
        assertEquals(READ, book2.getStatus());
    }

    @Test
    public void testChangeMultipleStatus() {
        book3.changeStatus(CURRENTLYREADING);
        book3.changeStatus(READ);
        assertEquals(READ, book3.getStatus());
    }

    @Test
    public void testChangeNewRating() {
        book3.changeRating(1);
        assertEquals(1, book3.getRating());
    }

    @Test
    public void testChangeSameRating() {
        book1.changeRating(4);
        assertEquals(4, book1.getRating());
    }

    @Test
    public void testChangeMultipleRating() {
        book3.changeRating(4);
        book3.changeRating(5);
        assertEquals(5, book3.getRating());
    }

    @Test
    public void testGetters() {
        assertEquals("Throne of Glass", book1.getTitle());
        assertEquals("Donna Tartt", book3.getAuthor());
        assertEquals(READ, book2.getStatus());
        assertEquals(0, book3.getRating());
    }

}
