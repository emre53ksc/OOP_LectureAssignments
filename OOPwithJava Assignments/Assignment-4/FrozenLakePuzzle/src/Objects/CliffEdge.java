package Objects;

import Equipment.ClimbingEquipment;
import Interfaces.IDangerousHazard;

/**
 * The CliffEdge class represents a hazardous cliff edge in the Frozen Lake Puzzle game.
 * It extends the Hazard class and implements the IDangerousHazard interface.
 * 
 * This class is used to create a cliff edge object that can be overcome by using climbing equipment.
 * It overrides the equals method to compare cliff edge objects and the showOnMap method to represent the cliff edge on the map.
 * 
 * Constructor:
 * - CliffEdge(): Initializes a new instance of the CliffEdge class and sets the equipment required to overcome it to ClimbingEquipment.
 * 
 * Methods:
 * - boolean equals(Object other): Overrides the equals method to compare this cliff edge with another object.
 * - String showOnMap(): Overrides the showOnMap method to return "CE", representing the cliff edge on the map.
 */

public class CliffEdge extends Hazard implements IDangerousHazard{
    public CliffEdge(){
        super();
        setOvercomeBy(new ClimbingEquipment());
    }
    @Override
    public boolean equals(Object other){
        return super.equals(other);
    }
    @Override
    public String showOnMap() {
        return "CE";
    }
}
