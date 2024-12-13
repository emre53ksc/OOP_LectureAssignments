package Objects;

import Enums.Color;
import Enums.LightType;

public class Spotlight extends LightSource{
    
    public Spotlight(String id, Color color, int areaOfLightReach) {
        super(LightType.SPOTLIGHT, id, color, areaOfLightReach);

        // areaOfLightReach values which mentioned in the assignment
        if (areaOfLightReach < 4 || areaOfLightReach > 5) {
            throw new IllegalArgumentException("Spotlight area of light reach must be between 1 and 3");
        }

        setEffectRanges(new int[]{0, 0, 0, areaOfLightReach, 0, 0, 0, 0}); // Spotlight only affects the down side
    }

    
    // Default constructor
    public Spotlight() {
        this("", Color.values()[0], 1); 
    }

    // Copy constructor
    public Spotlight(Spotlight other) {
        this(other.getId(), other.getColor(), other.getAreaOfLightReach());
    }
    
    public Spotlight clone(){
        return new Spotlight(this);
    }

    

}
