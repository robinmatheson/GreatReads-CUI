package exceptions;

public class InvalidGoalException extends InvalidEntryException {
    public InvalidGoalException() {
        super("Not a valid goal.");
    }
}
