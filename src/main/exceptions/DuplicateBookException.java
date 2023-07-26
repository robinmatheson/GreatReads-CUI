package exceptions;

// exception thrown when a book with the same title as one already shelved is trying to be shelved due to titles
// used as HashMap keys
public class DuplicateBookException extends Exception {
    public DuplicateBookException() {
        super("A book with the given title is already in your bookshelf. To add the current one, " +
                "please delete the old one first.");
    }
}
