package Objects;

import java.util.Locale;

// This enum represents different types of experiments that can be conducted in a frozen lake puzzle.
// The experiments include temperature measurement, wind speed measurement, camera placement, and glacial sampling.
// The toString method is overridden to provide a more readable string representation of the enum constants.
// It converts the enum name to lowercase, splits it by underscores, capitalizes the first letter of each word,
// and joins them with spaces to form a human-readable string.

public enum Experiment {
    TEMPERATURE_MEASUREMENT, WIND_SPEED_MEASUREMENT, CAMERA_PLACEMENT, GLACIAL_SAMPLING;

    @Override
    public String toString() {
        String name = name().toLowerCase(Locale.ENGLISH);
        String[] words = name.split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1))
              .append(" ");
        }
        return result.toString().trim();
    }
}
