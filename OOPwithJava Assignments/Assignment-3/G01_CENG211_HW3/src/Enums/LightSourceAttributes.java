package Enums;

import java.util.Locale;

public enum LightSourceAttributes {
    TYPE, ID, COLOR, AREA_OF_LIGHT_REACH;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH).replace("_", " ");
    }
}
