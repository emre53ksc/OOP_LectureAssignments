package Enums;

import java.util.Locale;

public enum LightType {
    SMALL_LAMP, LARGE_LAMP, SPOTLIGHT;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH).replace("_", " ");
    }
}

