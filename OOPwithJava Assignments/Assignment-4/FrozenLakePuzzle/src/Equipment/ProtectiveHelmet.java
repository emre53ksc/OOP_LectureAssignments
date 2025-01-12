package Equipment;

/**
 * The ProtectiveHelmet class extends the HazardEquipment class and represents a protective helmet item.
 * 
 * This class provides multiple constructors for creating a ProtectiveHelmet with a default ID, 
 * a specified ID, or by copying another ProtectiveHelmet's ID. It also overrides the equals method 
 * to compare ProtectiveHelmet objects based on their IDs.
 * 
 * The showOnMap method is overridden to return a string representation ("PH") of the ProtectiveHelmet 
 * on a map.
 * 
 * 
 */
public class ProtectiveHelmet extends HazardEquipment{

    public ProtectiveHelmet() {
        super();
    }

    public ProtectiveHelmet(int id) {
        super(id);
    }

    public ProtectiveHelmet(ProtectiveHelmet other){
        super(other);
    }

    public boolean equals(Object other){
        return super.equals(other);
    }

    @Override
    public String showOnMap() {
        return "PH";
    }
}
