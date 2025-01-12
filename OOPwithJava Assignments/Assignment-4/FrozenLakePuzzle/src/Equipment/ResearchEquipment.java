package Equipment;

import Objects.Experiment;

/**
 * The ResearchEquipment class is an abstract class that extends the Equipment class.
 * It represents a specific type of equipment used for research purposes.
 * 
 * This class provides constructors for creating research equipment with a specified ID,
 * a default ID, or by copying another ResearchEquipment object. It also includes an 
 * abstract method to generate a report and another abstract method to get the associated 
 * experiment.
 * 
 * The equals method is overridden to compare ResearchEquipment objects based on their IDs.
 * 
 * The getExperiment method is an abstract method that should be implemented to return 
 * the Experiment associated with this ResearchEquipment.
 * 
 */
public abstract class ResearchEquipment extends Equipment{


    public ResearchEquipment(int id) {
        super(id);

    }

    public ResearchEquipment(){
        this(-1);
    }

    public ResearchEquipment(ResearchEquipment other){
        super(other);
    }

    public abstract String report();

    @Override
    public boolean equals(Object other){
        return super.equals(other);
    }

    public abstract Experiment getExperiment();

}
