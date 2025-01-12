package Objects;

import Bag.EquipmentBag;
import Equipment.Equipment;
import Exceptions.IncorrectBagContentsException;
import Exceptions.UnavailableEquipmentException;
import Interfaces.IMapPlaceable;

/**
 * The Researcher class represents a researcher in the Frozen Lake Puzzle game.
 * 
 * This class is used to create a researcher object that can carry research equipment.
 * It implements the IMapPlaceable interface to represent the researcher on the map.
 */

public class Researcher implements IMapPlaceable{

    // Equipment bag to store the researcher's equipment
    private EquipmentBag equipmentBag;

    // Unique identifier for the researcher
    private int id;

    // default constructor
    public Researcher(int id){
        equipmentBag = new EquipmentBag();
        this.id = id;
    }

    // constructor with id
    public Researcher(){
        this(-1);
    }

    // copy constructor
    public Researcher(Researcher other){
        this.equipmentBag = new EquipmentBag(other.equipmentBag);
        this.id = other.id;
    }

    // the method for the researcher to take equipment
    public boolean takeEquipment(Equipment equipment) throws IncorrectBagContentsException {
        return equipmentBag.add(equipment);
    }

    // the method for the researcher to use equipment
    // it returns the equipment that was used and removes it from the equipment bag
    public Equipment useEquipment(Equipment equipment){
        for(Equipment e :equipmentBag.toArray()){
            if (e.getClass().equals(equipment.getClass())){
                equipmentBag.remove(e);
                return e;}
        }
        return null;
    }

    // get the equipment bag as array 
    public Equipment[] getEquipmentBagArray(){
        return equipmentBag.toArray();
    }

    // checking method if the researcher is carrying research equipment
    public boolean carryingResearchEquipment() throws UnavailableEquipmentException {
        if(equipmentBag.hasResearchEquipment()){
            return true;
        }
        throw new UnavailableEquipmentException("*** Researcher currently doesn't have any Research Equipment.");
    }

    // get the researcher's id
    public int getId(){
        return id;
    }

    // to show the researcher on the map
    public String showOnMap(){
        return ("R"+id);
    }
}
