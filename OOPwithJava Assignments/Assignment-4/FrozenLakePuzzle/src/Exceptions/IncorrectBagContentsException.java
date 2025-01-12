package Exceptions;

// Exception to be thrown when a researcher tries to carry different types of equipment in their bag
// the Exception is also thrown when a researcher tries leave with an empty bag or try to take more than 3 equipment

public class IncorrectBagContentsException extends Exception{
    public IncorrectBagContentsException() {
        super("*** Researchers cannot carry different types of equipment in their bags.");
    }
    public IncorrectBagContentsException(String message) {
        super(message);
    }
}
