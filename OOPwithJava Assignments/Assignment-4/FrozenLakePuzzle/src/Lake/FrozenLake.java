package Lake;

import java.util.ArrayList;
import java.util.List;

import Interfaces.IMapPlaceable;
import Objects.CliffEdge;
import Objects.Hazard;
import Objects.HoleInIce;
import Objects.IceBlock;
import Objects.IceSpikes;
import Objects.Wall;

/**
 * The FrozenLake class represents a frozen lake grid with various objects placed on it.
 * It provides methods to initialize the lake with walls, cliff edges, ice blocks, and hazards.
 * The lake is represented as a 2D ArrayList of LakeSquare objects.
 */

public class FrozenLake {

    private int rowCount;
    private int columnCount;
    private ArrayList<ArrayList<LakeSquare>> frozenLake;

    public FrozenLake(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        frozenLake = new ArrayList<ArrayList<LakeSquare>>();
        for (int i = 0; i < rowCount + 2; i++) { // Add 2 to account for the border squares !!!!!!!!!!!!!!!!!!!
            ArrayList<LakeSquare> row = new ArrayList<LakeSquare>();
            for (int j = 0; j < columnCount + 2; j++) { // Add 2 to account for the border squares
                row.add(new LakeSquare());
            }
            frozenLake.add(row);
        }
    }


    // Default constructor
    public FrozenLake() {
        this(8, 11);
    }


    // Copy constructor
    public FrozenLake(FrozenLake other) {
        this.rowCount = other.rowCount;
        this.columnCount = other.columnCount;
        frozenLake = new ArrayList<ArrayList<LakeSquare>>();
        for (int i = 0; i < rowCount + 2; i++) { // Add 2 to account for the border squares
            ArrayList<LakeSquare> row = new ArrayList<LakeSquare>();
            for (int j = 0; j < columnCount + 2; j++) { // Add 2 to account for the border squares
                row.add(new LakeSquare(other.frozenLake.get(i).get(j)));
            }
            frozenLake.add(row);
        }
    }

    // Initialize the FrozenLake with walls, cliff edges, ice blocks, and hazards
    public FrozenLake initializeFrozenLake(int cliffSide) {

        // Set the entrance at the upper middle square
        int entranceColumn = (columnCount + 1) / 2; // Middle column (0-indexed)

        // Add walls to the all edges, except the entrance
        addWalls(this, entranceColumn);

        // Add CliffEdge based on the randomly chosen side
        addCliffEdge(this, cliffSide);

        // Add IceBlocks
        addIceBlocks(this, entranceColumn, cliffSide);

        // Add Hazards
        addHazards(this, entranceColumn, cliffSide);

        return this;
    }

    // getters and setters
    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public IMapPlaceable getPriorityObject(int row, int column) {
        return frozenLake.get(row).get(column).getPriorityObject();
    }

    public List<IMapPlaceable> getAllObjects(int row, int column) {
        return frozenLake.get(row).get(column).getAllObjects();
    }

    public void setObject(int row, int column, IMapPlaceable object) {
        frozenLake.get(row).get(column).setObject(object);
    }

    // Remove a researcher from the specified row and column
    public void removeResearcher(int row, int column) {
        frozenLake.get(row).get(column).removeResearcher();
    }

    // Display the current state of the lake
    public void showLake() {
        System.out.println("\n");
        System.out.println("      " + "-" + "------".repeat((int) (columnCount / 2)) + "-   -"
                + "------".repeat((int) (columnCount / 2)));
        for (int i = 1; i < rowCount + 2; i++) {
            ArrayList<LakeSquare> currentRow = frozenLake.get(i);
            for (int j = 0; j < columnCount + 2; j++) {
                LakeSquare lakeSquare = currentRow.get(j);
                if (lakeSquare.getPriorityObject() == null) {
                    System.out.print("|     ");
                } else if (lakeSquare.getPriorityObject().getClass() == new Wall().getClass()) {
                    System.out.print("      ");
                } else if (lakeSquare.getPriorityObject().getClass() == new CliffEdge().getClass()) {
                    System.out.print("  " + lakeSquare.toString() + "  ");
                } else {
                    if (lakeSquare.toString().length() == 2) {
                        System.out.print("| " + lakeSquare.toString() + "  ");
                    } else {
                        System.out.print("|" + lakeSquare.toString());
                    }

                }
                if ((j == columnCount) && (i != rowCount + 1)) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i != rowCount + 1) {
                System.out.println("      " + "-" + "------".repeat(columnCount));
            }
        }
    }

    // Helper methods
    private void addWalls(FrozenLake lake, int entranceColumn) {
        // Add walls to upper edge except the entrance
        for (int col = 0; col < columnCount + 2; col++) {
            if (col != entranceColumn) {
                lake.setObject(0, col, new Wall());
            }
        }

        // add walls to the lower edge
        for (int col = 0; col < columnCount + 2; col++) {
            lake.setObject(rowCount + 1, col, new Wall());
        }

        for (int row = 0; row < rowCount + 2; row++) {
            // Add walls to the left edge
            lake.setObject(row, 0, new Wall());
            // Add walls to the right edge
            lake.setObject(row, columnCount + 1, new Wall());
        }
    }

    private void addCliffEdge(FrozenLake lake, int cliffSide) {
        switch (cliffSide) {
            case 1: // Left side
                for (int row = 1; row < rowCount + 1; row++) {
                    lake.setObject(row, 0, new CliffEdge());
                }
                break;
            case 2: // Right side
                for (int row = 1; row < rowCount + 1; row++) {
                    lake.setObject(row, columnCount + 1, new CliffEdge());
                }
                break;
            case 3: // Bottom side
                for (int col = 1; col < columnCount + 1; col++) {
                    lake.setObject(rowCount + 1, col, new CliffEdge());
                }
                break;
        }
    }

