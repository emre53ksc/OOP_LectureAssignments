package Garden;

import Interfaces.IGardenPlaceable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Garden class represents a garden composed of a grid of GardenSquare objects.
 * It provides methods to initialize the garden, place objects in specific squares,
 * and print the garden layout.
 */
public class Garden {
    private List<List<GardenSquare>> squares;
    private int rows = 6;
    private int columns = 8;
    
    /**
     * Constructs a Garden object with the specified number of rows and columns.
     * Initializes the garden by creating the necessary data structures.
     *
     * @param rows the number of rows in the garden
     * @param columns the number of columns in the garden
     */
    public Garden(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        squares = new ArrayList<>();
        initializeGarden();
    } 
    
    /**
     * Default constructor for the Garden class.
     * Initializes a Garden object with default dimensions of 6 rows and 8 columns.
     */
    public Garden(){
        this(6,8);
    }
    
    /**
     * Copy constructor for the Garden class.
     * Creates a new Garden object that is a deep copy of the specified Garden object.
     *
     * @param other the Garden object to be copied
     */
    public Garden(Garden other) {
        this.rows = other.rows;
        this.columns = other.columns;
        this.squares = new ArrayList<>();
        for (List<GardenSquare> row : other.squares) {
            List<GardenSquare> newRow = new ArrayList<>();
            for (GardenSquare square : row) {
                newRow.add(new GardenSquare(square));
            }
            this.squares.add(newRow);
        }
    }

    /**
     * Returns a deep copy of the garden squares.
     *
     * @return a List of Lists containing deep copies of GardenSquare objects.
     */
    public List<List<GardenSquare>> getSquares() {
        List<List<GardenSquare>> copy = new ArrayList<>();
        for (List<GardenSquare> row : squares) {
            List<GardenSquare> newRow = new ArrayList<>();
            for (GardenSquare square : row) {
            newRow.add(new GardenSquare(square));
            }
            copy.add(newRow);
        }
        return copy;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * Checks if the garden is completely full.
     * 
     * @return true if all squares in the garden are occupied, false otherwise.
     */
    private boolean isFull() {
        for (List<GardenSquare> row : squares) {
            for (GardenSquare square : row) {
                if (square.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Initializes the garden by creating a grid of GardenSquare objects.
     * Each square is labeled with a row letter (starting from 'A') and a column number (starting from 1).
     * The grid is stored in the 'squares' list.
     */
    private void initializeGarden() {
        for (int row = 0; row < getRows(); row++) {
            List<GardenSquare> rowList = new ArrayList<>();
            for (int col = 0; col < getColumns(); col++) {
                char rowLabel = (char) ('A' + row);
                rowList.add(new GardenSquare(rowLabel, col + 1));
            }
            squares.add(rowList);
        }
    }

    /**
     * Prints the garden layout to the console.
     * The garden is displayed as a grid with rows labeled A, B, C, etc., and columns numbered 1, 2, 3, etc.
     * Each cell in the grid is represented by its corresponding string value, centered within a 6-character wide cell.
     * The grid is bordered by horizontal and vertical lines for better readability.
     */
    public void printGarden() {
        System.out.println("        1        2        3        4        5        6        7        8   ");
        System.out.println("   ------------------------------------------------------------------------");

        for (int row = 0; row < rows; row++) {
            System.out.print((char) ('A' + row) + " | ");
            for (int col = 0; col < columns; col++) {
                String out = squares.get(row).get(col).toString();
                int padding = (6 - out.length()) / 2;
                out = " ".repeat(padding) + out + " ".repeat(6 - out.length() - padding);
                System.out.print(out + " | ");
            }
            System.out.println();
            System.out.println("   ------------------------------------------------------------------------");
        }
    }

    /**
     * Places an object in the garden at the specified row and column.
     *
     * @param row    The row in the garden where the object should be placed (A-Z).
     return true;
     * @param column The column in the garden where the object should be placed (1-based index).
     * @param object The object to be placed in the garden, implementing the IGardenPlaceable interface.
     * @return       True if the object was successfully placed, false if the specified position is out of bounds.
     */
    public boolean placeObject(char row, int column, IGardenPlaceable object) {
        int rowIndex = row - 'A';
        int colIndex = column - 1;

        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < columns) {
            GardenSquare square = squares.get(rowIndex).get(colIndex);
            square.setGardenPlaceable(object);
        }
        return false;
    }

    /**
     * Retrieves a GardenSquare object from the garden based on the specified row and column.
     *
     * @param row    The row character (e.g., 'A', 'B', 'C', etc.) representing the row in the garden.
     * @param column The column number (1-based index) representing the column in the garden.
     * @return A new GardenSquare object if the specified row and column are within the garden's bounds,
     *         otherwise returns null.
     */
    public GardenSquare getSquare(char row, int column) {
        int rowIndex = row - 'A';
        int colIndex = column - 1;

        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < columns) {
            return new GardenSquare(squares.get(rowIndex).get(colIndex)); 
        }
        return null;
    }

    /**
     * Sets the target flag for a specific square in the garden.
     *
     * @param row    The row character (e.g., 'A', 'B', 'C', etc.) where the target is to be set.
     * @param column The column number (1-based index) where the target is to be set.
     */
    public void setTarget(char row, int column){
        int rowIndex = row - 'A';
        int colIndex = column - 1;

        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < columns) {
            squares.get(rowIndex).get(colIndex).setTarget(true);
        }
    }


    /**
     * Places an object randomly in the garden if there is space available.
     * If the garden is full, it prints a message and does not place the object.
     * The method continues to generate random positions until it finds an empty square
     * that is not a target, and places the object there.
     *
     * @param object the object to be placed in the garden, implementing the IGardenPlaceable interface
     */
    public void placeObjectRandomly(IGardenPlaceable object) {
        if (isFull()) {
            System.out.println("The garden is full. Cannot place the object.");
            return;
        }

        while (true) {
            int randomRow = (int) (Math.random() * rows);
            int randomCol = (int) (Math.random() * columns);
            GardenSquare square = squares.get(randomRow).get(randomCol);
            
            if(square.isTarget()) {
                continue;
            }
            if (square.isEmpty()) {
                square.setGardenPlaceable(object);
                break;
            }
        }
    }

    /**
     * Compares this Garden object to the specified object for equality.
     * 
     * @param garden the object to compare with this Garden object.
     * @return {@code true} if the specified object is equal to this Garden object;
     *         {@code false} otherwise.
     * 
     * The method checks:
     * 1. If the current object is the same as the passed object.
     * 2. If the passed object is null or if the classes of the objects are different.
     * 3. If the number of rows and columns are the same.
     * 4. If the squares in both gardens are the same.
     */
    @Override
    public boolean equals(Object garden){
        // Check if the current object is the same as the passed object
        if (this == garden) {
            return true;
        }
        // Check if the passed object is null or if the classes of the objects are different
        if (garden == null || getClass() != garden.getClass()){
            return false;
        }
        else {
            // Cast the passed object to Garden
            Garden other = (Garden) garden;
            // Check if the number of rows and columns are the same
            if (getRows() != other.rows || getColumns() != other.columns) {
                return false;
            }
            // Check if the squares are the same
            for (int row = 0; row < getRows(); row++) {
                for (int col = 0; col < getColumns(); col++) {
                    if (!squares.get(row).get(col).equals(other.squares.get(row).get(col))) {
                        return false;
                    }
                }
            }
            return true;
        }      
    }
    


}