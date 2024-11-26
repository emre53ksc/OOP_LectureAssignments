package TreasureHuntProcessing;

public class Player extends MapItem{

    // Constructor with parameters to initialize the Player with specific values
    public Player(MapPosition position,char symbol,int lives, int points) {
        super(position,symbol,lives,points);
    }

    // Default constructor initializing Player with default values
    public Player() {
        this(new MapPosition(), 'P', 2, 100);
    }

    // Copy constructor to create a new Player object from an existing one
    public Player(Player original) {
        this(new MapPosition(original.getPosition()), original.getSymbol(), original.getLives(), original.getPoints());
    }


}