    private void addIceBlocks(FrozenLake lake, int entranceColumn, int cliffSide) {
        // Ensure 8 IceBlocks, one per row, not blocking entrance
        // Ensure at least one IceBlock in the middle column below the entrance
        int iceBlockCount = 8;
        boolean[] rowUsed = new boolean[rowCount + 2];
        int iceBlocksPlaced = 0;

        // Place IceBlock to the column below the entrance
        int randomRow = (int) (Math.random() * (rowCount-1)) + 2; // Random row (2-8)
        lake.setObject(randomRow, entranceColumn, new IceBlock(iceBlocksPlaced));
        rowUsed[randomRow] = true;
        iceBlocksPlaced++;


        // adding 2 ice blocks near the cliff edge
        if (cliffSide == 1 || cliffSide == 2) {
            for (int i = 0; i < 2; i++) {
                while (true) {
                    int row = (int) (Math.random() * rowCount) + 1; // Random row (1-indexed)
                    if (rowUsed[row])
                        continue;
                    int col = (cliffSide == 1) ? 1 : columnCount;
                    lake.setObject(row, col, new IceBlock(iceBlocksPlaced));
                    rowUsed[row] = true;
                    iceBlocksPlaced++;
                    break;
                }
            }
        } else if (cliffSide == 3) {
            // adding 2 ice blocks near the cliff edge, checking bottom row because the IceBlock count rule in row 
            int limit = ((rowUsed[rowCount]) ? 1: 2);
            for (int i = 0; i < limit; i++) { // If the last row is used when placing an IceBlock in enteranceColumn, place only one ice block
                while (true) {
                    int col = (int) (Math.random() * columnCount) + 1; // Random column (1-indexed)
                    if (lake.getPriorityObject(rowCount, col)!=null)
                        continue;
                    lake.setObject(rowCount, col, new IceBlock(iceBlocksPlaced));
                    rowUsed[rowCount] = true;
                    iceBlocksPlaced++;
                    break;
                }
            }
        }
            

        // Place remaining IceBlocks
        while (iceBlocksPlaced < iceBlockCount) {
            int row = (int) (Math.random() * rowCount) + 1; // Random row (1-indexed)

            // Skip if row already used or is the entrance row
            if (rowUsed[row] || row <= 0 || row >= rowCount + 1)
                continue;

            // Place IceBlock
            if (row > 0 && row < rowCount + 1) {
                int col = 0;
                do {
                    col = (int) (Math.random() * columnCount) + 1; // Random column (1-indexed)
                } while (row == 1 && col == entranceColumn); // Skip if blocking entrance
                lake.setObject(row, col, new IceBlock(iceBlocksPlaced));

                rowUsed[row] = true;
                iceBlocksPlaced++;
            }

        }

    }

    // Add 3 HoleInIce and 3 IceSpikes to the lake
    private void addHazards(FrozenLake lake, int entranceColumn, int cliffSide) {
        // Add 3 HoleInIce
        int holeCount = 0;
        while (holeCount < 3) {
            int row = (int) (Math.random() * rowCount) + 1; // Random row (1-indexed)
            int col = (int) (Math.random() * columnCount) + 1; // Random column (1-indexed)

            // Avoid placing near entrance or on cliffside
            if (isValidHoleInIcePlacement(lake, row, col, entranceColumn, cliffSide)) {
                lake.setObject(row, col, new HoleInIce(holeCount));
                holeCount++;
            }
        }

        // Add 3 IceSpikes next to walls
        int iceSpikeCount = 0;
        while (iceSpikeCount < 3) {
            int row = (int) (Math.random() * rowCount) + 1; // Random row (1-indexed)
            int col = (int) (Math.random() * columnCount) + 1; // Random column (1-indexed)

            // Check if next to a wall and not near entrance or cliffside
            if (isValidIceSpikePlacement(lake, row, col, entranceColumn, cliffSide)) {
                lake.setObject(row, col, new IceSpikes(iceSpikeCount));
                iceSpikeCount++;
            }
        }
    }

    // Validate the placement of a HoleInIce
    private boolean isValidHoleInIcePlacement(FrozenLake lake, int row, int col, int entranceColumn, int cliffSide) {
        // Implementation of hazard placement validation
        // Check:
        // 1. Not within 3 squares of entrance
        if ((row == 1) && (col >= entranceColumn - 2 && col <= entranceColumn + 2)) {
            return false;
        }
        if ((col == entranceColumn) && (row >= 1 && row <= 3)) {
            return false;
        }
        // 2. Not on cliffside
        if ((cliffSide == 1 && col == 1) || (cliffSide == 2 && col == columnCount)
                || (cliffSide == 3 && row == rowCount)) {
            return false;
        }
        // 3. No existing hazards on the square
        if (lake.getPriorityObject(row, col) instanceof IMapPlaceable) {
            return false;
        }
        return true;
    }

    private boolean isValidIceSpikePlacement(FrozenLake lake, int row, int col, int entranceColumn, int cliffSide) {
        // Implementation of ice spike placement validation
        // Check:
        // 1. Next to a wall
        if (!(row == 1 || row == rowCount || col == 1 || col == columnCount)) {
            return false;
        }
        // 2. Not near entrance
        if ((row == 1) && (col >= entranceColumn - 2 && col <= entranceColumn + 2)) {
            return false;
        }
        // 3. Not on cliffside
        if ((cliffSide == 1 && col == 1) || (cliffSide == 2 && col == columnCount)
                || (cliffSide == 3 && row == rowCount)) {
            return false;
        }
        // 4. No existing hazards on the square
        if (lake.getPriorityObject(row, col) instanceof Hazard) {
            return false;
        }

        return true;
    }

}