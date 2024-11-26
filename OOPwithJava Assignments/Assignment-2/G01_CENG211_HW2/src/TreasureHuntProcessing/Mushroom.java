package TreasureHuntProcessing;

public class Mushroom extends Breaker{

    // Constructor with parameters to initialize the Mushroom with specific values
    public Mushroom(MapPosition position,char symbol,int lives, int points) {
        super(position,symbol,lives,points);
    }

    // Default constructor initializing Mushroom with default values
    public Mushroom() {
        this(new MapPosition(), 'M', 0, -20);
    }

    // Copy constructor to create a new Mushroom object from an existing one
    public Mushroom(Mushroom original) {
        this(new MapPosition(original.getPosition()), original.getSymbol(), original.getLives(), original.getPoints());
    }

    @Override
    public void interact(Player player) {
        // Default implementation does nothing
        player.setLives(player.getLives() + this.getLives());
        player.setPoints(player.getPoints() + this.getPoints());
    }

}
