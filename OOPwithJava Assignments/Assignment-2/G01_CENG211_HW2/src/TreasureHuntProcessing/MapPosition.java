package TreasureHuntProcessing;

public class MapPosition {
    // Instance variables
    private int x;
    private int y;
    private boolean occupied;

    // Constructor with parameters to initialize the MapPosition with specific values
    public MapPosition(int x, int y, boolean occupied) {
        this.x = x;
        this.y = y;
        this.occupied = occupied;
    }

    // Default constructor initializing MapPosition with default values
    public MapPosition() {
        this(0, 0, false);
    }

    // Copy constructor to create a new MapPosition object from an existing one
    public MapPosition(MapPosition original) {
        this(original.x, original.y, original.occupied);
    }

    // Constructor to initialize MapPosition with coordinates only
    public MapPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.occupied = false;
    }

    // Getter for x coordinate
    public int getX() {
        return x;
    }

    // Setter for x coordinate
    public void setX(int x) {
        this.x = x;
    }

    // Getter for y coordinate
    public int getY() {
        return y;
    }

    // Setter for y coordinate
    public void setY(int y) {
        this.y = y;
    }

    // Getter for occupied status
    public boolean isOccupied() {
        return occupied;
    }

    // Setter for occupied status
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    // Overridden toString method to provide a string representation of the MapPosition object
    public String toString(){
        String result="";
        result+=" ("+x+","+y+") ";
        return result;
    }



}
