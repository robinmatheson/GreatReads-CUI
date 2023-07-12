package model;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BookshelfTest {

    private Bookshelf myBookshelf;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;
    private Book book6;
    private Book book7;
    private Book book8;
    private Book book9;

    @BeforeEach
    public void setUp() throws InvalidEntryException {
        myBookshelf = new Bookshelf("Robin's Bookshelf");
        book1 = new Book("Throne of Glass", "Sarah J. Maas", "r", 4);
        book2 = new Book("Love On the Brain", "Ali Hazelwood", "r", 5);
        book3 = new Book("The Secret History", "Donna Tartt", "tbr", 0);
        book4 = new Book("Heartstopper", "Alice Oseman", "cr", 0);
        book5 = new Book("The Idiot", "Elif Batuman", "cr", 0);
        book6 = new Book("The Poppy War", "R. F. Kuang", "tbr", 0);
        book7 = new Book("The Ice Princess", "Camilla Lackberg", "r", 3);
        book8 = new Book("Circe", "Madeline Miller", "r", 2);
        book9 = new Book("Baby Rudin", "Walter Rudin", "r", 1);
        myBookshelf.setGoal(100);
    }

    @Test
    public void testBookshelf() {
        assertEquals("Robin's Bookshelf", myBookshelf.getName());
        assertEquals(100, myBookshelf.getGoal());
        assertTrue(myBookshelf.getBooks().isEmpty());
    }

    @Test
    public void testSimpleGetters() throws Exception {
        myBookshelf.shelveBook(book1);
        assertEquals(100, myBookshelf.getGoal());
        assertEquals("Robin's Bookshelf", myBookshelf.getName());
        assertEquals(1, myBookshelf.getCardinality());
        assertEquals(book1, myBookshelf.getBooks().get("Throne of Glass"));
    }

    @Test
    public void testSetGoal() throws InvalidGoalException {
        myBookshelf.setGoal(47);
        assertEquals(47, myBookshelf.getGoal());
    }

    @Test
    public void testSetName() {
        myBookshelf.setName("Your Bookshelf");
        assertEquals("Your Bookshelf", myBookshelf.getName());
    }

    @Test
    public void testShelveOneBook() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        assertEquals(1, myBookshelf.getCardinality());
        assertEquals(book1, myBookshelf.getBooks().get("Throne of Glass"));
    }

    @Test
    public void testShelveMultipleBooks() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        assertEquals(book1, myBookshelf.getBooks().get("Throne of Glass"));
        assertEquals(book2, myBookshelf.getBooks().get("Love On the Brain"));
        assertEquals(2, myBookshelf.getCardinality());
    }

    @Test
    public void testShelveRepeatBook() throws DuplicateBookException {
        myBookshelf.shelveBook(book3);
        try {
            myBookshelf.shelveBook(book3);
            fail();
        } catch (DuplicateBookException e) {
            // expected
        }
        assertEquals(1, myBookshelf.getBooks().size());
        assertEquals(book3, myBookshelf.getBooks().get("The Secret History"));
    }

    @Test
    public void testRemoveOneBook() throws DuplicateBookException {
        myBookshelf.shelveBook(book3);
        assertEquals(1, myBookshelf.getBooks().size());
        assertEquals(book3, myBookshelf.getBooks().get("The Secret History"));
        myBookshelf.burnBook(book3.getTitle());
        assertTrue(myBookshelf.getBooks().isEmpty());
    }

    @Test
    public void testRemoveOneBookOfMany() throws DuplicateBookException {
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        myBookshelf.shelveBook(book1);
        assertEquals(3, myBookshelf.getBooks().size());
        assertEquals(book3, myBookshelf.getBooks().get("The Secret History"));
        assertEquals(book4, myBookshelf.getBooks().get("Heartstopper"));
        assertEquals(book1, myBookshelf.getBooks().get("Throne of Glass"));
        myBookshelf.burnBook(book3.getTitle());
        assertEquals(2, myBookshelf.getBooks().size());
        assertEquals(book4, myBookshelf.getBooks().get("Heartstopper"));
        assertEquals(book1, myBookshelf.getBooks().get("Throne of Glass"));
    }

    @Test
    public void testRemoveMultipleBooks() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book4);
        assertEquals(3, myBookshelf.getBooks().size());
        myBookshelf.burnBook(book2.getTitle());
        myBookshelf.burnBook(book1.getTitle());
        assertEquals(1, myBookshelf.getBooks().size());
        assertEquals(book4, myBookshelf.getBooks().get("Heartstopper"));
    }

    @Test
    public void testRemoveBookNotOnShelf() throws DuplicateBookException {
        myBookshelf.shelveBook(book4);
        myBookshelf.burnBook(book1.getTitle());
        assertEquals(1, myBookshelf.getBooks().size());
        assertEquals(book4, myBookshelf.getBooks().get("Heartstopper"));
    }

    @Test
    public void testAddAndRemoveBook() throws DuplicateBookException {
        myBookshelf.shelveBook(book2);
        myBookshelf.burnBook(book2.getTitle());
        assertTrue(myBookshelf.getBooks().isEmpty());
    }

    @Test
    public void testInBookshelfBook() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        assertTrue(myBookshelf.inBookshelf(book1));
    }

    @Test
    public void testNotInBookshelfBook() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        assertFalse(myBookshelf.inBookshelf(book3));
    }

    @Test
    public void testInBookshelfString() throws DuplicateBookException {
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertTrue(myBookshelf.inBookshelf("Love On the Brain"));
    }

    @Test
    public void testNotInBookshelfString() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book3);
        assertFalse(myBookshelf.inBookshelf("Love On the Brain"));
    }

    @Test
    public void testGetAllBooks() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.getAllBooks();
        assertTrue(myBookshelf.getBooks().containsValue(book1));
        assertTrue(myBookshelf.getBooks().containsValue(book2));
        assertTrue(myBookshelf.getBooks().containsValue(book3));
    }

    @Test
    public void testGetBooksTBR() throws DuplicateBookException, InvalidStatusException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book6);
        ArrayList<Book> tbr = myBookshelf.getBooksOfStatus("tbr");
        assertEquals(2, tbr.size());
        assertTrue(tbr.contains(book6));
        assertTrue(tbr.contains(book3));
        assertFalse((tbr.contains(book2)));
        assertFalse((tbr.contains(book1)));
    }

    @Test
    public void testGetBooksCurrentlyReading() throws InvalidStatusException, DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book5);
        ArrayList<Book> cr = myBookshelf.getBooksOfStatus("cr");
        assertTrue(cr.contains(book5));
        assertTrue(cr.contains(book4));
        assertFalse((cr.contains(book3)));
        assertFalse((cr.contains(book1)));
    }

    @Test
    public void testGetBooksRead() throws DuplicateBookException, InvalidStatusException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        myBookshelf.shelveBook(book3);
        ArrayList<Book> r = myBookshelf.getBooksOfStatus("r");
        assertTrue(r.contains(book1));
        assertFalse(r.contains(book4));
        assertFalse((r.contains(book3)));
    }

    @Test
    public void testGetBooksReadNoneInBookshelf() throws DuplicateBookException, InvalidStatusException {
        myBookshelf.shelveBook(book3);
        assertTrue(myBookshelf.getBooksOfStatus("r").isEmpty());
    }

    @Test
    public void testGetBooksReadBookshelfEmpty() throws InvalidStatusException {
        assertTrue(myBookshelf.getBooksOfStatus("r").isEmpty());
    }

    @Test
    public void testGetFiveStarBooks() throws DuplicateBookException, InvalidRatingException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        ArrayList<Book> fiveStars = myBookshelf.getBooksOfRating(5);
        assertEquals(1, fiveStars.size());
        assertTrue(fiveStars.contains(book2));
    }

    @Test
    public void testGetFourStarBooks() throws DuplicateBookException, InvalidRatingException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        ArrayList<Book> fourStars = myBookshelf.getBooksOfRating(4);
        assertEquals(1, fourStars.size());
        assertTrue(fourStars.contains(book1));
    }

    @Test
    public void testGetThreeStarBooks() throws DuplicateBookException, InvalidRatingException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book7);
        ArrayList<Book> threeStars = myBookshelf.getBooksOfRating(3);
        assertEquals(1, threeStars.size());
        assertTrue(threeStars.contains(book7));
    }

    @Test
    public void testGetTwoStarBooks() throws DuplicateBookException, InvalidRatingException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book8);
        myBookshelf.shelveBook(book4);
        ArrayList<Book> twoStars = myBookshelf.getBooksOfRating(2);
        assertEquals(1, twoStars.size());
        assertTrue(twoStars.contains(book8));
    }

    @Test
    public void testGetOneStarBooks() throws DuplicateBookException, InvalidRatingException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book9);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        ArrayList<Book> oneStar = myBookshelf.getBooksOfRating(1);
        assertEquals(1, oneStar.size());
        assertTrue(oneStar.contains(book9));
    }

    @Test
    public void testGetZeroStarBooks() throws DuplicateBookException, InvalidRatingException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        ArrayList<Book> zeroStars = myBookshelf.getBooksOfRating(0);
        assertEquals(2, zeroStars.size());
        assertTrue(zeroStars.contains(book3));
        assertTrue(zeroStars.contains(book4));
    }


    @Test
    public void testGetCardinality() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals(3, myBookshelf.getCardinality());
    }

    @Test
    public void testGetReadEmptyBookshelf() {
        assertEquals(0, myBookshelf.getNumberRead());
    }

    @Test
    public void testGetReadNoneOnBookshelf() throws DuplicateBookException {
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        assertEquals(0, myBookshelf.getNumberRead());
    }

    @Test
    public void testGetRead() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals(2, myBookshelf.getNumberRead());
    }

    @Test
    public void testNoneGetRead() throws DuplicateBookException {
        myBookshelf.shelveBook(book4);
        myBookshelf.shelveBook(book5);
        myBookshelf.shelveBook(book3);
        assertEquals(0, myBookshelf.getNumberRead());
    }

    @Test
    public void testGetGoalProgress() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals("You have read 2 books out of your goal of 100!", myBookshelf.getGoalProgress());
    }

    @Test
    public void testGetGoalProgressOneBook() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book3);
        assertEquals("You have read 1 book out of your goal of 100!", myBookshelf.getGoalProgress());
    }

    @Test
    public void testGetBook() throws DuplicateBookException {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals(book2, myBookshelf.getBook("Love On the Brain"));
    }

    @Test
    public void testInvalidGoal() {
        try {
            myBookshelf.setGoal(-1);
            fail();
        } catch (InvalidGoalException e) {
            // expected
        }
    }

    @Test
    public void testInvalidConvertStatus() {
        try {
            myBookshelf.getBooksOfStatus("b");
            fail();
        } catch (InvalidStatusException e) {
            // expected
        }

    }

    @Test
    public void testGetBooksOfInvalidRating() {
        try {
            myBookshelf.getBooksOfRating(-3);
            fail();
        } catch (InvalidRatingException e) {
            // expected
        }
    }

}
