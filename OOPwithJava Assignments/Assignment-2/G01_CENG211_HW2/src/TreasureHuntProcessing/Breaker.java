package TreasureHuntProcessing;
import java.util.ArrayList;

public class Breaker extends MapItem{

    // List to hold different types of MapItems
    private ArrayList<MapItem> mapItemList;

    // Constructor with parameters to initialize the Booster with specific values
    public Breaker(MapPosition position,char symbol,int lives, int points) {
        super(position,symbol,lives,points);
        this.mapItemList = new ArrayList<MapItem>(); 
    }

    // Default constructor initializing Breaker with default values
    public Breaker() {
        this(new MapPosition(), 'X', 0, 0);
    }

    // Copy constructor to create a new Breaker object from an existing one
    public Breaker(Breaker original) {
        this(new MapPosition(original.getPosition()), original.getSymbol(), original.getLives(), original.getPoints());
    }

    // Overridden interact method to interact with the player
    @Override
    public void interact(Player player){
        // Iterate over each MapItem in the list and call their interact method
        for (MapItem item : mapItemList) {
            item.interact(player);
        }
    }

    public void initializeMapItemList(){
        mapItemList.add(new Mushroom());
        mapItemList.add(new Frog());
    }


}
