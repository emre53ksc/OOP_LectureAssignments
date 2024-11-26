package TreasureHuntProcessing;

public class MapItem {
    // Instance variables
    private MapPosition position;
    private char symbol;
    private int lives;
    private int points;

    // Constructor with parameters to initialize the MapItem with specific values
    public MapItem(MapPosition position, char symbol, int lives, int points) {
        this.position = position;
        this.symbol = symbol;
        this.lives = lives;
        this.points = points;
    }

    // Default constructor initializing MapItem with default values
    public MapItem() { 
        this(new MapPosition(), '_', 0, 0);
    }

    // Copy constructor to create a new MapItem object from an existing one
    public MapItem(MapItem original) {
        this(new MapPosition(original.position), original.symbol, original.lives, original.points);
    }

    // Special constructor for ease of use, initializing with a position only
    public MapItem(MapPosition position) {
        this(position, '_', 0, 0);
    }

    // Getter for position
    public MapPosition getPosition() {
        return new MapPosition(position);
    }

    // Setter for position
    public void setPosition(MapPosition position) {
        this.position = position;
    }

    // Getter for symbol
    public char getSymbol() {
        return symbol;
    }

    // Setter for symbol
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    // Getter for lives
    public int getLives() {
        return lives;
    }

    // Setter for lives
    public void setLives(int lives) {
        this.lives = lives;
    }

    // Getter for points
    public int getPoints() {
        return points;
    }

    // Setter for points
    public void setPoints(int points) {
        this.points = points;
    }

    // Method to interact with a player (to be overridden by subclasses)
    public void interact(Player player) {
        // Default implementation does nothing
        player.setLives(player.getLives() + this.lives);
        player.setPoints(player.getPoints() + this.points);
    }



}
