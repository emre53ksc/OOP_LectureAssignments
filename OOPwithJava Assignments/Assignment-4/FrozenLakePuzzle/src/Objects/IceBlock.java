package Objects;

/*
 * The IceBlock class represents a hazardous ice block in the Frozen Lake Puzzle game.
 * It extends the Hazard class.
 * Its main purpose is stop the player when sliding on the ice.
 */

public class IceBlock extends Hazard{
    
    // default constructor
    public IceBlock(){
        this(-1);
    }

    // constructor with id
    public IceBlock(int id){
        super(id);
    }

    // copy constructor
    public IceBlock(IceBlock other){
        super(other);
    }

    // objects are equal if they have the same id and class
    @Override
    public boolean equals(Object other){
        return super.equals(other);
    }

    // show ice block on the map
    @Override
    public String showOnMap() {
        return "IB";
    }
}
