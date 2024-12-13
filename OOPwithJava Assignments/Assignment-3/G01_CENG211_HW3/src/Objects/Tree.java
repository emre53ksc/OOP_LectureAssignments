package Objects;

import Enums.PlantType;

public class Tree extends GardenPlant{
        
    public Tree(String id, String name, int areaOfPollenSpread) {
        super(PlantType.TREE, id, name, areaOfPollenSpread);

        // areaOfPollenSpread values which mentioned in the assignment
        if (areaOfPollenSpread < 3 || areaOfPollenSpread > 5) {
            throw new IllegalArgumentException("Area of pollen spread cannot be negative");
        }

        setEffectRanges(new int[]{areaOfPollenSpread, areaOfPollenSpread, 0, 0, 0, 0, 0, 0}); // Tree affects horizontally
    }

    // Clone method for return a new Tree object with the same attributes
    @Override
    public Tree clone(){
        Tree ret = new Tree(this.getId() , this.getName() , this.getAreaOfPollenSpread() );
        ret.addColors(this.getPlantColors());
        ret.addPollenTypes(this.getPlantPollenTypes());
        return ret;
    }

}
