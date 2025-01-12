package Equipment;

import Interfaces.IMapPlaceable;

/**
 * The Equipment class is an abstract class that implements the IMapPlaceable interface.
 * It represents an equipment item with a unique identifier.
 * 
 * This class provides constructors for creating equipment with a specified ID, 
 * a default ID, or by copying another equipment's ID. It also provides methods 
 * to get and set the ID, and an abstract method to show the equipment on a map.</p>
 * 
 * The equals method is overridden to compare equipment objects based on their IDs.</p>
 * 
 * 
 */
public abstract class Equipment implements IMapPlaceable{
    private int id;

    public Equipment(int id){
        this.id = id;
    } 

    public Equipment(){
        this(-1);
    }

    public Equipment(Equipment other){
        this(other.id);
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public abstract String showOnMap();

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
            Equipment otherEquipment = (Equipment) other;
            return this.id == otherEquipment.id;
        }      
    }
}
