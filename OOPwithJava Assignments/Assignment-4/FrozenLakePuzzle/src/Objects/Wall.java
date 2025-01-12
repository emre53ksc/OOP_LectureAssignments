package Objects;

import Interfaces.IMapPlaceable;

/**
 * The Wall class represents a wall in the Frozen Lake Puzzle game.
 * 
 * This class is used to create a wall object that can be placed on the map.
 * It implements the IMapPlaceable interface to represent the wall on the map.
 */

public class Wall implements IMapPlaceable{

    int id;

    public Wall(int id) {
        this.id = id;
    }

    public Wall(){
        this.id = 0;
    }

    public Wall(Wall other){
        this(other.id);
    }

    @Override
    public boolean equals(Object other){
        return super.equals(other);
    }


    // wall is represented by an empty string on the map
    @Override
    public String showOnMap() {
        return ""; 
    }

}
