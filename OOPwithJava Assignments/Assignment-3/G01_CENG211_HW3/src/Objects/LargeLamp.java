package Objects;

import Enums.Color;
import Enums.LightType;

public class LargeLamp extends LightSource{
        
        public LargeLamp(String id, Color color, int areaOfLightReach) {
        super(LightType.LARGE_LAMP, id, color, areaOfLightReach);

        // areaOfLightReach values which mentioned in the assignment
        if (areaOfLightReach < 3 || areaOfLightReach > 4) {
            throw new IllegalArgumentException("Spotlight area of light reach must be between 1 and 3");
        }

        setEffectRanges(new int[]{areaOfLightReach, 0, 0, 0, 0, 0, 0, 0}); // LargeLamp only affects the left side
    }

    
    // Default constructor
    public LargeLamp() {
        this("", Color.values()[0], 0); 
    }
    // Copy constructor
    public LargeLamp(LargeLamp other) {
        this(other.getId(), other.getColor(), other.getAreaOfLightReach());
    }
    
    public LargeLamp clone(){
        return new LargeLamp(this);
    }
}
