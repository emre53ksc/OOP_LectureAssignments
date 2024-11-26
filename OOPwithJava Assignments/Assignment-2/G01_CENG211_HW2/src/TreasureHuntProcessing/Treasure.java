package TreasureHuntProcessing;

public class Treasure extends Booster{

    // Constructor with parameters to initialize the Treasure with specific values
    public Treasure(MapPosition position,char symbol,int lives, int points) {
        super(position,symbol,lives,points);
    }

    // Default constructor initializing Treasure with default values
    public Treasure() {
        this(new MapPosition(), 'T', 1, 0);
    }
    
    // Copy constructor to create a new Treasure object from an existing one
    public Treasure(Treasure original) {
        this(new MapPosition(original.getPosition()), original.getSymbol(), original.getLives(), original.getPoints());
    }

    @Override
    public void interact(Player player) {
        // Default implementation does nothing
        player.setLives(player.getLives() + this.getLives());
        player.setPoints(player.getPoints() + this.getPoints());
    }
}
