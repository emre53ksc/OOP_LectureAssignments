package TreasureHuntProcessing;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    private ArrayList<ArrayList<MapItem>> map;

    // Default constructor initializing the map with default values
    public Map() {
        this.map = new ArrayList<ArrayList<MapItem>>();

        // Creating a 20x20 grid of Default MapItems
        for(int i = 0; i < 20; i++) {
            ArrayList<MapItem> row = new ArrayList<MapItem>();
            for(int j = 0; j < 20; j++) {
                row.add(new MapItem(new MapPosition(i,j)));
            }
            this.map.add(row);
        }
        
    }

    // Copy constructor to create a new Map object from an existing one
    public Map(Map original) {
        this.map = new ArrayList<ArrayList<MapItem>>();
         // Copy each MapItem from the original map
        for(int i = 0; i < 20; i++) {
            ArrayList<MapItem> row = new ArrayList<MapItem>();
            for(int j = 0; j < 20; j++) {
                row.add(new MapItem(original.map.get(i).get(j)));
            }
            this.map.add(row);
        }
    }

    // Method to get a MapItem from a specific MapPosition
    public MapItem getMapItemFromPosition(MapPosition position) {
        return new MapItem(this.map.get(position.getX()).get(position.getY()));
    }

    // Method to set a MapItem at a specific MapPosition
    public void setMapItemFromPosition(MapPosition position, MapItem item) {
        this.map.get(position.getX()).set(position.getY(), new MapItem(item));
    }

    // Method to print the map to the console
    public void printMap() {
        System.out.println("\nMap:\n");
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                System.out.print(this.map.get(i).get(j).getSymbol() + " ");
            }
            System.out.println();
        }
    }

    // Method to update the map with the player's new position
    public void updateMap(Player player) {

        // Clear the player's previous position
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                if(this.map.get(i).get(j).getSymbol() == player.getSymbol()) {
                    this.map.get(i).set(j,new MapItem(new MapPosition(i,j)));
                }
            }
        }
        // Set the player's new position on the map
        this.map.get(player.getPosition().getX()).set(player.getPosition().getY(), player);
    }

    // Method to set a random MapItem on the map
    public void setRandomMapItem(MapItem mapItem) {
        Random random = new Random();
        while(!mapItem.getPosition().isOccupied()) {
            int randomX = random.nextInt(20);
            int randomY = random.nextInt(20);

            if(!map.get(randomX).get(randomY).getPosition().isOccupied()) {
                this.map.get(randomX).set(randomY,mapItem);
                mapItem.setPosition(new MapPosition(randomX, randomY,true));
            }
        }
    }






}
