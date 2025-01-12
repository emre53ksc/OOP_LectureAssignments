package Objects;

import Equipment.ProtectiveHelmet;
import Interfaces.IDangerousHazard;

/**
 * The IceSpikes class represents a hazardous ice spikes in the Frozen Lake Puzzle game.
 * It extends the Hazard class and implements the IDangerousHazard interface.
 * 
 * This class is used to create an ice spikes object that can be overcome by using a protective helmet.
 * It overrides the equals method to compare ice spikes objects and the showOnMap method to represent the ice spikes on the map.
 * 
 */

public class IceSpikes extends Hazard implements IDangerousHazard{

    // default constructor
    public IceSpikes(){
        this(-1);
    }

    // constructor with id
    public IceSpikes(int id){
        super(id);
        setOvercomeBy(new ProtectiveHelmet());
    }

    // copy constructor
    public IceSpikes(IceSpikes other){
        super(other);
    }

    // objects are equal if they have the same id and class
    @Override
    public boolean equals(Object other){
        return super.equals(other);
    }

    // show ice spikes on the map
    @Override
    public String showOnMap() {
        return "IS";
    }
}
