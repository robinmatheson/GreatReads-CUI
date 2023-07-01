package exceptions;

public class DuplicateBookException extends Exception {
    public DuplicateBookException() {
        super("A book with the given title is already in your bookshelf. To add the current one, " +
                "please delete the old one first.");
    }
}
