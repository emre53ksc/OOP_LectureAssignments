package Equipment;

import Objects.Experiment;

/**
 * The Camera class extends the ResearchEquipment class and represents a camera used in research experiments.
 * It includes functionality to determine if the camera is working and provides methods to report its status,
 * show its representation on a map, and compare it with other Camera objects.
 * 
 * The Camera class provides constructors for creating a camera with a specified ID, a default ID, or by copying another camera's properties.
 * The isWorking method returns the working status of the camera, and the report method provides a status message.
 * The showOnMap method returns a string representation for map display.
 * 
 * The equals method is overridden to compare Camera objects based on their working status and inherited properties.
 * The getExperiment method returns the specific experiment type associated with the camera.
 * 
 */
public class Camera extends ResearchEquipment{

    private boolean isWorking;

    public Camera(int id) {
        super(id);
        this.isWorking = Math.random() >= 0.2;
    }

    public Camera(){
        this(-1);
    }

    public Camera(Camera other){
        super(other);
        this.isWorking = other.isWorking;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public String report() {
        return "Camera Placement: " + ((this.isWorking) ? "The camera successfully started recording.":"The camera failed to start recording.");
    }

    public String showOnMap() {
        return "CM";
    }
    
    @Override
    public boolean equals(Object other){
        if (super.equals(other)){
            Camera otherCamera = (Camera) other;
            return this.isWorking == otherCamera.isWorking;
    }
        else{return false;}
    } 

    @Override
    public Experiment getExperiment() {
        return Experiment.CAMERA_PLACEMENT;
    }
    
}
