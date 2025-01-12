package Equipment;

/**
 * The ClimbingEquipment class extends the HazardEquipment class and represents
 * a specific type of equipment used for climbing. It inherits properties and 
 * methods from the HazardEquipment class and provides additional functionality 
 * specific to climbing equipment.
 * 
 * This class provides multiple constructors for creating climbing equipment 
 * with a default ID, a specified ID, or by copying another ClimbingEquipment 
 * object. It also overrides the equals method to compare climbing equipment 
 * objects based on their IDs.
 * 
 * The showOnMap method is overridden to return a string representation of the 
 * climbing equipment on a map.
 * 
 * 
 * 
 */
public class ClimbingEquipment extends HazardEquipment{

    public ClimbingEquipment() {
        super();
    }

    public ClimbingEquipment(int id) {
        super(id);
    }

    public ClimbingEquipment(ClimbingEquipment other){
        super(other);
    }

    public boolean equals(Object other){
        return super.equals(other);
    }

    @Override
    public String showOnMap() {
        return "CL";
    }

}
