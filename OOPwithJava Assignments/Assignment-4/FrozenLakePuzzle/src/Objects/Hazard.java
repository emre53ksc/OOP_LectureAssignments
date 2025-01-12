package Objects;

import Equipment.HazardEquipment;
import Interfaces.IMapPlaceable;

/*
 * Abstract class that represents a hazard on the map.
 * Each hazard has an id and a piece of equipment that can overcome it.
 * The class implements the IMapPlaceable interface.
 */

public abstract class Hazard implements IMapPlaceable{

    HazardEquipment overcomeBy; // Equipment required to overcome the hazard
    private int id; // Unique identifier for the hazard

    // default constructor
    public Hazard(){
        this(-1);
    }

    // constructor with id
    public Hazard(int id){
        this.id = id;
        overcomeBy = null;
    }

    // copy constructor
    public Hazard(Hazard other){
        this.id = other.id;
        this.overcomeBy = other.overcomeBy;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HazardEquipment getOvercomeBy() {
        return overcomeBy;
    }

    public void setOvercomeBy(HazardEquipment overcomeBy) {
        this.overcomeBy = overcomeBy;
    }
    
    // objects are equal if they have the same id and class
    @Override
    public boolean equals(Object other){
        // Check if the current object is the same as the passed object
        if (this == other) {
            return true;
        }
        // Check if the passed object is null or if the classes of the objects are different
        if (other == null || getClass() != other.getClass()){
            return false;
        }
        else {
            // Cast the passed object to a Hazard object
            Hazard otherHazard = (Hazard) other;
            return otherHazard.getId() == this.id;
        }      
    }

    // all hazards have to be represented on the map
    public abstract String showOnMap();
}
