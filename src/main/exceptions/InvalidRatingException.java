package exceptions;

// exception thrown when an invalid rating is inputted
public class InvalidRatingException extends InvalidEntryException {
    public InvalidRatingException() {
        super("Not a valid rating.");
    }
}
