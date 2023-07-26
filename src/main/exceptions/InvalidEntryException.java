package exceptions;

// exception thrown when input is invalid for some book or bookshelf characteristic
public class InvalidEntryException extends Exception {
    public InvalidEntryException(String errorMessage) {
        super(errorMessage);
    }
}
