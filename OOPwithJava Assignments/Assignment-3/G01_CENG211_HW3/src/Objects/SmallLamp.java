package Objects;

import Enums.Color;
import Enums.LightType;

public class SmallLamp extends LightSource {

    public SmallLamp(String id, Color color, int areaOfLightReach) {
        super(LightType.SMALL_LAMP, id, color, areaOfLightReach);

        // areaOfLightReach values which mentioned in the assignment
        if (areaOfLightReach < 1 || areaOfLightReach > 3) {
            throw new IllegalArgumentException("Spotlight area of light reach must be between 1 and 3");
        }

        setEffectRanges(new int[] { 0, areaOfLightReach, 0, 0, 0, 0, 0, 0 }); // SmallLamp only affects the right side
    }

    // Default constructor
    public SmallLamp() {
        this("0", Color.values()[0], 1);
    }

    // Copy constructor
    public SmallLamp(SmallLamp other) {
        this(other.getId(), other.getColor(), other.getAreaOfLightReach());
    }

    public SmallLamp clone() {
        return new SmallLamp(this);
    }
}
