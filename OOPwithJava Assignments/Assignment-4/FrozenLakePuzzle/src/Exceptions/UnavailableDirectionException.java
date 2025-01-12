package Exceptions;

// Exception to be thrown when a researcher tries to move in an unavailable direction


public class UnavailableDirectionException extends Exception{
    public UnavailableDirectionException() {
        super("*** The input direction is unavailable. Please enter an available direction:");
    }
    public UnavailableDirectionException(String message) {
        super(message);
    }
}
