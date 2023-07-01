package exceptions;

public class InvalidStatusException extends InvalidEntryException {
    public InvalidStatusException() {
        super("Not a valid status.");
    }
}
