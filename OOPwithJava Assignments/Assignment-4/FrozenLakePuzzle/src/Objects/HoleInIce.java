package Objects;

import Equipment.LargeWoodenBoard;
import Interfaces.IDangerousHazard;

/**
 * The HoleInIce class represents a hazardous hole in the ice in the Frozen Lake Puzzle game.
 * It extends the Hazard class and implements the IDangerousHazard interface.
 * 
 * This class is used to create a hole in the ice object that can be overcome by using a large wooden board.
 * It overrides the equals method to compare hole in the ice objects and the showOnMap method to represent the hole in the ice on the map.
 * 
 */

public class HoleInIce extends Hazard implements IDangerousHazard{
    
    // default constructor
    public HoleInIce(){ 
        this(-1);
    }

    // constructor with id
    public HoleInIce(int id){
        super(id);
        setOvercomeBy(new LargeWoodenBoard());
    }

    // copy constructor
    public HoleInIce(HoleInIce other){
        super(other);
    }

    // objects are equal if they have the same id and class
    @Override
    public boolean equals(Object other){
        return super.equals(other);
    }

    // show hole in the ice on the map
    @Override
    public String showOnMap() {
        return "HI";
    }
}
