package Bag;

import Equipment.*;
import Exceptions.*;

import java.util.HashSet;
import java.util.Set;

/**
 * The EquipmentBag class represents a bag that can store equipment.
 * 
 * This class is used to create a bag object that can store up to 3 equipment items.
 * It contains methods to add, remove, check for the presence of, and get the equipment in the bag.
 */

public class EquipmentBag{

    // using Set data structure to store the equipment
    Set<Equipment> set;

    // default constructor
    public EquipmentBag() {
        set = new HashSet<Equipment>();
    }

    // copy constructor
    public EquipmentBag(EquipmentBag other) {
        set = new HashSet<Equipment>();
        for (Equipment e : other.toArray()) {
            set.add(e);
        }
    }

    // method to add equipment to the bag
    // it throws an exception if the bag already contains a different type of equipment or if the bag is full (3 items)
    // it returns true if the equipment was added successfully
    public boolean add(Equipment newEntry) throws IncorrectBagContentsException {
        Equipment item = (set.size() != 0) ? set.toArray(new Equipment[0])[0] : null;
        if ((item instanceof ResearchEquipment && newEntry instanceof HazardEquipment) || (item instanceof HazardEquipment && newEntry instanceof ResearchEquipment)){
            throw new IncorrectBagContentsException("Researchers cannot carry different types of equipment in their bags");
        }
        if (set.size() < 3) {
            return set.add(newEntry);
        } else {
            return false;
        }
    }

    // method to check if the bag is empty
	public boolean isEmpty() {
		return set.isEmpty();
	}

    // method to check if the bag is full (3 items max)
	public boolean isFull() {
		return set.size()==3;
	}

    // method to check if the bag contains research equipment
    public boolean hasResearchEquipment(){
        return (((set.size() != 0) ? set.toArray(new Equipment[0])[0] : null) instanceof ResearchEquipment);
    }

    // method to remove equipment from the bag
    // it returns true if the equipment was removed successfully
	public boolean remove(Equipment anEntry) {
        return set.remove(anEntry);
    }

    // method to check if the bag contains a specific equipment
	public boolean contains(Equipment anEntry) {
		return set.contains(anEntry);
	}

    // method to get the equipment in the bag as an array
    public Equipment[] toArray() {
        return set.toArray(new Equipment[0]);
    }
}
