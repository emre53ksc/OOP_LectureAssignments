package Objects;

import java.util.ArrayList;
import java.util.Locale;

import Enums.*;
import Garden.*;
import Interfaces.*;

public abstract class GardenPlant extends GardenObject  implements IColorablePollenable{

    private ArrayList<PlantType> pollenTypes; // e.g., "Flower", "Tree", "Bush"
    private ArrayList<Color> colors;       // Uses the Color enum
    private PlantType plantType; // Type of the plant (e.g., "Flower", "Tree", "Bush")
    private int areaOfPollenSpread; // Area of pollen spread
    private String name; // Name of the plant

    // Effect ranges: [0] = left, [1] = right, [2] = up, [3] = down, [4] = up-right, [5] = up-left, [6] = down-right, [7] = down-left
    private int[] effectRanges;

    // Constructor
    public GardenPlant(PlantType plantType, String id, String name, int areaOfPollenSpread) {
        super(id);
        this.plantType = plantType;
        this.name = name;
        this.areaOfPollenSpread = areaOfPollenSpread;
        this.effectRanges = new int[]{0, 0, 0, 0, 0, 0, 0, 0}; // Default effect ranges
        this.colors = new ArrayList<>(); // initialy empty
        this.pollenTypes = new ArrayList<>(); // initialize pollen types array, and add the plant type of its own
        this.pollenTypes.add(PlantType.valueOf(plantType.name())); 
    }
    

    // Getter for plant type
    public PlantType getPlantType() {
        return PlantType.valueOf(plantType.name());
    }

    public void setPlantType(PlantType plantType){
        this.plantType = plantType;
    }

    // Getter for area of pollen spread
    public int getAreaOfPollenSpread() {
        return areaOfPollenSpread;
    }

