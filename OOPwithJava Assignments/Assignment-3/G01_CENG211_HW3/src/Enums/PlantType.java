package Enums;

import java.util.Locale;

public enum PlantType {
    FLOWER,TREE,BUSH;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH); // Returns "tree", "flower", or "bush"
    }

    public String toSymbol() {
        if (this == TREE) {
            return "t";
        } else if (this == FLOWER) {
            return "f";
        } else {
            return "u";
        }
    }
}
