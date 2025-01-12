package Equipment;

/**
 * The HazardEquipment class is an abstract subclass of Equipment that represents
 * equipment associated with hazards. It provides constructors for creating instances
 * with an ID, a default constructor, and a copy constructor. This class also overrides
 * the equals method to compare HazardEquipment objects.
 */
public abstract class HazardEquipment extends Equipment{

    public HazardEquipment(int id) {
        super(id);
    }

    public HazardEquipment(){
        super();
    }

    public HazardEquipment(HazardEquipment other){
        super(other);
    }

    @Override
    public boolean equals(Object other){
        return super.equals(other);
    }


}
