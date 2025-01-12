package Exceptions;

// Exception to be thrown when a researcher tries to use equipment that is not in their bag
// the Exception is also thrown when a researcher tries to perform an experiment while carrying no Research Equipment

public class UnavailableEquipmentException extends Exception{
    public UnavailableEquipmentException() {
        super("");
    }
    public UnavailableEquipmentException(String message) {
        super(message);
    }
}
