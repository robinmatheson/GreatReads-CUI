package model;

import exceptions.InvalidEntryException;
import exceptions.InvalidRatingException;
import exceptions.InvalidStatusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.BookStatus.*;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookTest {

    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;

    @BeforeEach
    public void setUp() throws InvalidEntryException {
        book1 = new Book("Throne of Glass", "Sarah J. Maas", "r", 4);
        book2 = new Book("Love On the Brain", "Ali Hazelwood", "r", 5);
        book3 = new Book("The Secret History", "Donna Tartt", "tbr", 0);
        book4 = new Book("Heartstopper", "Alice Oseman", "cr", 0);
    }

    @Test
    public void testBook() {
        assertEquals("Throne of Glass", book1.getTitle());
        assertEquals("Ali Hazelwood", book2.getAuthor());
        assertEquals(TOBEREAD, book3.getStatus());
        assertEquals(0, book4.getRating());
    }


    @Test
    public void testChangeNewStatus() throws InvalidStatusException {
        book4.changeStatus("tbr");
        assertEquals(TOBEREAD, book4.getStatus());
    }

    @Test
    public void testChangeSameStatus() throws InvalidStatusException {
        book2.changeStatus("r");
        assertEquals(READ, book2.getStatus());
    }

    @Test
    public void testChangeMultipleStatus() throws InvalidStatusException {
        book3.changeStatus("cr");
        book3.changeStatus("r");
        assertEquals(READ, book3.getStatus());
    }

    @Test
    public void testChangeNewRating() throws InvalidRatingException {
        book3.changeRating(1);
        assertEquals(1, book3.getRating());
    }

    @Test
    public void testChangeSameRating() throws InvalidRatingException {
        book1.changeRating(4);
        assertEquals(4, book1.getRating());
    }

    @Test
    public void testChangeMultipleRating() throws InvalidRatingException {
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

    @Test
    public void testInvalidRating() {
        try {
            book2.changeRating(6);
            fail();
        } catch (InvalidRatingException e) {
            // expected
        }
    }

    @Test
    public void testInvalidStatus() {
        try {
            book3.changeStatus("dnf");
            fail();
        } catch (InvalidStatusException e) {
            //expected
        }
    }

}
