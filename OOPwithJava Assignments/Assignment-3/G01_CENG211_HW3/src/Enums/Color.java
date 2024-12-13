package Enums;

import java.util.Locale;

public enum Color {
    RED, GREEN, BLUE;

    @Override
    public String toString() {
        return String.valueOf(name().toLowerCase(Locale.ENGLISH)); // Returns "r", "g", or "b"
    }

    public String toSymbol() {
        return String.valueOf(name().toLowerCase(Locale.ENGLISH).charAt(0)); // Returns "r", "g", or "b"
    }

}