    public void setAreaOfPollenSpread(int areaOfPollenSpread){
        this.areaOfPollenSpread = areaOfPollenSpread;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int[] getEffectRanges() {
        return effectRanges.clone();
    }

    public void setEffectRanges(int[] effectRanges) {
        this.effectRanges = effectRanges;
    }

    public ArrayList<Color> getPlantColors() {
        return new ArrayList<>(colors);
    }

    public ArrayList<PlantType> getPlantPollenTypes() {
        return new ArrayList<>(pollenTypes);
    }

    // if Name, Id, Type and Area of pollen spread are same, then the two objects are equal
    @Override
    public boolean equals(Object gardenPlantObject){
        if (this == gardenPlantObject) {
            return true;
        }
        if (gardenPlantObject == null || getClass() != gardenPlantObject.getClass()){
            return false;
        }
        else {
            GardenPlant other = (GardenPlant) gardenPlantObject;
            return (this.getName().equals(other.getName()) && this.getId().equals(other.getId()) 
            && this.getPlantType().equals(other.getPlantType()) && this.getAreaOfPollenSpread() == other.getAreaOfPollenSpread());
        }      
    }
   

    // Match the given attribute with the query
    public boolean match(GardenPlantAttributes attribute, String query) {
        switch (attribute) {
            case TYPE:
                return this.plantType.equals(PlantType.valueOf(query.toUpperCase(Locale.ENGLISH)));
            case NAME:
                return this.name.equals(query.toLowerCase(Locale.ENGLISH));
            case AREA_OF_POLLEN_SPREAD:
                return this.areaOfPollenSpread == Integer.parseInt(query);
            case ID:
                return this.getId().equals(query);
            default:
                throw new IllegalArgumentException("Unknown attribute: " + attribute.toString());
        }
    }

    @Override
    public void addColor(Color color) {
        if (!(colors.contains(color))) { // Check if the color is already added
            colors.add(color);
        }
    }

    @Override
    public void addPollenType(PlantType pollenType) {
        if (!(pollenTypes.contains(pollenType))) { // Check if the pollen type is already added
            pollenTypes.add(pollenType);
        }
    }

    @Override
    public void addColors(ArrayList<Color> colors) {
        for (Color color : colors) {
            this.addColor(color);
        }
    }

    @Override
    public void addPollenTypes(ArrayList<PlantType> pollenTypes) {
        for (PlantType pollenType : pollenTypes) { 
            this.addPollenType(pollenType);
        }
    }

    // to string method for printing the object with its attributes
    @Override
    public String toString(){
        return
        "- Type: " + plantType + 
        ", Id: " + this.getId() + 
        ", Name: " + name + 
        ", Area of Reach: " + areaOfPollenSpread;
    }


    // general method for blooming the plant
    // it takes the affected squares and updates them
    // affected squares are determined by determineRange method and have directions in it
    public ArrayList<GardenSquare> bloom(ArrayList<ArrayList<GardenSquare>> affectedSquares) {
        ArrayList<GardenSquare> changedSquares = new ArrayList<>(); // List to store changed squares
    
        for (ArrayList<GardenSquare> direction : affectedSquares) { // Iterate over the directions
            // Iterate over the squares in the direction
            for (GardenSquare square : direction) { 
                Object obj = square.getObjectInSquare();
    
                // Check if the square contains an object
                if (obj != null) {
                    // If the object is ColorablePollenable
                    if (obj instanceof IColorablePollenable) {
                        IColorablePollenable colorablePollenable = (IColorablePollenable) obj;
                        colorablePollenable.addColors(this.getPlantColors());
                        colorablePollenable.addPollenTypes(this.getPlantPollenTypes());
                        // Set updated IColorablePollenable inside new square
                        square.setGardenPlaceable((IGardenPlaceable) colorablePollenable);
                        changedSquares.add(square); // Add to the changed list
                    }
                    // If the object is a GardenObject, stop spreading on that direction
                    if (obj instanceof GardenObject) {
                        break;
                    }
                } else if (square.isEmpty()) {
                    // If the square is empty, create a new PollenCloud
                    PollenCloud newPollenCloud = new PollenCloud();
                    newPollenCloud.addColors(this.getPlantColors());
                    newPollenCloud.addPollenTypes(this.getPlantPollenTypes());
                    square.setGardenPlaceable(newPollenCloud);
                    changedSquares.add(square); // Add to the changed list
                }
            }
        }
        return changedSquares; // Return only the changed squares
    }

    public ArrayList<ArrayList<GardenSquare>> determineRange(char row, int column) {
        // Define the boundaries of the garden
        char minRow = 'A';
        char maxRow = 'F';
        int minColumn = 1;
        int maxColumn = 8;

        // 2D array, list elements in list represent the affected directions
        // in these list have affected squares
        // 0 = left, 1 = right, 2 = up, 3 = down, 4 = up-right, 5 = up-left, 6 = down-right, 7 = down-left

        ArrayList<ArrayList<GardenSquare>> affectedSquares = new ArrayList<>();

        // Left direction
        ArrayList<GardenSquare> leftDirection = new ArrayList<>();
        for (int i = 1; i <= getEffectRanges()[0]; i++) {
            int newColumn = column - i;
            if (newColumn >= minColumn) {
                leftDirection.add(new GardenSquare(row, newColumn));
            } else {
                break;
            }
        }
        affectedSquares.add(leftDirection);

        // Right direction
        ArrayList<GardenSquare> rightDirection = new ArrayList<>();
        for (int i = 1; i <= getEffectRanges()[1]; i++) {
            int newColumn = column + i;
            if (newColumn <= maxColumn) {
                rightDirection.add(new GardenSquare(row, newColumn));
            } else {
                break;
            }
        }
        affectedSquares.add(rightDirection);

        // Up direction
        ArrayList<GardenSquare> upDirection = new ArrayList<>();
        for (int i = 1; i <= getEffectRanges()[2]; i++) {
            char newRow = (char) (row - i);
            if (newRow >= minRow) {
                upDirection.add(new GardenSquare(newRow, column));
            } else {
                break;
            }
        }
        affectedSquares.add(upDirection);

        // Down direction
        ArrayList<GardenSquare> downDirection = new ArrayList<>();
        for (int i = 1; i <= getEffectRanges()[3]; i++) {
            char newRow = (char) (row + i);
            if (newRow <= maxRow) {
                downDirection.add(new GardenSquare(newRow, column));
            } else {
                break;
            }
        }
        affectedSquares.add(downDirection);

        // Up-right direction
        ArrayList<GardenSquare> upRightDirection = new ArrayList<>();
        for (int i = 1; i <= getEffectRanges()[4]; i++) {
            char newRow = (char) (row - i);
            int newColumn = column + i;
            if (newRow >= minRow && newColumn <= maxColumn) {
                upRightDirection.add(new GardenSquare(newRow, newColumn));
            } else {
                break;
            }
        }
        affectedSquares.add(upRightDirection);

        // Up-left direction
        ArrayList<GardenSquare> upLeftDirection = new ArrayList<>();
        for (int i = 1; i <= getEffectRanges()[5]; i++) {
            char newRow = (char) (row - i);
            int newColumn = column - i;
            if (newRow >= minRow && newColumn >= minColumn) {
                upLeftDirection.add(new GardenSquare(newRow, newColumn));
            } else {
                break;
            }
        }
        affectedSquares.add(upLeftDirection);

        // Down-right direction
        ArrayList<GardenSquare> downRightDirection = new ArrayList<>();
        for (int i = 1; i <= getEffectRanges()[6]; i++) {
            char newRow = (char) (row + i);
            int newColumn = column + i;
            if (newRow <= maxRow && newColumn <= maxColumn) {
                downRightDirection.add(new GardenSquare(newRow, newColumn));
            } else {
                break;
            }
        }
        affectedSquares.add(downRightDirection);

        // Down-left direction
        ArrayList<GardenSquare> downLeftDirection = new ArrayList<>();
        for (int i = 1; i <= getEffectRanges()[7]; i++) {
            char newRow = (char) (row + i);
            int newColumn = column - i;
            if (newRow <= maxRow && newColumn >= minColumn) {
                downLeftDirection.add(new GardenSquare(newRow, newColumn));
            } else {
                break;
            }
        }
        affectedSquares.add(downLeftDirection);

        return affectedSquares;
    }
    
}