package Objects;

// Enum class to represent the shorthand names of the equipments

public enum ShortHand {
    TD, WS, CM, CH, CL, WB, PH, NO;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
