package Equipment;

import Objects.Experiment;

/**
 * The WindSpeedDetector class extends the ResearchEquipment class and represents a device 
 * used to measure wind speed. It includes functionality to generate a random wind speed 
 * value, report the wind speed, and display the device on a map.
 * 
 * This class provides constructors for creating a WindSpeedDetector with a specified ID, 
 * a default ID, or by copying another WindSpeedDetector's properties. It also provides 
 * methods to get the wind speed, report the wind speed, and show the device on a map.
 * 
 * The equals method is overridden to compare WindSpeedDetector objects based on their 
 * wind speed values in addition to their IDs.
 * 
 * The getExperiment method returns the type of experiment associated with this equipment, 
 * which is WIND_SPEED_MEASUREMENT.
 * 
 */
public class WindSpeedDetector extends ResearchEquipment {

    private int windSpeed;

    public WindSpeedDetector(int id) {
        super(id);
        this.windSpeed = (int) Math.round(Math.random() * 30); // Random wind speed between 0 and 30, rounded to the nearest integer
    }

    public WindSpeedDetector(){
        this(-1);
    }

    public WindSpeedDetector(WindSpeedDetector other){
        super(other);
        this.windSpeed = other.windSpeed;
    }


    public double getWindSpeed() {
        return windSpeed;
    }

    public String report() {
        return "Wind Speed: " + windSpeed + " m/s";
    }

    @Override
    public String showOnMap() {
        return "WS";
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)){
            WindSpeedDetector otherWindSpeedDetector = (WindSpeedDetector) other;
            return this.windSpeed == otherWindSpeedDetector.windSpeed;
        }
        else{
            return false;
        }
    }

    @Override
    public Experiment getExperiment() {
        return Experiment.WIND_SPEED_MEASUREMENT;
    }
}
