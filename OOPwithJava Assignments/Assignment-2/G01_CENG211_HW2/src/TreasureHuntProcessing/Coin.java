package TreasureHuntProcessing;
public class Coin extends Booster {
    
    // Constructor with parameters to initialize the Coin with specific values
    public Coin(MapPosition position,char symbol,int lives, int points) {
        super(position,symbol,lives,points);
    }

    // Default constructor initializing Coin with default values
    public Coin() {
        this(new MapPosition(), 'C', 0, 5);
    }

    // Copy constructor to create a new Coin object from an existing one
    public Coin(Coin original) {
        this(new MapPosition(original.getPosition()), original.getSymbol(), original.getLives(), original.getPoints());
    }
    
    @Override
    public void interact(Player player) {
        // Default implementation does nothing
        player.setLives(player.getLives() + this.getLives());
        player.setPoints(player.getPoints() + this.getPoints());
    }
}
