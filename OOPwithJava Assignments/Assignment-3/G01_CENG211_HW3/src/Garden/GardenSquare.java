package Garden;

import Interfaces.IGardenPlaceable;
import Objects.*;


/**
 * The GardenSquare class represents a square in a garden grid.
 * Each square can hold either a GardenObject or a PollenCloud, but not both.
 * It also has a row and column to specify its position and a flag to indicate if it is a target square.
 */
public class GardenSquare {
    private char row; // A-F
    private int col;  // 1-8
    private GardenObject gardenObject; // The object placed in this square (if any)
    private PollenCloud pollenCloud;   // Represents pollens and their colors
    private boolean isTarget;

    /**
     * Constructs a GardenSquare object with the specified row and column.
     * Initializes the gardenObject and pollenCloud to null, and sets isTarget to false.
     *
     * @param row the row character of the garden square
     * @param col the column number of the garden square
     */
    public GardenSquare(char row, int col) {
        this.row = row;
        this.col = col;
        this.gardenObject = null; // Initially empty
        this.pollenCloud = null;  // Initially empty
        isTarget = false; // initially false
    }

    /**
     * Constructs a GardenSquare object with default values.
     * The default row is set to 'A', the default column is set to 1,
     * and both gardenObject and pollenCloud are initialized to null.
     * The isTarget flag is set to false by default.
     */
    public GardenSquare() {
        this.row = 'A'; // Default row
        this.col = 1;   // Default column
        this.gardenObject = null; // Initially empty
        this.pollenCloud = null;  // Initially empty
        isTarget = false; // initially false
    }

    /**
     * Constructs a new GardenSquare by copying the properties of another GardenSquare.
     *
     * @param other the GardenSquare to copy from
     */
    public GardenSquare(GardenSquare other) {
        this.row = other.row;
        this.col = other.col;
        this.gardenObject = other.gardenObject != null ? (other.gardenObject).copy() : null;
        this.pollenCloud = other.pollenCloud != null ? new PollenCloud(other.pollenCloud) : null;
        this.isTarget = other.isTarget;
    }

    /**
     * Retrieves the row character of the garden square.
     */
    public char getRow() {
        return row;
    }

    /**
     * Returns the column index of the garden square.
     */
    public int getCol() {
        return col;
    }

    /**
     * Checks if the current garden square is the target.
     */
    public boolean isTarget(){
        return isTarget;
    }

    /**
     * Sets the target status of the garden square.
     */
    public void setTarget(boolean isTarget){
        this.isTarget = isTarget;
    }
    
    /**
     * Retrieves a copy of the object present in the garden square.
     * 
     * @return a copy of the garden object if it exists, a copy of the PollenCloud
     *         if the pollen cloud exists, or null if neither is present.
     */
    public Object getObjectInSquare() {
        if (gardenObject != null) {
            return gardenObject.copy();
        } else if (pollenCloud != null) {
            return new PollenCloud(pollenCloud);
        } else {
            return null;
        }
    }

    /**
     * Displays the current state of the garden square on the map.
     * 
     * @return A string representation of the garden object if it exists,
     *         otherwise a string representation of the pollen cloud if it exists,
     *         or null if neither exists.
     */
    public String showOnMap() {
        if (gardenObject != null) {
            return gardenObject.ShowOnMap();
        } else if (pollenCloud != null) {
            return pollenCloud.toString();
        } else {
            return null;
        }
    }

    /**
     * Sets the given garden placeable object in the garden square.
     * 
     * @param obj the garden placeable object to be set, which can be an instance of GardenObject or PollenCloud
     * @return true if the object was successfully set, false otherwise
     * @throws IllegalArgumentException if the object is not a known garden placeable type
     */
    public boolean setGardenPlaceable(IGardenPlaceable obj){
        if(obj instanceof GardenObject){
            return setGardenObject((GardenObject)obj);
        }
        else if(obj instanceof PollenCloud){
            return setPollenCloud((PollenCloud)obj);
        }
        else{
            System.out.println("Unknown Garden Placeable");
            System.exit(-1);
        }
        return false;
    }
   
    private boolean setGardenObject(GardenObject gardenObject) {
        if (this.pollenCloud != null) {
            return false; // Cannot place if there's an object of other type, also it doesn't make sense
        }
        this.gardenObject = gardenObject;
        return true;
    }

    private boolean setPollenCloud(PollenCloud pollenCloud) {
        if (this.gardenObject != null) {
            return false; // Cannot place if there's an object of other type, also it doesn't make sense
        }
        this.pollenCloud = pollenCloud;
        return true;
    }


    /**
     * Checks if the garden square is empty.
     */
    public boolean isEmpty() {
        return gardenObject == null && pollenCloud == null;
    }

    /**
     * Returns a string representation of the GardenSquare object.
     * The string representation depends on the state of the object:
     * - If gardenObject is not null, returns the ID of the gardenObject.
     * - If pollenCloud is not null, returns the string representation of the pollenCloud.
     * - If the object is a target, returns the string "Target".
     * 
     * @return A string representation of the GardenSquare object.
     */
    @Override
    public String toString() {
        String result = "";
        if (gardenObject != null) {
            result += gardenObject.getId();
        } else if (pollenCloud != null) {
            result += pollenCloud.toString();
        } else if(isTarget){
            result += "Target";
        }
        return result;
    }

    /**
     * Compares this GardenSquare object to the specified object.
     * The result is true if and only if the argument is not null and is a GardenSquare object
     * that has the same row, column, garden object, and pollen cloud as this object.
     *
     * @param square the object to compare this GardenSquare against
     * @return true if the given object represents a GardenSquare equivalent to this GardenSquare, false otherwise
     */
    public boolean equals(Object square){
        // Check if the current object is the same as the passed object
        if (this == square) {
            return true;
        }
        // Check if the passed object is null or if the classes of the objects are different
        if (square == null || getClass() != square.getClass()){
            return false;
        }
        else {
            // Cast the passed object to GardenSquare
            GardenSquare other = (GardenSquare) square;
            // Compare the row, column, garden object, and pollen cloud of both objects
            if (!(this.getRow() == other.getRow() && this.getCol() == other.getCol())) {
                return false;
            }
            if(this.gardenObject == null && other.gardenObject == null && this.pollenCloud == null && other.pollenCloud == null){
                return true;
            }
            if(this.gardenObject != null && other.gardenObject != null){
                return this.gardenObject.copy().equals(other.gardenObject);
            }
            if(this.pollenCloud != null && other.pollenCloud != null){
                return this.pollenCloud.equals(other.pollenCloud);
            }
            return false;
            
        }      
    }

}
