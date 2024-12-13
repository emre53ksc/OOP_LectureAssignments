package Enums;

import java.util.Locale;

public enum GardenObjectType {
    GARDEN_PLANT, LIGHT_SOURCE, STATUE;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH).replace("_", " ");
    }
}
