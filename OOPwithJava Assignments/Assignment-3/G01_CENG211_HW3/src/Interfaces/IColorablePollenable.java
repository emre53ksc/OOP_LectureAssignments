/**
 * This interface represents an entity that can be both colored and pollenated.
 * Implementing classes should provide the logic for the colorAndPollenate method.
 */
package Interfaces;

import Enums.*;
import java.util.ArrayList;

public interface IColorablePollenable {
    public void addPollenType(PlantType pollen);
    public void addColor(Color color);
    public void addColors(ArrayList<Color> colors);
    public void addPollenTypes(ArrayList<PlantType> pollenTypes);
}
