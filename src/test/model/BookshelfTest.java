package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.BookStatus.*;
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
    public void setUp() throws Exception {
        myBookshelf = new Bookshelf("Robin's Bookshelf");
        myBookshelf.setGoal(100);
        book1 = new Book("Throne of Glass", "Sarah J. Maas", READ, 4);
        book2 = new Book("Love On the Brain", "Ali Hazelwood", READ, 5);
        book3 = new Book("The Secret History", "Donna Tartt", TOBEREAD, 0);
        book4 = new Book("Heartstopper", "Alice Oseman", CURRENTLYREADING, 0);
        book5 = new Book("The Idiot", "Elif Batuman", CURRENTLYREADING, 0);
        book6 = new Book("The Poppy War", "R. F. Kuang", TOBEREAD, 0);
        book7 = new Book("The Ice Princess", "Camilla Lackberg", READ, 3);
        book8 = new Book("Circe", "Madeline Miller", READ, 2);
        book9 = new Book("Baby Rudin", "Walter Rudin", READ, 1);
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
        assertEquals(1, myBookshelf.getBooks().size());
        assertEquals(book1, myBookshelf.getBooks().get("Throne of Glass"));
    }

    @Test
    public void testSetGoal() {
        myBookshelf.setGoal(47);
        assertEquals(47, myBookshelf.getGoal());
    }

    @Test
    public void testSetName() {
        myBookshelf.setName("Your Bookshelf");
        assertEquals("Your Bookshelf", myBookshelf.getName());
    }

    @Test
    public void testShelveOneBook() throws Exception {
        myBookshelf.shelveBook(book1);
        assertEquals(1, myBookshelf.getBooks().size());
        assertEquals(book1, myBookshelf.getBooks().get("Throne of Glass"));
    }

    @Test
    public void testShelveMultipleBooks() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        assertEquals(book1, myBookshelf.getBooks().get("Throne of Glass"));
        assertEquals(book2, myBookshelf.getBooks().get("Love On the Brain"));
        assertEquals(2, myBookshelf.getBooks().size());
    }

    @Test
    public void testShelveRepeatBook() throws Exception {
        myBookshelf.shelveBook(book3);
        try {
            myBookshelf.shelveBook(book3);
            fail();
        } catch (Exception e) {
            // expected
        }
        assertEquals(1, myBookshelf.getBooks().size());
        assertEquals(book3, myBookshelf.getBooks().get("The Secret History"));
    }

    @Test
    public void testRemoveOneBook() throws Exception {
        myBookshelf.shelveBook(book3);
        assertEquals(1, myBookshelf.getBooks().size());
        assertEquals(book3, myBookshelf.getBooks().get("The Secret History"));
        myBookshelf.burnBook(book3.getTitle());
        assertTrue(myBookshelf.getBooks().isEmpty());
    }

    @Test
    public void testRemoveOneBookOfMany() throws Exception {
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
    public void testRemoveMultipleBooks() throws Exception {
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
    public void testRemoveBookNotOnShelf() throws Exception {
        myBookshelf.shelveBook(book4);
        myBookshelf.burnBook(book1.getTitle());
        assertEquals(1, myBookshelf.getBooks().size());
        assertEquals(book4, myBookshelf.getBooks().get("Heartstopper"));
    }

    @Test
    public void testAddAndRemoveBook() throws Exception {
        myBookshelf.shelveBook(book2);
        myBookshelf.burnBook(book2.getTitle());
        assertTrue(myBookshelf.getBooks().isEmpty());
    }

    @Test
    public void testInBookshelfBook() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        assertTrue(myBookshelf.inBookshelf(book1));
    }

    @Test
    public void testNotInBookshelfBook() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        assertFalse(myBookshelf.inBookshelf(book3));
    }

    @Test
    public void testInBookshelfString() throws Exception {
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertTrue(myBookshelf.inBookshelf("Love On the Brain"));
    }

    @Test
    public void testNotInBookshelfString() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book3);
        assertFalse(myBookshelf.inBookshelf("Love On the Brain"));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.getAllBooks();
        assertTrue(myBookshelf.getBooks().containsValue(book1));
        assertTrue(myBookshelf.getBooks().containsValue(book2));
        assertTrue(myBookshelf.getBooks().containsValue(book3));
    }

    @Test
    public void testGetBooksTBR() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book6);
        ArrayList<String> tbr = myBookshelf.getBooksOfStatus(TOBEREAD);
        assertEquals(2, tbr.size());
        assertTrue(tbr.contains(book6.getTitle()));
        assertTrue(tbr.contains(book3.getTitle()));
        assertFalse((tbr.contains(book2.getTitle())));
        assertFalse((tbr.contains(book1.getTitle())));
    }

    @Test
    public void testGetBooksCurrentlyReading() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book5);
        ArrayList<String> cr = myBookshelf.getBooksOfStatus(CURRENTLYREADING);
        assertTrue(cr.contains(book5.getTitle()));
        assertTrue(cr.contains(book4.getTitle()));
        assertFalse((cr.contains(book3.getTitle())));
        assertFalse((cr.contains(book1.getTitle())));
    }

    @Test
    public void testGetBooksRead() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        myBookshelf.shelveBook(book3);
        ArrayList<String> r = myBookshelf.getBooksOfStatus(READ);
        assertTrue(r.contains(book1.getTitle()));
        assertFalse(r.contains(book4.getTitle()));
        assertFalse((r.contains(book3.getTitle())));
    }

    @Test
    public void testGetBooksReadNoneInBookshelf() throws Exception {
        myBookshelf.shelveBook(book3);
        assertTrue(myBookshelf.getBooksOfStatus(READ).isEmpty());
    }

    @Test
    public void testGetBooksReadBookshelfEmpty() {
        assertTrue(myBookshelf.getBooksOfStatus(READ).isEmpty());
    }

    @Test
    public void testGetFiveStarBooks() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        ArrayList<String> fiveStars = myBookshelf.getBooksOfRating(5);
        assertEquals(1, fiveStars.size());
        assertTrue(fiveStars.contains(book2.getTitle()));
    }

    @Test
    public void testGetFourStarBooks() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        ArrayList<String> fourStars = myBookshelf.getBooksOfRating(4);
        assertEquals(1, fourStars.size());
        assertTrue(fourStars.contains(book1.getTitle()));
    }

    @Test
    public void testGetThreeStarBooks() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book7);
        ArrayList<String> threeStars = myBookshelf.getBooksOfRating(3);
        assertEquals(1, threeStars.size());
        assertTrue(threeStars.contains(book7.getTitle()));
    }

    @Test
    public void testGetTwoStarBooks() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book8);
        myBookshelf.shelveBook(book4);
        ArrayList<String> twoStars = myBookshelf.getBooksOfRating(2);
        assertEquals(1, twoStars.size());
        assertTrue(twoStars.contains(book8.getTitle()));
    }

    @Test
    public void testGetOneStarBooks() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book9);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        ArrayList<String> oneStar = myBookshelf.getBooksOfRating(1);
        assertEquals(1, oneStar.size());
        assertTrue(oneStar.contains(book9.getTitle()));
    }

    @Test
    public void testGetZeroStarBooks() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        ArrayList<String> zeroStars = myBookshelf.getBooksOfRating(0);
        assertEquals(2, zeroStars.size());
        assertTrue(zeroStars.contains(book3.getTitle()));
        assertTrue(zeroStars.contains(book4.getTitle()));
    }


    @Test
    public void testGetCardinality() throws Exception {
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
    public void testGetReadNoneOnBookshelf() throws Exception {
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        assertEquals(0, myBookshelf.getNumberRead());
    }

    @Test
    public void testGetRead() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals(2, myBookshelf.getNumberRead());
    }

    @Test
    public void testNoneGetRead() throws Exception {
        myBookshelf.shelveBook(book4);
        myBookshelf.shelveBook(book5);
        myBookshelf.shelveBook(book3);
        assertEquals(0, myBookshelf.getNumberRead());
    }

    @Test
    public void testGetGoalProgress() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals("You have read 2 books out of your goal of 100!", myBookshelf.getGoalProgress());
    }

    @Test
    public void testGetBook() throws Exception {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals(book2, myBookshelf.getBook("Love On the Brain"));
    }

}
