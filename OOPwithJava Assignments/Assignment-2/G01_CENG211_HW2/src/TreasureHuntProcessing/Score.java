package TreasureHuntProcessing;

public class Score {
    // Instance variables
    private int points;
    private int lives;
    private MapPosition fromPosition;
    private MapPosition toPosition;

    // Constructor with parameters to initialize the Score with specific values
    
    public Score(int points, int lives, MapPosition fromPosition, MapPosition toPosition) {
        this.points = points;
        this.lives = lives;
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

    // Default constructor initializing Score with default values
    public Score() {
        this(0, 0, new MapPosition(), new MapPosition());
    }

    // Copy constructor to create a new Score object from an existing one
    public Score(Score score) {
        this(score.getPoints(), score.getLives(), new MapPosition(score.getFromPosition()),
                new MapPosition(score.getToPosition()));
    }

    // Getter for points
    public int getPoints() {
        return points;
    }

    // Setter for points
    public void setPoints(int points) {
        this.points = points;
    }

    // Getter for lives
    public int getLives() {
        return lives;
    }

    // Setter for lives
    public void setLives(int lives) {
        this.lives = lives;
    }

    // Getter for fromPosition
    public MapPosition getFromPosition() {
        return new MapPosition(fromPosition);
    }

    // Setter for fromPosition
    public void setFromPosition(MapPosition fromPosition) {
        this.fromPosition = new MapPosition(fromPosition);
    }

    // Getter for toPosition
    public MapPosition getToPosition() {
        return new MapPosition(toPosition);
    }

    // Setter for toPosition
    public void setToPosition(MapPosition toPosition) {
        this.toPosition = new MapPosition(toPosition);
    }

    // Overridden toString method to provide a string representation of the Score object
    @Override
    public String toString() {
        String result = "";
        result += "points: " + this.getPoints()
                + ", lives: " + this.getLives()
                + ", Departed Position: " + this.getFromPosition().toString()
                + ", landed position: " + this.getToPosition().toString();
        return result;
    }

}
