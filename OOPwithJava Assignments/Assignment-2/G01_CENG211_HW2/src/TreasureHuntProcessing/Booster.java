package TreasureHuntProcessing;

import java.util.ArrayList;

public class Booster extends MapItem{

    // List to hold different types of MapItems
    private ArrayList<MapItem> mapItemList;


    // Constructor with parameters to initialize the Booster with specific values
    public Booster(MapPosition position,char symbol,int lives, int points) {
        super(position,symbol,lives,points);
        this.mapItemList = new ArrayList<MapItem>();
    }
    // Default constructor initializing Booster with default values
    public Booster() {
        this(new MapPosition(), 'O', 0, 0);
    }

    // Copy constructor to create a new Booster object from an existing one
    public Booster(Booster original) {
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
        mapItemList.add(new Coin());
        mapItemList.add(new Treasure());
        mapItemList.add(new Diamond());
    }

}
