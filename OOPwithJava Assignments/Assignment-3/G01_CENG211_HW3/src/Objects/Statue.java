package Objects;


public class Statue extends GardenObject {
    
    public Statue(String id) {
        super(id);
    }

    public Statue() {
        this("");
    }

    public Statue(Statue other) {
        this(other.getId());
    }

    public boolean equals(Object statue){
        // Check if the current object is the same as the passed object
        if (this == statue) {
            return true;
        }
        // Check if the passed object is null or if the classes of the objects are different
        if (statue == null || getClass() != statue.getClass()){
            return false;
        }
        else {
            // Cast the passed object to Statue
            Statue other = (Statue) statue;
            return getId().equals(other.getId());
        }      
    }

    public GardenObject clone(){
        return new Statue(this);
    }
}