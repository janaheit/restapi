package be.abis.exercise.exception;

public class PersonCannotBeDeletedException extends Exception {
    public PersonCannotBeDeletedException(String message) {
        super(message);
    }
}
