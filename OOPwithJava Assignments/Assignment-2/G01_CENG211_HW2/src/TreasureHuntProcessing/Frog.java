package TreasureHuntProcessing;

public class Frog extends Breaker{

     // Constructor with parameters to initialize the Frog with specific values
    public Frog(MapPosition position,char symbol,int lives, int points) {
        super(position,symbol,lives,points);
    }

    // Default constructor initializing Frog with default values
    public Frog() {
        this(new MapPosition(), 'F', -1, 0);
    }

    // Copy constructor to create a new Frog object from an existing one
    public Frog(Frog original) {
        this(new MapPosition(original.getPosition()), original.getSymbol(), original.getLives(), original.getPoints());
    }

    @Override
    public void interact(Player player) {
        // Default implementation does nothing
        player.setLives(player.getLives() + this.getLives());
        player.setPoints(player.getPoints() + this.getPoints());
    }

}
