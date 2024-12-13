package Objects;

import Enums.*;
import Garden.*;
import Interfaces.*;
import java.util.ArrayList;
import java.util.Locale;

// Abstract class representing a light source in the garden
public abstract class LightSource extends GardenObject {

    private Color color; // The color of the light
    private int areaOfLightReach; // The area the light reaches
    private LightType lightType; // Type of light source

    private int[] effectRanges;

    // Constructor for LightSource
    public LightSource(LightType lightType, String id, Color color, int areaOfLightReach) {
        super(id);
        this.color = color;
        this.areaOfLightReach = areaOfLightReach;
        this.lightType = lightType;
        this.effectRanges = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 }; // Default effect ranges
    }

    public Color getColor() {
        return Color.valueOf(color.name());
    }

    public void setColor(Color color){
        this.setColor(color);
    }

    public int getAreaOfLightReach() {
        return areaOfLightReach;
    }
    
    public void setAreaOfLightReach(int areaOfLightReach) {
        this.setAreaOfLightReach(areaOfLightReach);
    }

    public LightType getLightType() {
        return LightType.valueOf(lightType.name());
    }

    public void setLightType(LightType lightType){
        this.setLightType(lightType);
    }

    public int[] getEffectRanges() {
        return effectRanges.clone();
    }

    public void setEffectRanges(int[] effectRanges) {
        this.effectRanges = effectRanges;
    }

    // if color, id, light type and area of light reach are the same, then the light sources are equal
    public boolean equals(Object lightSource){
        // Check if the current object is the same as the passed object
        if (this == lightSource) {
            return true;
        }
        // Check if the passed object is null or if the classes of the objects are different
        if (lightSource == null || getClass() != lightSource.getClass()){
            return false;
        }
        else {
            // Cast the passed object to LightSource
            LightSource other = (LightSource) lightSource;
            // Compare the color, id, and light type of both objects
            return (this.getColor().equals(other.getColor()) && this.getId().equals(other.getId()) 
            && this.getLightType().equals(other.getLightType()) && this.getAreaOfLightReach() == other.getAreaOfLightReach());
        }      
    }
   

    // Method to match the light source with the given attribute and query
    public boolean match(LightSourceAttributes attribute, String query) {
        // Switch based on the attribute to match
        switch (attribute) {
            case TYPE:
                // Check if the light type matches
                return this.lightType == LightType.valueOf(query.toUpperCase(Locale.ENGLISH).replace(" ", "_"));
            case COLOR:
                // Check if the color matches
                return this.color.equals(Color.valueOf(query.toUpperCase(Locale.ENGLISH)));
            case AREA_OF_LIGHT_REACH:
                // Check if the area of light reach matches
                return this.areaOfLightReach == Integer.parseInt(query);
            case ID:
                // Check if the ID matches
                return this.getId().equals(query);
            default:
                // Throw an exception for unknown attributes
                throw new IllegalArgumentException("Unknown attribute: " + attribute);
        }
    }

    // toString method to show the light source with its' attributes
    @Override
    public String toString() {
        return "- Type: " + lightType +
                ", Id: " + getId() +
                ", Color: " + color +
                ", Area of Reach: " + areaOfLightReach;
    }

    // General method to light up the affected squares
    public ArrayList<GardenSquare> lightUp(ArrayList<ArrayList<GardenSquare>> affectedSquares) {
        // List to store changed squares
        ArrayList<GardenSquare> changedSquares = new ArrayList<>();
    
        // Iterate through each direction of affected squares
        for (ArrayList<GardenSquare> direction : affectedSquares) {
            // Iterate through each square in the direction
            for (GardenSquare square : direction) {
                // Get the object in the current square
                Object obj = square.getObjectInSquare();
    
                // Check if the square contains an object
                if (obj != null) {
                    // If the object is ColorablePollenable
                    if (obj instanceof IColorablePollenable) {
                        IColorablePollenable colorablePollenable = (IColorablePollenable) obj;
                        // Add the light's color to the object
                        colorablePollenable.addColor(this.getColor());
                        // Set updated IColorablePollenable inside new square
                        square.setGardenPlaceable((IGardenPlaceable) colorablePollenable);
                        // Add the square to the changed list
                        changedSquares.add(square);
                    }
                    // If the object is a GardenObject, stop the light
                    if (obj instanceof GardenObject) {
                        break;
                    }
                } else if (square.isEmpty()) {
                    // If the square is empty, create a new PollenCloud
                    PollenCloud newPollenCloud = new PollenCloud();
                    // Add the light's color to the PollenCloud
                    newPollenCloud.addColor(this.getColor());
                    // Set the PollenCloud in the square
                    square.setGardenPlaceable(newPollenCloud);
                    // Add the square to the changed list
                    changedSquares.add(square);
                }
            }
        }
        // Return only the changed squares
        return changedSquares;
    }
    public ArrayList<ArrayList<GardenSquare>> determineRange(char row, int column) {
        // Define the boundaries of the garden
        char minRow = 'A';
        char maxRow = 'F';
        int minColumn = 1;
        int maxColumn = 8;

        // 2D array, list elements şn list represent the affected directions
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
