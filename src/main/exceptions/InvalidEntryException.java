package exceptions;

public class InvalidEntryException extends Exception {
    public InvalidEntryException(String errorMessage) {
        super(errorMessage);
    }
}
