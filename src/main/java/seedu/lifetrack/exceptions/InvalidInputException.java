package seedu.lifetrack.exceptions;

public class InvalidInputException extends Exception {

    public InvalidInputException() {
        super("Please ensure that you have keyed in the correct format!");
    }
}