package Garden;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import Enums.Color;
import Enums.GardenObjectType;
import Enums.GardenPlantAttributes;
import Enums.LightSourceAttributes;
import Enums.LightType;
import Enums.PlantType;
import Interfaces.IGardenPlaceable;
import Interfaces.ISearchable;
import Objects.GardenObject;
import Objects.GardenPlant;
import Objects.LightSource;
import Objects.PollenCloud;
import Storage.StorageShed;
import Objects.Statue;

public class GardenMenu {
    private StorageShed storageShed;
    private Garden garden;
    private Scanner scanner;

    public GardenMenu() {
        storageShed = new StorageShed();
        garden = new Garden();
        scanner = new Scanner(System.in);
    }

    public void startProcess() {
        PollenCloud goal = selectGoal();
        GardenSquare goalSquare = selectGoalSquare();
        System.out.println("Welcome to Garden Puzzle App. " + goalString(goal));
        ArrayList<ISearchable> selectedObjects = selectGardenObjects(7);
        System.out.println("==> The gardener carries selected objects to the Garden. \r\n" + //
                "******************************************************************");
        garden.setTarget(goalSquare.getRow(), goalSquare.getCol());
        initializeStatues(garden);
        System.out.println("Initial Garden Map:");
        garden.printGarden();
        List<GardenSquare> selectedLocations = placeGardenObjects(selectedObjects);
        bloomAndLightUpAll(selectedLocations);
        System.out.println("Final Garden Map:");
        garden.printGarden();
        showPlacedObjects();
        System.out.println("====>>  "+(checkSuccess(goal,goalSquare) ? "SUCCESSFULL":"UNSUCCESSFULL"));
        scanner.close();

    }

    /**
     * Selects a specified number of garden objects from the storage shed based on user input.
     * The user can choose between selecting plants or light sources and apply various filters
     * to search for specific objects.
     *
     * @param amount the number of garden objects to select
     * @return a list of selected garden objects
     */
    private ArrayList<ISearchable> selectGardenObjects(int amount) {
        ArrayList<ISearchable> selectedObjects = new ArrayList<>();
        GardenObject selectedObj;

        while (selectedObjects.size() < amount) {
            int typeChoice = readAndValidateSelection("Please select the type of object ([1] Plant, [2] Light): ", 2,
                    0);

            if (typeChoice == 1) {
                int plantFilter = readAndValidateSelection(
                        "Please select search filter for Plants ([1] Plant type, [2] Plant name, [3] Plant id, [4] Area of spread): ",
                        4, 0);

                GardenPlantAttributes attribute = GardenPlantAttributes.values()[plantFilter - 1];
                try {
                    selectedObj = handleGardenPlantSelection(attribute);
                    selectedObjects.add(selectedObj);
                    storageShed.remove(selectedObj);
                } catch (NoResultsFoundException e) {
                    System.out.println("No Results Found with this query. Please try again.");
                    continue;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }

            } else if (typeChoice == 2) {
                int lightFilter = readAndValidateSelection(
                        "Please select search filter for Light Sources ([1] Light type, [2] Light id, [3] Color, [4] Area of reach): ",
                        4, 0);

                LightSourceAttributes attribute = LightSourceAttributes.values()[lightFilter - 1];
                try {
                    selectedObj = handleLightSourceSelection(attribute);
                    selectedObjects.add(selectedObj);
                    storageShed.remove(selectedObj);
                } catch (NoResultsFoundException e) {
                    System.out.println("No Results Found with this query. Please try again.");
                    continue;
                }
            }

            if (selectedObjects.size() < amount) {
                int continueChoice = readAndValidateSelection("You have taken " + selectedObjects.size()
                        + " Garden Object(s). Would you like to select another one? ([1] Yes, [2] No): ", 2, 0);
                if (continueChoice == 2) {
                    break;
                }
            }
        }
        return selectedObjects;
    }

