package Equipment;

import Objects.Experiment;

/**
 * The ChiselingEquipment class extends the ResearchEquipment class and represents
 * a specific type of equipment used for chiseling ice blocks in a research context.
 * 
 * This class provides constructors for creating chiseling equipment with a specified ID,
 * a default ID, or by copying another chiseling equipment's properties. It also includes
 * methods to get the weight of the ice block, generate a report of the equipment, and 
 * display the equipment on a map.
 * 
 * The equals method is overridden to compare chiseling equipment objects based on their
 * ice block weight and inherited properties.
 * 
 * The getExperiment method returns the specific experiment type associated with this equipment.
 * 
 * 
 */
public class ChiselingEquipment extends ResearchEquipment{

    private double iceBlockWeight;

    public ChiselingEquipment(int id) {
        super(id);
        this.iceBlockWeight = (Math.random() * 19) + 1 ; // Random ice block weight between 1 and 20
    }

    public ChiselingEquipment(){
        this(-1);
    }

    public ChiselingEquipment(ChiselingEquipment other){
        super(other);
        this.iceBlockWeight = other.iceBlockWeight;
    }

    public double getIceBlockWeight() {
        return iceBlockWeight;
    }

    public String report() {
        return String.format("Ice Block Weight: %.2f g", iceBlockWeight);
    }

    @Override
    public String showOnMap() {
        return "CH";
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)){
            ChiselingEquipment otherChiselingEquipment = (ChiselingEquipment) other;
            return this.iceBlockWeight == otherChiselingEquipment.iceBlockWeight;
        }
        else{
            return false;
        }
    }

    @Override
    public Experiment getExperiment() {
        return Experiment.GLACIAL_SAMPLING;
    }
}
