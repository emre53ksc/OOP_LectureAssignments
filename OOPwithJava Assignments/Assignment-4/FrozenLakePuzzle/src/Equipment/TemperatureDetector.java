package Equipment;

import Objects.Experiment;

/**
 * The TemperatureDetector class extends the ResearchEquipment class and represents
 * a temperature detecting equipment item with a unique identifier and a temperature
 * value in degrees Celsius ranging from -30 to 0.
 * 
 * This class provides constructors for creating a TemperatureDetector with a specified ID,
 * a default ID, or by copying another TemperatureDetector's properties. It also provides
 * methods to get the temperature, generate a report, show the equipment on a map, and 
 * compare TemperatureDetector objects based on their properties.
 * 
 * The getExperiment method returns the type of experiment associated with this equipment.
 * 
 */
public class TemperatureDetector extends ResearchEquipment{

    private int temperature; // temperature in degrees Celsius -30 to 0

    public TemperatureDetector(int id) {
        super(id);
        this.temperature = (int) (Math.random() * 31) - 30; // random temperature between -30 and 0
    }

    public TemperatureDetector(){
        this(-1);
    }

    public TemperatureDetector(TemperatureDetector other){
        super(other);
        this.temperature = other.temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    @Override
    public String report() {
        return "Temperature Detector: " + temperature + " degrees Celsius";
    }

    @Override
    public String showOnMap() {
        return "TD";
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)){
            TemperatureDetector otherTemperatureDetector = (TemperatureDetector) other;
            return this.temperature == otherTemperatureDetector.temperature;
        }
        else{
            return false;
        }
    }

    @Override
    public Experiment getExperiment() {
        return Experiment.TEMPERATURE_MEASUREMENT;
    }
}
