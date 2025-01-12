package Lake;

import Interfaces.IMapPlaceable;
import Objects.Researcher;
import java.util.ArrayList;
import java.util.List;

/**
 * The LakeSquare class represents a square in the lake puzzle.
 * 
 * This class is used to create a square object that can be placed on the lake map.
 * It contains a researcher and a map item that can be placed on the square.
 */

public class LakeSquare {

    // The researcher that is placed on the square
    private Researcher researcher;
    // The object that is placed on the square
    private IMapPlaceable MapItem;

    // default constructor
    public LakeSquare(){
        this(null, null);
    }

    // copy constructor
    public LakeSquare(LakeSquare other){
        this.researcher = other.researcher;
        this.MapItem = other.MapItem;
    }

    // constructor with researcher and map item
    public LakeSquare(Researcher researcher, IMapPlaceable MapItem){
        this.researcher = researcher;
        this.MapItem = MapItem;
    }

    // Getters and Setters
    public Researcher getResearcher() {
        return new Researcher(researcher);
    }

    public void removeResearcher() {
        this.researcher = null;
    }

    // priority object is the researcher if it is present, otherwise the map item
    public IMapPlaceable getPriorityObject() {
        return (researcher == null) ? MapItem : researcher;
    }

    public void setObject(IMapPlaceable MapItem) {
        if (MapItem instanceof Researcher){
            this.researcher = (Researcher) MapItem;
        }
        else {
            this.MapItem = MapItem;
        }
    }

    // get all objects on the square
    public List<IMapPlaceable> getAllObjects() {
        List<IMapPlaceable> objects = new ArrayList<IMapPlaceable>();
        if (researcher != null) objects.add(researcher);
        if (MapItem != null) objects.add(MapItem);
        return objects;
    }

    // objects are equal if they have the same researcher and map item
    public boolean equals(Object other){
        if (other == null) return false;
        if (this == other) return true;
        if (this.getClass() != other.getClass()) return false;
        LakeSquare otherSquare = (LakeSquare) other;
        return this.researcher.equals(otherSquare.researcher) && this.MapItem.equals(otherSquare.MapItem);
    }

    // toString method for the LakeSquare
    @Override
    public String toString(){
        return ((researcher == null) ? ""    :   "R"+researcher.getId()  ) +(((researcher != null)&&(MapItem != null))? "-":"")+ ((MapItem == null) ? ""    :   MapItem.showOnMap() );
    }

}
