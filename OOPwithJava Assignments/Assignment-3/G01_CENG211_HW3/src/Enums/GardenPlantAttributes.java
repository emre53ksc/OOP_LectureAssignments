package Enums;

import java.util.Locale;

public enum GardenPlantAttributes {
    TYPE, NAME, ID, AREA_OF_POLLEN_SPREAD;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH).replace("_", " ");
    }
}
