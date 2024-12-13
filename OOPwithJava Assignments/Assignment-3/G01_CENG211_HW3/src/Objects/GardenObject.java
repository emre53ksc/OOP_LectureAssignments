package Objects;

import Interfaces.IGardenPlaceable;
import Interfaces.ISearchable;

public abstract class GardenObject implements ISearchable,IGardenPlaceable   {

    private String id; // Unique identifier for the garden object

    public GardenObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(int id){
        this.setId(id);
    }

    // method for showing the object on the map
    // that returns the id of the object with padding spaces to center it in square
    public String ShowOnMap() {
        int totalLength = 6;
        int idLength = id.length();
        int padding = (totalLength - idLength) / 2;

        StringBuilder paddedId = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            paddedId.append(' ');
        }
        paddedId.append(id);
        while (paddedId.length() < totalLength) {
            paddedId.append(' ');
        }

        return paddedId.toString();
    }

    // method for copying the object
    // that behaves differently for each object type
    public GardenObject copy(){
        if(this instanceof LightSource){
            if(this instanceof SmallLamp){
                return (GardenObject)((SmallLamp)this).clone();
            }
            else if(this instanceof LargeLamp){
                return (GardenObject)((LargeLamp)this).clone();
            }
            else if(this instanceof Spotlight){
                return (GardenObject)((Spotlight)this).clone();
            }
        }
        else if(this instanceof GardenPlant){
            if(this instanceof Flower){
                return (GardenObject)((Flower)this).clone();
            }
            else if(this instanceof Tree){
                return (GardenObject)((Tree)this).clone();
            }
            else if(this instanceof Bush){
                return (GardenObject)((Bush)this).clone();
            }
        } 
        else if(this instanceof Statue){
            return (GardenObject)((Statue)this).clone();
        }
        return null;
    }


}