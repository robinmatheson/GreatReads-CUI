package exceptions;

public class InvalidRatingException extends InvalidEntryException {
    public InvalidRatingException() {
        super("Not a valid rating.");
    }
}
