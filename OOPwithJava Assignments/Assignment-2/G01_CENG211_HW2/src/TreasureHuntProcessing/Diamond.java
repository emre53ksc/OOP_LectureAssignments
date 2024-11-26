package TreasureHuntProcessing;

public class Diamond extends Booster{

    // Constructor with parameters to initialize the Diamond with specific values
    public Diamond(MapPosition position,char symbol,int lives, int points) {
        super(position,symbol,lives,points);
    }

    // Default constructor initializing Diamond with default values
    public Diamond() {
        this(new MapPosition(), 'D', 0, 10);
    }

     // Copy constructor to create a new Diamond object from an existing one
    public Diamond(Diamond original) {
        this(new MapPosition(original.getPosition()), original.getSymbol(), original.getLives(), original.getPoints());
    }

    @Override
    public void interact(Player player) {
        // Default implementation does nothing
        player.setLives(player.getLives() + this.getLives());
        player.setPoints(player.getPoints() + this.getPoints());
    }
    
}