    /**
     * Handles the selection of a GardenPlant based on the specified attribute.
     *
     * @param attribute The attribute to base the selection on. This can be one of the following:
     *                  TYPE, NAME, ID, AREA_OF_POLLEN_SPREAD.
     * @return The selected GardenPlant that matches the given criteria.
     * @throws NoResultsFoundException If no plants match the given criteria.
     * @throws IllegalArgumentException If an unknown attribute is provided.
     */
    private GardenPlant handleGardenPlantSelection(GardenPlantAttributes attribute) throws NoResultsFoundException {

        String options = switch (attribute) { // Forming question
            case TYPE -> " ([1] Flower, [2] Tree, [3] Bush)";
            case NAME -> "";
            case ID -> "";
            case AREA_OF_POLLEN_SPREAD -> " value between 1 and 5";
            default -> " ";
        };
        String question = "Please enter a plant " + attribute.toString() + options + ":";
        String query;

        switch (attribute) {

            case TYPE:
                int typeInt = readAndValidateSelection(question, PlantType.values().length, 0);
                PlantType type = PlantType.values()[typeInt - 1];
                query = type.toString();
                break;

            case NAME:
                String name = readReply(question).toLowerCase(Locale.ENGLISH); // No input validation is okay because wrong inputs
                                                                 // will just cause a NoResultsFoundException
                query = name;
                break;

            case ID:
                String id = readAndValidateId(question, GardenObjectType.GARDEN_PLANT);
                query = id;
                break;

            case AREA_OF_POLLEN_SPREAD:
                int areaOfPollenSpread = readAndValidateSelection(question, 5, 0);
                query = String.valueOf(areaOfPollenSpread);
                break;

            default:
                throw new IllegalArgumentException("Unknown attribute: " + attribute.toString());
        }

        ArrayList<ISearchable> plants = storageShed.search(attribute, query);
        if (plants.isEmpty()) {
            throw new NoResultsFoundException("No plants that fit the criteria");
        }
        System.out.println("The Plants that fit the given criteria:");
        for (ISearchable plant : plants) {
            System.out.println(plant.toString());
        }

        String plantId;
        do {
            plantId = readAndValidateId("--> Please enter the id of the Garden Plant you would like to take: ",
                    GardenObjectType.GARDEN_PLANT);
            GardenPlant selectedPlant = (GardenPlant) storageShed.searchById(GardenObjectType.GARDEN_PLANT, plantId);
            if (selectedPlant != null && plants.contains(selectedPlant)) {
                return selectedPlant;
            }
            System.out.println("Incorrect input. Please try again.");
        } while (true);
    }

    /**
     * Handles the selection of a LightSource based on the specified attribute.
     *
     * @param attribute The attribute to filter the LightSource by. It can be TYPE, COLOR, ID, or AREA_OF_LIGHT_REACH.
     * @return The selected LightSource that matches the given criteria.
     * @throws NoResultsFoundException If no LightSource matches the given criteria.
     * @throws IllegalArgumentException If an unknown attribute is provided.
     */
    private LightSource handleLightSourceSelection(LightSourceAttributes attribute) throws NoResultsFoundException {
        String options = switch (attribute) {
            case TYPE -> " ([1] Small Lamp, [2] Large Lamp, [3] Spotlight)";
            case COLOR -> " ([1] Red, [2] Green, [3] Blue)";
            case ID -> "";
            case AREA_OF_LIGHT_REACH -> " value between 1 and 5";
            default -> " ";
        };
        String question = "Please enter a light " + attribute.toString() + options + ":";
        String query;

        switch (attribute) {

            case TYPE:
                int queryInt = readAndValidateSelection(question, 3, 0);
                LightType type = LightType.values()[queryInt - 1];
                query = type.toString();
                break;

            case COLOR:
                int colorInt = readAndValidateSelection(question, 3, 0);
                Color color = Color.values()[colorInt - 1];
                query = color.toString();
                break;

            case ID:
                String id = readAndValidateId(question, GardenObjectType.LIGHT_SOURCE);
                query = id;
                break;

            case AREA_OF_LIGHT_REACH:
                int areaOfPollenSpread = readAndValidateSelection(question, 5, 0);
                query = String.valueOf(areaOfPollenSpread);
                break;

            default:
                throw new IllegalArgumentException("Unknown attribute: " + attribute.toString());
        }

        ArrayList<ISearchable> lights = storageShed.search(attribute, query);
        if (lights.isEmpty()) {
            throw new NoResultsFoundException("No lights that fit the criteria");
        }
        System.out.println("The Lights that fit the given criteria:");
        for (ISearchable light : lights) {
            System.out.println(light.toString());
        }

        String lightId;
        do {
            lightId = readAndValidateId("--> Please enter the id of the Light Source you would like to take: ",
                    GardenObjectType.LIGHT_SOURCE);
            LightSource selectedLight = (LightSource) storageShed.searchById(GardenObjectType.LIGHT_SOURCE, lightId);
            if (selectedLight != null && lights.contains(selectedLight)) {
                return selectedLight;
            }
            System.out.println("Incorrect input. Please try again.");
        } while (true);
    }

