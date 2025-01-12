package Equipment;

/**
 * The LargeWoodenBoard class represents a type of hazard equipment in the Frozen Lake Puzzle game.
 * It extends the HazardEquipment class and provides specific implementations for the large wooden board.
 * 
 * Constructors:
 * - LargeWoodenBoard(): Initializes a new instance of the LargeWoodenBoard class with default values.
 * - LargeWoodenBoard(int id): Initializes a new instance of the LargeWoodenBoard class with the specified ID.
 * - LargeWoodenBoard(ProtectiveHelmet other): Initializes a new instance of the LargeWoodenBoard class by copying the properties of another ProtectiveHelmet object.
 * 
 * Methods:
 * - boolean equals(Object other): Checks if this LargeWoodenBoard is equal to another object.
 * - String showOnMap(): Returns a string representation ("WB") of the LargeWoodenBoard to be shown on the map.
 */
public class LargeWoodenBoard extends HazardEquipment{

    public LargeWoodenBoard() {
        super();
    }

    public LargeWoodenBoard(int id) {
        super(id);
    }

    public LargeWoodenBoard(ProtectiveHelmet other){
        super(other);
    }

    public boolean equals(Object other){
        return super.equals(other);
    }

    @Override
    public String showOnMap() {
        return "WB";
    }


}
