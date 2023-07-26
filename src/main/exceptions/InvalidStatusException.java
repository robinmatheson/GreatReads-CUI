package exceptions;

// exception throw when an invalid status is inputted
public class InvalidStatusException extends InvalidEntryException {
    public InvalidStatusException() {
        super("Not a valid status.");
    }
}