    /**
     * Prompts the user with a question and reads a single-word reply from the console.
     * If the user inputs more than one word, it will prompt the user to try again until
     * a valid single-word input is provided.
     *
     * @param question the question to prompt the user with
     * @return the single-word reply from the user
     */
    private String readReply(String question) {
        String ret;
        System.out.println(question);
        while (true) {
            String text = scanner.nextLine();
            String[] parts = text.split("\\s+", 2);
            ret = parts[0];
            String restOfTheLine = parts.length > 1 ? parts[1] : "";
            if (!restOfTheLine.isEmpty()) {
                System.out.println("Incorrect input. Please try again.\n" + question); // All answers we need are only
                                                                                       // one "WORD"
                continue;
            }
            break;
        }
        return ret;
    }

    /**
     * Reads a user input, validates it against the specified bounds, and returns the valid input.
     * The input must be an integer within the range (lowerBound, upperBound].
     *
     * @param question The prompt question to be displayed to the user.
     * @param upperBound The inclusive upper bound for the valid input range.
     * @param lowerBound The exclusive lower bound for the valid input range.
     * @return The validated integer input from the user.
     */
    private int readAndValidateSelection(String question, int upperBound, int lowerBound) {
        String query = readReply(question);
        int queryInt;
        do {
            try {
                queryInt = Integer.parseInt(query);
                if (upperBound >= queryInt && queryInt > lowerBound) {
                    return queryInt;
                } else {
                    System.out.println("Incorrect input. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            query = readReply(question);
        } while (true);
    }

    /**
     * Reads and validates an ID based on the specified garden object type.
     * The ID must start with 'P' for garden plants or 'L' for light sources.
     * If the ID does not match the expected format, the user is prompted to try again.
     *
     * @param question The question to prompt the user for input.
     * @param gardenObject The type of garden object to validate the ID against.
     *                     If null, both 'P' and 'L' prefixes are accepted.
     * @return A valid ID string that starts with 'P' or 'L' based on the garden object type.
     */
    private String readAndValidateId(String question, GardenObjectType gardenObject) {
        String id = readReply(question);
        do {
            if ((id.startsWith("P") && (gardenObject == null || gardenObject.equals(GardenObjectType.GARDEN_PLANT)))
                    || (id.startsWith("L")
                            && (gardenObject == null || gardenObject.equals(GardenObjectType.LIGHT_SOURCE)))) {
                break;
            }
            System.out.println("Incorrect input. Please try again.");
            id = readReply(question);
        } while (true);
        return id;
    }

    public class NoResultsFoundException extends Exception {
        public NoResultsFoundException(String message) {
            super(message);
        }
    }

    /**
     * Selects a random goal for the garden by creating a PollenCloud with random pollens and colors.
     * 
     * @return a PollenCloud object containing a random selection of pollens and colors.
     */
    private PollenCloud selectGoal() {
        Random random = new Random();
        PollenCloud pollenCloud = new PollenCloud();

        // Randomly select a number of pollens and colors to add
        int numberOfPollens = random.nextInt(PlantType.values().length) + 1;
        int numberOfColors = random.nextInt(Color.values().length + 1);

        // Add random pollens
        PlantType randomPollen;
        while (numberOfPollens > pollenCloud.getPollenTypes().size()) {
            randomPollen = PlantType.values()[random.nextInt(PlantType.values().length)];
            pollenCloud.addPollenType(randomPollen);
        }

        // Add random pollens
        Color randomColor;
        while (numberOfColors > (pollenCloud.getColors().size())) {
            randomColor = Color.values()[random.nextInt(Color.values().length)];
            pollenCloud.addColor(randomColor);
        }

        return pollenCloud;
    }

    /**
     * Selects a random goal square from the garden.
     * The row is randomly chosen from 'A' to 'F' (inclusive),
     * and the column is randomly chosen from 1 to 8 (inclusive).
     *
     * @return the randomly selected GardenSquare object
     */
    private GardenSquare selectGoalSquare() {
        Random random = new Random();
        char row = (char) ('A' + random.nextInt(6));
        int column = random.nextInt(8) + 1;
        return garden.getSquare(row, column);
    }

    /**
     * Generates a descriptive string for the goal PollenCloud.
     *
     * This method constructs a string that describes the required pollen types and colors
     * for a given PollenCloud goal.
     *
     * @param goal the PollenCloud object containing the goal pollen types and colors
     * @return a string describing the required pollen types and colors for the goal
     */
    private String goalString(PollenCloud goal) {
        StringBuilder goalPollens = new StringBuilder();
        StringBuilder goalColors = new StringBuilder();
        for (PlantType pollen : goal.getPollenTypes()) {
            goalPollens.append(pollen.toString().toUpperCase(Locale.ENGLISH) + ",");
        }
        for (Color color : goal.getColors()) {
            goalColors.append(color.toString().toUpperCase(Locale.ENGLISH) + ",");
        }
        String gPString = goalPollens.substring(0, goalPollens.length() - 1);
        String gCString = (goalColors.isEmpty()) ? "no" : goalColors.substring(0, goalColors.length() - 1);
        return "Your goal Square needs " + gPString + " pollens infused with " + gCString + " color(s).";
    }

    /**
     * Initializes and places statues in the garden.
     * This method creates 7 statues with unique identifiers ("S1" to "S7")
     * and places them randomly in the given garden.
     *
     * @param garden the garden where the statues will be placed
     */
    private void initializeStatues(Garden garden) {
        Statue statue;
        for (int i = 1; i < 8; i++) {
            statue = new Statue("S" + i);
            garden.placeObjectRandomly(statue);
        }
    }

    /**
     * Places selected garden objects in specified locations within the garden.
     * 
     * @param selectedObjects An ArrayList of ISearchable objects representing the garden objects to be placed.
     * @return An ArrayList of GardenSquare objects representing the locations where the garden objects were placed.
     * 
     * The method performs the following steps:
     * 1. Displays the list of selected garden objects.
     * 2. Prompts the user to enter the ID of the garden object they want to place.
     * 3. Validates the entered ID and finds the corresponding garden object.
     * 4. Prompts the user to enter the location (row and column) where they want to place the selected garden object.
     * 5. Validates the entered location and checks if it is empty and not a target location.
     * 6. Places the garden object in the specified location within the garden.
     * 7. Removes the placed garden object from the list of selected objects.
     * 8. Repeats the process until all selected objects are placed or the user chooses to stop.
     */
    private ArrayList<GardenSquare> placeGardenObjects(ArrayList<ISearchable> selectedObjects) {
        ArrayList<GardenSquare> objLocations = new ArrayList<GardenSquare>();
        while (true) {
            System.out.println("--> Your chosen Garden Objects: ");
            for (ISearchable obj : selectedObjects) {
                System.out.println(obj.toString());
            }
            ISearchable found = null;
            while (true) {
                String id = readAndValidateId(
                        "Enter the id corresponding to the Garden Object you would like to place:", null);
                for (ISearchable element : selectedObjects) {
                    GardenObject gardenObject = (GardenObject) element;
                    if (gardenObject.getId().equals(id)) {
                        found = element;
                        break;
                    }
                }
                if (found != null) {
                    break;
                }
                System.out.println("Please select one of the existing ids.");
            }
            GardenSquare location;
            while (true) {
                String rowColumn = readReply("Enter the location you would like to place the selected Garden Object:");
                if (rowColumn.matches("[A-F][0-8]")) {
                    char row = rowColumn.charAt(0);
                    int column = Character.getNumericValue(rowColumn.charAt(1));
                    location = garden.getSquare(row, column);
                    if (!(location.isEmpty()) || location.isTarget()){
                        System.out.println("Please enter a valid location");
                        continue;
                    }
                    break;
                }
                System.out.println("Please enter a valid location");
            }
            selectedObjects.remove(found);
            garden.placeObject(location.getRow(), location.getCol(), (IGardenPlaceable) found);
            objLocations.add(garden.getSquare(location.getRow(), location.getCol()));
            if (selectedObjects.isEmpty()) {
                System.out.println("==> You have run out of objects to place.");
                break;
            }
            int keepGoing = readAndValidateSelection("Would you like to place another object? ([1] Yes, [2] No):", 2,
                    0);
            if (keepGoing == 2) {
                break;
            }
        }
        return objLocations;
    }

    /**
     * This method lights up all light sources and makes all plants bloom in the given garden.
     * It first separates the selected locations into light sources and plants.
     * Then, it lights up all light sources and updates the garden accordingly.
     * After that, it makes all plants bloom and updates the garden until no more changes occur.
     *
     * @param selectedLocations The list of garden squares that contain either light sources or plants.
     */
    private void bloomAndLightUpAll(List<GardenSquare> selectedLocations) {
        System.out.println("==> The gardener starts waiting. All lights are lit up. All plants start blooming. ");
        List<GardenSquare> lightsLocations = new ArrayList<GardenSquare>();
        List<GardenSquare> plantsLocations = new ArrayList<GardenSquare>();

        for (GardenSquare square : selectedLocations) {
            if (square.getObjectInSquare() instanceof GardenPlant) {
                plantsLocations.add(square);
            } else if (square.getObjectInSquare() instanceof LightSource) {
                lightsLocations.add(square);
            }
        }

        LightSource light;
        for (GardenSquare square : lightsLocations) {
            light = (LightSource) garden.getSquare(square.getRow(), square.getCol()).getObjectInSquare();
            ArrayList<ArrayList<GardenSquare>> affectedSquares = light.determineRange(square.getRow(), square.getCol());
            ArrayList<ArrayList<GardenSquare>> affectedOriginalSquares = getSquaresFromGarden(affectedSquares);
            List<GardenSquare> changedSquares = light.lightUp(affectedOriginalSquares);

            for (GardenSquare changedSquare : changedSquares) {
                garden.placeObject(changedSquare.getRow(), changedSquare.getCol(),
                        (IGardenPlaceable) changedSquare.getObjectInSquare());
            }
        }
    

        GardenPlant plant;
        Garden GardenCopy = new Garden(garden);
        boolean changed = true;
        while (changed) {
            for (GardenSquare square : plantsLocations) {
                plant = (GardenPlant) garden.getSquare(square.getRow(), square.getCol()).getObjectInSquare();
                ArrayList<ArrayList<GardenSquare>> affectedSquares = plant.determineRange(square.getRow(), square.getCol());
                ArrayList<ArrayList<GardenSquare>> affectedOriginalSquaresCopy = getSquaresFromGarden(affectedSquares);
                List<GardenSquare> changedSquares = plant.bloom(affectedOriginalSquaresCopy);

                for (GardenSquare changedSquare : changedSquares) {
                    garden.placeObject(changedSquare.getRow(), changedSquare.getCol(),
                            (IGardenPlaceable) changedSquare.getObjectInSquare());
               }
            }

            changed = !(garden.equals(GardenCopy));
            GardenCopy = new Garden(garden);
        }

    }

    /**
     * Retrieves the original GardenSquare objects from the garden based on the provided affected squares.
     * The affected squares only have row and col attributes, and this method fetches the corresponding
     * GardenSquare objects from the garden.
     *
     * @param affectedSquares A list of lists containing GardenSquare objects with only row and col attributes.
     * @return A list of lists containing the original GardenSquare objects from the garden.
     */
    private ArrayList<ArrayList<GardenSquare>> getSquaresFromGarden(ArrayList<ArrayList<GardenSquare>> affectedSquares) {
        ArrayList<ArrayList<GardenSquare>> affectedOriginalSquares = new ArrayList<ArrayList<GardenSquare>>();
        for (ArrayList<GardenSquare> direction : affectedSquares) {
            ArrayList<GardenSquare> newDirection = new ArrayList<GardenSquare>();
            for (GardenSquare square : direction) {
                newDirection.add(garden.getSquare(square.getRow(), square.getCol()));
            }
            affectedOriginalSquares.add(newDirection);
        }
        return affectedOriginalSquares;
    }

    /**
     * Checks if the target PollenCloud in the specified GardenSquare matches the goal PollenCloud.
     *
     * @param goal The goal PollenCloud to be matched.
     * @param goalSquare The GardenSquare where the target PollenCloud is located.
     * @return true if the target PollenCloud matches the goal PollenCloud in terms of pollen types and colors, false otherwise.
     */
    private boolean checkSuccess(PollenCloud goal,GardenSquare goalSquare){
        PollenCloud target = (PollenCloud) garden.getSquare(goalSquare.getRow(), goalSquare.getCol()).getObjectInSquare();
        if (target == null){
            return false;
        }
        List<PlantType> targetPollens = target.getPollenTypes();
        List<Color> targetColors = target.getColors();
        List<PlantType> goalPollens = goal.getPollenTypes();
        List<Color> goalColors = goal.getColors();
        return targetPollens.containsAll(goalPollens) && targetColors.containsAll(goalColors);
    }

    /**
     * Retrieves a list of placed lights and plants from the given garden.
     *
     * This method iterates through all the squares in the garden and checks if 
     * they contain an object that is either a GardenPlant or a LightSource. 
     * If such an object is found, it is added to the list of placed objects.
     *
     * @param garden the garden from which to retrieve the placed lights and plants
     * @return a list of GardenObject instances that are either GardenPlant or LightSource
     */
    private List<GardenObject> getPlacedLightsAndPlants(Garden garden) {
        List<GardenObject> placedObjects = new ArrayList<GardenObject>();
        for (List<GardenSquare> row : garden.getSquares()) {
            for (GardenSquare square : row) {
                if (!square.isEmpty() && (square.getObjectInSquare() instanceof GardenObject)) {
                    if (square.getObjectInSquare() instanceof GardenPlant || square.getObjectInSquare() instanceof LightSource){
                        placedObjects.add((GardenObject) square.getObjectInSquare());
                    }
                }
            }
        }
        return placedObjects;
    }

    /**
     * Displays the placed objects in the garden.
     * This method retrieves the list of placed lights and plants in the garden
     * and prints their details to the console.
     * 
     * For each placed object:
     * - If the object is a GardenPlant, it prints the plant's ID, name, and type.
     * - If the object is a LightSource, it prints the light's ID, type, and color.
     */
    private void showPlacedObjects() {
        System.out.println();
        // if obj is placed in the garden, show objects
        List<GardenObject> selectedObjects = getPlacedLightsAndPlants(this.garden);
        for (GardenObject obj : selectedObjects) {
            
                if (obj instanceof GardenPlant) {
                    GardenPlant plant = (GardenPlant) obj;
                    System.out.println("-- " + plant.getId().toUpperCase() + ": " + plant.getName() + " " + plant.getPlantType().toString());
                } else if (obj instanceof LightSource) {
                    LightSource light = (LightSource) obj;
                    System.out.println("-- " + light.getId().toUpperCase() + ": " + light.getLightType().toString() + " with " + light.getColor().toString() + " color");
                }
        }
    }
}
 