package Objects;

import Enums.PlantType;

public class Flower extends GardenPlant{

    public Flower(String id, String name, int areaOfPollenSpread) {
        super(PlantType.FLOWER, id, name, areaOfPollenSpread);

        // areaOfPollenSpread values which mentioned in the assignment
        if (areaOfPollenSpread < 2 || areaOfPollenSpread > 4) { 
            throw new IllegalArgumentException("Area of pollen spread cannot be negative");
        }

        setEffectRanges(new int[]{0, 0, 0, 0, areaOfPollenSpread, areaOfPollenSpread, areaOfPollenSpread, areaOfPollenSpread}); // Flower affects diagonally

    }

    // Clone method for return a new Flower object with the same attributes
    @Override
    public Flower clone(){
        Flower ret = new Flower(this.getId() , this.getName() , this.getAreaOfPollenSpread() );
        ret.addColors(this.getPlantColors());
        ret.addPollenTypes(getPlantPollenTypes());
        return ret;
    }
    
}
