package exceptions;

// exception thrown when an invalid goal is inputted
public class InvalidGoalException extends InvalidEntryException {
    public InvalidGoalException() {
        super("Not a valid goal.");
    }
}
