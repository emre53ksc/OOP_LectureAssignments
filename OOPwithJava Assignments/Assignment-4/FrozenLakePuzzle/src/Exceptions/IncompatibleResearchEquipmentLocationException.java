package Exceptions;

// Exception to be thrown when a researcher tries to use research equipment that is incompatible with the current location

public class IncompatibleResearchEquipmentLocationException extends Exception{
    public IncompatibleResearchEquipmentLocationException() {
        super("*** The selected research equipment is incompatible with the current location.");
    }
    public IncompatibleResearchEquipmentLocationException(String message){
        super(message);
    }

}
