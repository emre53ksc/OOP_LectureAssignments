package TreasureHuntProcessing;

import java.util.ArrayList;
import java.util.Random;

public class GameArranger {
    // Instance variables
    private ArrayList<MapItem> mapItemList;
    private Map map;
    private Player player;
    private int unusedMapItemCounter;
    private int highScore;
    private Scoreboard scoreboard;

    // Constructor to initialize the GameArranger with a list of MapItems
    public GameArranger() {
        // Initialize the list of MapItems
        this.mapItemList = new ArrayList<MapItem>();
        for (int i = 0; i < 1; i++) {
            Booster booster = new Booster();
            booster.initializeMapItemList();
            mapItemList.add(new Booster());
        }
        for (int i = 0; i < 10; i++) {
            mapItemList.add(new Coin());
        }
        for (int i = 0; i < 5; i++) {
            mapItemList.add(new Diamond());
        }
        for (int i = 0; i < 2; i++) {
            mapItemList.add(new Treasure());
        }
        for (int i = 0; i < 1; i++) {
            Breaker breaker = new Breaker();
            breaker.initializeMapItemList();
            mapItemList.add(breaker);
        }
        for (int i = 0; i < 5; i++) {
            mapItemList.add(new Mushroom());
        }
        for (int i = 0; i < 2; i++) {
            mapItemList.add(new Frog());
        }
        for (int i = 0; i < 1; i++) {
            mapItemList.add(new Player());
        }
        
        // Initialize other game components
        unusedMapItemCounter = mapItemList.size()-1;
        scoreboard = new Scoreboard();
        createPlayer();
        createTreasureHuntMap();
        highScore = player.getPoints();
        
    }

    private void createPlayer(){
        this.player = new Player();
    }

    private void createTreasureHuntMap(){
        this.map = new Map();
        for (MapItem item : mapItemList) {
            this.map.setRandomMapItem(item);
        }
    }

    // Method to move the player to a new position
    private void movePlayer() {
        Random random = new Random();
        MapItem currentItem = new MapItem();
        MapPosition position = new MapPosition();

        do {
            // Generate random coordinates for the new position
            int randomX = random.nextInt(20);
            int randomY = random.nextInt(20);
            position = new MapPosition(randomX, randomY);
            // Get the MapItem at the new position
            currentItem = map.getMapItemFromPosition(position);
        } while (currentItem.getSymbol() == 'P'); // Ensure the new position is not already occupied by the player

        // Interact with the MapItem at the new position
        currentItem.interact(player);

        if (!(currentItem.getSymbol() == '_')) {
            // Decrease the counter for unused MapItems
            this.unusedMapItemCounter--;
            // Add the score for the current move to the scoreboard
            Score score = new Score(player.getPoints(), player.getLives(), player.getPosition(), position);
            scoreboard.addScore(score);
            // Update the high score if necessary
            highScore = Math.max(highScore, player.getPoints());
        }

        // Update the player's position
        player.setPosition(position);
        // Update the map with the player's new position
        map.updateMap(player);
        // Print the updated map
        map.printMap();
    }

    // Method to check the current game status
    private boolean checkGameStatus() {
        // Check if the game is over based on player's lives, unused MapItems, or points
        if ((player.getLives() <= 0) || (unusedMapItemCounter == 0) || (player.getPoints() <= 0)) {
            System.out.println("Game Over!");
            System.out.println("High Score: " + highScore);
            return false;
        } else {
            return true;
        }
    }

    // Method to return Scoreboard object as a string
    public String getScoreboardString(){
        return new Scoreboard(scoreboard).toString();
    }

    // Method to run the game
    public void operate(){
        while (checkGameStatus()){
            movePlayer();
        }
    }

}
