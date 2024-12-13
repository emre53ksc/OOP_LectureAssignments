package Objects;

import java.util.ArrayList;
import java.util.List;

import Enums.Color;
import Enums.PlantType;
import Interfaces.IColorablePollenable;
import Interfaces.IGardenPlaceable;

public class PollenCloud implements IColorablePollenable,IGardenPlaceable{
    private List<PlantType> pollenTypes; // e.g., "Flower", "Tree", "Bush"
    private List<Color> colors;       // Uses the Color enum

    // default constructor
    public PollenCloud() {
        this.pollenTypes = new ArrayList<>();
        this.colors = new ArrayList<>();
    }

    // full constructor
    public PollenCloud(List<PlantType> pollenTypes, List<Color> colors) {
        this.pollenTypes = new ArrayList<>(pollenTypes);
        this.colors = new ArrayList<>(colors);
    }

    // copy constructor
    public PollenCloud(PollenCloud other) {
        this.pollenTypes = other.getPollenTypes();
        this.colors = other.getColors();
    }

    // Adds a pollen type to the list
    @Override
    public void addPollenType(PlantType type) {
        if (!(pollenTypes.contains(type))) { // If the type is not already in the list
            pollenTypes.add(type);
        }
    }

    // Adds a color to the list
    @Override
    public void addColor(Color color) {
        if (!(colors.contains(color))) { // If the color is not already in the list
            colors.add(color);
        }
    }

    @Override
    public void addColors(ArrayList<Color> colors) {
        for (Color color : colors) {
            addColor(color);
        }
    }

    @Override
    public void addPollenTypes(ArrayList<PlantType> pollenTypes) {
        for (PlantType type : pollenTypes) {
            addPollenType(type);
        }
    }

    public ArrayList<PlantType> getPollenTypes() {
        return new ArrayList<>(pollenTypes);
    }

    public ArrayList<Color> getColors() {
        return new ArrayList<>(colors);
    }

    public boolean isEmpty() {
        return pollenTypes.isEmpty() && colors.isEmpty();
    }

    // Returns a string representation for map in square of the pollen cloud
    @Override
    public String toString() {
        char[] result = {'.', '.', '.', '.', '.', '.'};
        String pollenInitials = "ftu";
        String colorInitials = "...rgb";

        for (PlantType type : pollenTypes) {
            int index = pollenInitials.indexOf(type.toSymbol().charAt(0));
            if (index != -1) {
                result[index] = type.toSymbol().charAt(0);
            }
        }

        for (Color color : colors) {
            int index = colorInitials.indexOf(color.toSymbol().charAt(0));
            if (index != -1) {
                result[index] = color.toSymbol().charAt(0);
            }
        }

        return new String(result);
    }

    // if the pollen clouds have the exactly same pollen types and colors, they are equal
    @Override
    public boolean equals(Object pollenCloud){
        // Check if the current object is the same as the passed object
        if (this == pollenCloud) {
            return true;
        }
        // Check if the passed object is null or if the classes of the objects are different
        if (pollenCloud == null || getClass() != pollenCloud.getClass()){
            return false;
        }
        else {
            // Cast the passed object to PollenCloud
            PollenCloud other = (PollenCloud) pollenCloud;
            // Compare the pollen types and colors of both objects
            return this.pollenTypes.containsAll(other.pollenTypes) && other.pollenTypes.containsAll(this.pollenTypes) &&
                   this.colors.containsAll(other.colors) && other.colors.containsAll(this.colors);
        }      
    }
}
