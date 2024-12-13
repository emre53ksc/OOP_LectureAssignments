package Objects;


import Enums.PlantType;

public class Bush extends GardenPlant{

    public Bush(String id, String name, int areaOfPollenSpread) {
        super(PlantType.BUSH, id, name, areaOfPollenSpread);
        
        // areaOfPollenSpread values which mentioned in the assignment
        if (areaOfPollenSpread < 1 || areaOfPollenSpread > 2) {
            throw new IllegalArgumentException("Area of pollen spread cannot be negative");
        }

        setEffectRanges(new int[]{0, 0, areaOfPollenSpread, areaOfPollenSpread, 0, 0, 0, 0}); // Bush affects vertically
    }

    // Clone method for return a new Bush object with the same attributes
    @Override
    public Bush clone() {
        Bush ret = new Bush(this.getId(), this.getName(), this.getAreaOfPollenSpread());
        ret.addColors(this.getPlantColors());
        ret.addPollenTypes(this.getPlantPollenTypes());
        return ret;
    }
   
    

    

}
