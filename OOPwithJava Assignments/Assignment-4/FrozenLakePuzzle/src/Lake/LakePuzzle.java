package Lake;

import Objects.*;
import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Set;

import Interfaces.*;

import Equipment.*;
import Exceptions.*;

import java.util.HashSet;

public class LakePuzzle {
    public static final int ROW_COUNT = 8;
    public static final int COLUMN_COUNT = 11;
    private int cliffSide;
    private Menu menu;
    private FrozenLake lake;
    private Queue<Researcher> researchers;
    private Set<Experiment> experiments;
    private Set<Equipment> equipmentStorage;

    public LakePuzzle() {
        cliffSide = -1;             
        menu = new Menu();
        lake = new FrozenLake();
        cliffSide = ((int) (Math.random() * 3) + 1);    // Random number between 1 and 3
        lake.initializeFrozenLake(cliffSide);           // Initialize the frozen lake
        researchers = createResearchersQueue();         // Create a queue of researchers randomly between 2 and 4
        experiments = createExperimentsSet();           // Create a set of experiments randomly between 1 and 4
        equipmentStorage = createEquipmentSet(2);    // Create a set of equipment with 2 of each equipment
    }

    // Starts the puzzle
    public void startPuzzle() {
        menu.start(lake, researchers, experiments, equipmentStorage);
    }

    // Getters and Setters
    public int getCliffSide() {
        return cliffSide;
    }

    public void setCiffSide(int cliffSide) {
        this.cliffSide = cliffSide;
    }

    private class Menu {
        private Scanner scanner;
        private boolean isInjured;
        private int researcherRow;
        private int researcherColumn;
        Set<ResearchEquipment> placedEquipments;

        public Menu() {
            scanner = new Scanner(System.in);                   // Scanner object to read user input, must be done like this otherwise buggy
            placedEquipments = new HashSet<ResearchEquipment>();// Set to keep track of placed research equipments to check if the goal is reached
        }

        // Gettters and Setters
        public int getResearcherRow() {
            return researcherRow;
        }

        public int getResearcherColumn() {
            return researcherColumn;
        }

        public void setResearcherRow(int researcherRow) {
            this.researcherRow = researcherRow;
        }

        public void setResearcherColumn(int researcherColumn) {
            this.researcherColumn = researcherColumn;
        }

        public boolean getIsInjured() {
            return isInjured;
        }

        public void setIsInjured(boolean isInjured) {
            this.isInjured = isInjured;
        }

        // Starts the menu dialog
        public void start(FrozenLake lake, Queue<Researcher> researchers, Set<Experiment> experiments,
                Set<Equipment> equipmentStorage) {
            // 1. Show initial state of the lake, researchers, experiments, and equipment
            // storage
            setIsInjured(false); // Flag to indicate if a researcher has been injured
            System.out.println("Welcome to Frozen Lake Puzzle App. There are " + researchers.size()
                    + " researchers waiting at the lake entrance.");
            System.out.println("There are " + experiments.size() + " experiment(s) that must be completed:");
            for (Experiment experiment : experiments) {
                System.out.println("- " + experiment.toString());
            }
            System.out.println("The initial map of the frozen lake: ");
            lake.setObject(1, (COLUMN_COUNT + 1) / 2, researchers.peek()); // Set the first researcher to the entrance
            lake.showLake();

            for (Researcher researcher : researchers) {
                // 2. Announce the researcher
                

                researcherRow = 1;                          // initialize row of the researcher
                researcherColumn = (COLUMN_COUNT + 1) / 2;  // initialize column of the researcher
                lake.setObject(researcherRow, researcherColumn, researcher);    // Set the researcher to the entrance

                if (researcher.getId() == 1) {// 3. Display the equipment storage for only the first researcher
                    System.out.println(
                            "=====> Researcher 1 starts waiting at the entrance and can select up to 3 pieces of equipment of the \r\n"
                                    + //
                                    "same type. Here are the shorter notations of the equipments: \r\n" + //
                                    "[td] Temperature detector \r\n" + //
                                    "[ws] Wind speed detector \r\n" + //
                                    "[cm] Camera \r\n" + //
                                    "[ch] Chiseling equipment \r\n" + //
                                    "[cl] Climbing equipment \r\n" + //
                                    "[wb] Large wooden board \r\n" + //
                                    "[ph] Protective helmet \r\n" + //
                                    "[no] Stop taking equipment and head out to the lake ");
                } else {
                    System.out
                            .println("=====> Researcher " + (researcher.getId() - 1) + " stops moving, and Researcher "
                                    + (researcher.getId()) + " starts waiting at the entrance.");
                    lake.showLake();
                    System.out.println("Researcher " + (researcher.getId())
                            + " can select up to 3 pieces of equipment of the same type.");
                }
                // 4. Assign equipment to a researcher
                for (int i = 0; i < 3; i++) {
                    Equipment equipment = null;
                    ShortHand shortHand;
                    while (true) {
                        System.out.println("Enter the short name of an equipment:");
                        try {
                            shortHand = ShortHand.valueOf(getShorthand().toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid input. Please enter a valid equipment short name:");
                            continue;
                        }
                        if (shortHand == ShortHand.NO) {
                            if (i == 0) {
                                System.out.println("*** Researchers cannot head to the lake with an empty bag.");
                                continue;
                            } else {
                                break;
                            }
                        }

                        equipment = getEquipment(shortHand);

                        for (Equipment e : equipmentStorage) {
                            if (e.getClass().equals(equipment.getClass())) {
                                equipment = e; // If equipment is found in the equipment storage, the equipment object
                                               // will be replaced with the one in the storage changing its id
                                break;
                            }
                        }

                        if (equipment.getId() == -1) { // If equipment is not found in the equipment storage its id will
                                                       // be -1
                            System.out.println(
                                    "*** There no more " + shortHand.toString() + " left in the Equipment Storage.");
                            continue;
                        }

                        try {
                            researcher.takeEquipment(equipment); // Add equipment to the researcher's bag
                        } catch (IncorrectBagContentsException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        equipmentStorage.remove(equipment);     // Remove taken equipment from the equipment storage
                        if (researcher.getId() == 1) {
                            System.out.print("- Contents of the bag of Researcher " + researcher.getId() + ": ");   // Display the contents of the current bag
                            for (Equipment e : researcher.getEquipmentBagArray()) {
                                System.out.print(e.showOnMap().toLowerCase() + ", ");
                            }
                            System.out.println();
                        }
                        break;
                    }
                    if (shortHand == ShortHand.NO) {            // If the researcher decides to stop taking equipment
                        break;
                    }
                }

                System.out.print("- The final contents of the bag of Researcher " + researcher.getId() + ":");  // Display the final contents of the bag
                for (Equipment e : researcher.getEquipmentBagArray()) {
                    System.out.print(e.showOnMap().toLowerCase() + ", ");
                }
                System.out.println();

                if (researcher.getId() == 1) {               // The researcher's first movement is handled differently
                    System.out.println("=====> Researcher " + researcher.getId()
                            + " heads out to the lake. Select a direction to slide ([U] Up, [D] Down, [L] Left, [R] Right):");
                } else {
                    System.out.println("=====> Researcher " + researcher.getId()
                            + " heads out to the lake. Select a direction to slide:");
                }
                char direction;
                while (true) {
                    try {
                        direction = getDirection(lake);
                        if (direction == 'U') {            // If the direction is up, the researcher's request will be denied
                            throw new UnavailableDirectionException(
                                    "*** The input direction is unavailable. Please enter an available direction:");
                        }
                    } catch (UnavailableDirectionException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                }

                State state = slide(lake, researcher, researcherRow, researcherColumn, direction);  // Slide the researcher

                setIsInjured(determineInjury(state, researcher));   // Determine if the researcher is injured

                while (true) {
                    switch (state) {    // Handle the state of the researcher
                        case LARGE_WOODEN_BOARD:    // If the researcher encounters a large wooden board
                            lake.showLake();
                            System.out.println("=====> Researcher " + researcher.getId()
                                    + " manages to stop safely on a Wooden Board.");
                            break;
                        case CLIMBING_EQUIPMENT:    // If the researcher encounters climbing equipment
                            lake.showLake();
                            System.out.println("=====> Researcher " + researcher.getId()
                                    + " manages to climb back up the cliff using the Climbing Equipment.");
                            break;
                        case CLIFF_EDGE:            // If the researcher encounters a cliff edge
                            if (getIsInjured()) {
                                System.out.println("=====> Researcher " + researcher.getId()
                                        + " falls off the cliff and gets injured.");
                            } else {
                                lake.setObject(researcherRow, researcherColumn,
                                        researcher.useEquipment(new ClimbingEquipment()));
                                lake.showLake();
                                System.out.println("!!! Researcher " + researcher.getId()
                                        + " is sliding towards the cliff edge and starts falling. However, Researcher "
                                        + researcher.getId() + " is carrying a Climbing Equipment. Researcher "
                                        + researcher.getId()
                                        + " sets the Climbing Equipment and manages to climb back up the cliff.");
                            }
                            break;
                        case HOLE_IN_ICE:           // If the researcher encounters a hole in the ice
                            if (getIsInjured()) {
                                System.out.println("=====> Researcher " + researcher.getId()
                                        + " falls into a hole in the ice and gets injured.");
                            } else {
                                lake.setObject(researcherRow, researcherColumn,
                                        researcher.useEquipment(new LargeWoodenBoard()));
                                lake.showLake();
                                System.out.println("!!! Researcher " + researcher.getId()
                                        + " comes across a hole in ice. However, Researcher " + researcher.getId()
                                        + " is carrying a Large Wooden Board. Researcher " + researcher.getId()
                                        + " covers the ice with the board and starts standing on it. ");
                            }
                            break;
                        case ICE_SPIKES:            // If the researcher encounters ice spikes
                            if (getIsInjured()) {
                                System.out.println("=====> The ice spikes falls on Researcher " + researcher.getId()
                                        + "'s head and injures the Researcher.");
                            } else {
                                lake.setObject(researcherRow, researcherColumn, null);
                                researcher.useEquipment(new ProtectiveHelmet());
                                lake.showLake();
                                System.out.println("!!! Researcher " + researcher.getId()
                                        + " comes across ice spikes. However, Researcher " + researcher.getId()
                                        + " is carrying a Protective Helmet. Researcher " + researcher.getId()
                                        + " wears the helmet and manages to survive the ice spikes falling.");

                            }
                            break;
                        case IMAP_PLACEABLE:        // If the researcher encounters any other IMapPlaceable object
                            lake.showLake();
                            System.out.println("=====> Researcher " + researcher.getId() + " manages to stop safely.");
                            break;
                        case ENTERANCE:             // If the researcher reaches the entrance
                            lake.showLake();
                            System.out.println("=====> Researcher " + researcher.getId()
                                    + " has reached the entrance. The researcher empties their bag into the equipment storage.");
                            for (Equipment e : researcher.getEquipmentBagArray()) {
                                equipmentStorage.add(e);
                            }
                            break;

                    }
                    if (getIsInjured() || state == State.ENTERANCE) {       // If the researcher is injured or reaches the entrance, the loop will end
                        break;
                    }
                    System.out.println("[1] Continue moving on the ice. \r\n" + //
                            "[2] Choose experiment equipment and perform an experiment. \r\n" + //
                            "[3] Sit on the ground and let the other researchers head out to the lake. \r\n" + //
                            "Choose the action of Researcher " + researcher.getId() + ":");
                    int selection = -1;
                    while (true) {                  // Handle the researcher's selection
                        selection = getSelection(3);
                        switch (selection) {
                            case 1:                 // If the researcher decides to continue moving
                                System.out.println("Select a direction to slide:");
                                direction = getDirection(lake);
                                state = slide(lake, researcher, researcherRow, researcherColumn, direction);
                                setIsInjured(determineInjury(state, researcher));
                                break;
                            case 2:                 // If the researcher decides to perform an experiment
                                System.out.println("Select an experiment to perform:");
                                try {               // Check if the researcher is carrying any research equipment
                                    researcher.carryingResearchEquipment();
                                } catch (UnavailableEquipmentException e) {
                                    System.out.println("Researcher " + researcher.getId()
                                            + " is not carrying any research equipment.");
                                    System.out.println("Choose the action of Researcher " + researcher.getId() + ":");
                                    continue;
                                }
                                ShortHand shortHand;
                                while (true) {
                                    System.out.println("=====> Enter the name of the research equipment:");

                                    try {
                                        shortHand = ShortHand.valueOf(getShorthand().toUpperCase());
                                    } catch (IllegalArgumentException e) {  // If the input is invalid, the user will be prompted to enter a valid input
                                        System.out.println("Invalid input. Please enter a valid equipment short name:");
                                        continue;
                                    }
                                    break;
                                }

                                Equipment equipment = getEquipment(shortHand);  // Get the equipment object from the short hand

                                for (Equipment e : researcher.getEquipmentBagArray()) { // Check if the any equipment of this class is in the researcher's bag
                                    if (e.getClass().equals(equipment.getClass())) {
                                        equipment = e;
                                        break;
                                    }
                                }

                                if (equipment.getId() == -1) {  // If the equipment is not found in the researcher's bag
                                    System.out.println("*** The selected equipment is not in the equipment bag.");
                                    System.out.println("Choose the action of Researcher " + researcher.getId() + ":");
                                    continue;
                                }

                                try {   // Check if the equipment can be placed in the current location
                                    isValidLocationForExperiment(lake, equipment);
                                } catch (IncompatibleResearchEquipmentLocationException e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("Choose the action of Researcher " + researcher.getId() + ":");
                                    continue;
                                }
                                // If the equipment can be placed in the current location, the equipment will be placed
                                lake.setObject(researcherRow, researcherColumn, researcher.useEquipment(equipment));
                                System.out.println(
                                        "--- The selected research equipment has been placed in the current location.");
                                placedEquipments = checkSuccess(equipment, experiments, placedEquipments);  // Check if the goal is reached
                                break;
                            case 3:    // If the researcher decides to sit on the ground
                                break;
                        }
                        break;
                    }
                    if (selection == 3) {   // If the researcher decides to sit on the ground, the selection loop will end
                        break;
                    }
                }
                if (isInjured) {    // If the researcher is injured, the game will end
                    break;
                }
            }

        }

        private String getShorthand() { // private method for getting shorthand inputs
            String input;
            while (true) {
                input = scanner.nextLine().trim();
                if (input.matches("[a-zA-Z]{2}")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid equipment short name:");
                }
            }
            return input.toLowerCase();
        }

        private int getSelection(int upperBound) {  // private method for getting selection inputs
            int selection;
            while (true) {
                try {
                    selection = Integer.parseInt(scanner.nextLine().trim());
                    if (selection >= 1 && selection <= upperBound) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a valid selection:");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid selection:");
                }
            }
            return selection;
        }

        private char getDirection(FrozenLake lake) {    // private method for getting direction inputs
            String input;
            while (true) {
                input = scanner.nextLine().trim();
                if (!(input.matches("[RLUDrlud]"))) {
                    System.out.println("*** Invalid input. Please reenter your input:");
                }

                try {
                    checkIfDirectionIsAvailable(lake, input.toUpperCase().charAt(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                break;
            }

            return (input.toUpperCase().charAt(0));
        }

        private boolean checkIfDirectionIsAvailable(FrozenLake lake, char direction)    // private method for checking if a direction is available to slide
                throws UnavailableDirectionException {
            int row = getResearcherRow();
            int col = getResearcherColumn();
            boolean isValid = false;

            switch (direction) {
                case 'U':
                    isValid = !(lake.getPriorityObject(row - 1, col) instanceof IMapPlaceable)
                            || (lake.getPriorityObject(row - 1, col) instanceof LargeWoodenBoard)
                            || (lake.getPriorityObject(row - 1, col) instanceof IDangerousHazard);
                    break;
                case 'D':
                    isValid = !(lake.getPriorityObject(row + 1, col) instanceof IMapPlaceable)
                            || (lake.getPriorityObject(row + 1, col) instanceof LargeWoodenBoard)
                            || (lake.getPriorityObject(row + 1, col) instanceof IDangerousHazard);
                    break;
                case 'L':
                    isValid = !(lake.getPriorityObject(row, col - 1) instanceof IMapPlaceable)
                            || (lake.getPriorityObject(row, col - 1) instanceof LargeWoodenBoard)
                            || (lake.getPriorityObject(row, col - 1) instanceof IDangerousHazard);
                    break;
                case 'R':
                    isValid = !(lake.getPriorityObject(row, col + 1) instanceof IMapPlaceable)
                            || (lake.getPriorityObject(row, col + 1) instanceof LargeWoodenBoard)
                            || (lake.getPriorityObject(row, col + 1) instanceof IDangerousHazard);
                    break;
                default:
            }

            if (!isValid) {
                throw new UnavailableDirectionException(
                        "*** The input direction is unavailable. Please enter an available direction:");
            }

            return isValid;
        }

        enum State {    // Enum for the state of the researcher after sliding
            LARGE_WOODEN_BOARD, CLIMBING_EQUIPMENT, CLIFF_EDGE, HOLE_IN_ICE, ICE_SPIKES, IMAP_PLACEABLE, ENTERANCE;
        }

        private boolean determineInjury(State state, Researcher researcher) {   // private method for determining if the researcher has item to overcome a dangerous hazard            boolean isInjured = true;
            Equipment neededEquipment = null;
            switch (state) {
                case CLIFF_EDGE:
                    neededEquipment = new CliffEdge().getOvercomeBy();
                    break;
                case HOLE_IN_ICE:
                    neededEquipment = new HoleInIce().getOvercomeBy();
                    break;
                case ICE_SPIKES:
                    neededEquipment = new IceSpikes().getOvercomeBy();
                    break;
                default:
                    return false;
            }
            for (Equipment e : researcher.getEquipmentBagArray()) {
                if (e.getClass().equals(neededEquipment.getClass())) {
                    isInjured = false;
                    break;
                }
            }

            return isInjured;
        }

        private Equipment getEquipment(ShortHand shortHand) {   // private method for getting equipment objects from shorthand inputs
            Equipment equipment = null;
            switch (shortHand) {
                case TD:
                    equipment = new TemperatureDetector();
                    break;
                case WS:
                    equipment = new WindSpeedDetector();
                    break;
                case CM:
                    equipment = new Camera();
                    break;
                case CH:
                    equipment = new ChiselingEquipment();
                    break;
                case CL:
                    equipment = new ClimbingEquipment();
                    break;
                case WB:
                    equipment = new LargeWoodenBoard();
                    break;
                case PH:
                    equipment = new ProtectiveHelmet();
                    break;
                default:
                    break;
            }
            return equipment;
        }

        private boolean isValidLocationForExperiment(FrozenLake lake, Equipment equipment)  // private method for checking if the location is valid to put an experiment
                throws IncompatibleResearchEquipmentLocationException {
            int row = getResearcherRow();
            int col = getResearcherColumn();

            ResearchEquipment researchEquipment = (ResearchEquipment) equipment;

            if (researchEquipment instanceof TemperatureDetector) { // For temperature detector there must be no ice block or wall directly adjacent
                if ((lake.getPriorityObject(row - 1, col) instanceof IceBlock) ||
                        (lake.getPriorityObject(row + 1, col) instanceof IceBlock) ||
                        (lake.getPriorityObject(row, col - 1) instanceof IceBlock) ||
                        (lake.getPriorityObject(row, col + 1) instanceof IceBlock) ||
                        (lake.getPriorityObject(row - 1, col) instanceof Wall) ||
                        (lake.getPriorityObject(row + 1, col) instanceof Wall) ||
                        (lake.getPriorityObject(row, col - 1) instanceof Wall) ||
                        (lake.getPriorityObject(row, col + 1) instanceof Wall)) {
                    throw new IncompatibleResearchEquipmentLocationException(
                            "*** The selected research equipment is incompatible with the current location.");
                }

            } else if (researchEquipment instanceof WindSpeedDetector) { // For wind speed detector there must be no dangerous hazard directly adjacent
                if ((lake.getPriorityObject(row - 1, col) instanceof IDangerousHazard) ||
                        (lake.getPriorityObject(row + 1, col) instanceof IDangerousHazard) ||
                        (lake.getPriorityObject(row, col - 1) instanceof IDangerousHazard) ||
                        (lake.getPriorityObject(row, col + 1) instanceof IDangerousHazard)) {
                    throw new IncompatibleResearchEquipmentLocationException(
                            "*** The selected research equipment is incompatible with the current location.");
                }
            } else if (researchEquipment instanceof Camera) {           // For camera there must be no hazard between cliff edge and camera's location
                int i = 1;
                while (true) {
                    switch (getCliffSide()) {
                        case 1:
                            if (lake.getPriorityObject(row, col - i) instanceof CliffEdge) {
                                return true;
                            }

                            if (lake.getPriorityObject(row, col - i) instanceof Hazard) {
                                throw new IncompatibleResearchEquipmentLocationException(
                                        "*** The selected research equipment is incompatible with the current location.");
                            }
                            i++;
                            break;
                        case 2:
                            if (lake.getPriorityObject(row, col + i) instanceof CliffEdge) {
                                return true;
                            }

                            if (lake.getPriorityObject(row, col + i) instanceof Hazard) {
                                throw new IncompatibleResearchEquipmentLocationException(
                                        "*** The selected research equipment is incompatible with the current location.");
                            }
                            i++;
                            break;
                        case 3:
                            if (lake.getPriorityObject(row + i, col) instanceof CliffEdge) {
                                return true;
                            }

                            if (lake.getPriorityObject(row + i, col) instanceof Hazard) {
                                throw new IncompatibleResearchEquipmentLocationException(
                                        "*** The selected research equipment is incompatible with the current location.");
                            }
                            i++;
                            break;
                        default:
                            break;

                    }
                }

            } else if (researchEquipment instanceof ChiselingEquipment) {   // For chiseling equipment there must be ice block directly adjacent
                if ((lake.getPriorityObject(row - 1, col) instanceof IceBlock) ||
                        (lake.getPriorityObject(row + 1, col) instanceof IceBlock) ||
                        (lake.getPriorityObject(row, col - 1) instanceof IceBlock) ||
                        (lake.getPriorityObject(row, col + 1) instanceof IceBlock)) {
                    return true;
                } else {
                    throw new IncompatibleResearchEquipmentLocationException(
                            "*** The selected research equipment is incompatible with the current location.");
                }
            }
            return false;
        }

        private Set<ResearchEquipment> checkSuccess(Equipment equipment, Set<Experiment> experiments,Set<ResearchEquipment> placedEquipments) { // private method for checking if the goal is reached
            ResearchEquipment researchEquipment = (ResearchEquipment) equipment;
            placedEquipments.add(researchEquipment);
            Set<ResearchEquipment> goalEquipments = new HashSet<ResearchEquipment>();
            Set<Experiment> completedExperiments = new HashSet<Experiment>();
            for (ResearchEquipment e : placedEquipments) {  // Check if placed equipments fulfill the experiments
                if (experiments.contains(e.getExperiment())) {
                    completedExperiments.add(e.getExperiment());
                    goalEquipments.add(e);
                }
            }
            if (completedExperiments.size() == experiments.size()) {    // If all experiments are completed, the goal is reached and the game will end
                lake.showLake();
                System.out.println("-----------> Research goal(s) have been accomplished. Here are their results:\n");
                for (ResearchEquipment e : goalEquipments) {
                    System.out.println("--" + e.report());
                }
                System.out.println("-----------> SUCCESSFUL ");
                System.exit(0);
            }
            return placedEquipments;
        }
    }

    /**
     * Slides the researcher in the specified direction until an obstacle is encountered.
     *
     * @return The state of the menu after the slide, indicating the type of obstacle encountered.
     */
    public Menu.State slide(FrozenLake lake, Researcher researcher, int researcherRow, int researcherColumn,
            char direction) {   // Method for sliding the researcher
        int row = researcherRow;
        int col = researcherColumn;
        Menu.State state = null;
        int i = 1;

        lake.removeResearcher(row, col);

        while (true) {
            switch (direction) {    // Check the direction of the slide
                case 'U':
                    if (lake.getPriorityObject(row - i, col) instanceof LargeWoodenBoard) {
                        this.menu.setResearcherRow(row - i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.LARGE_WOODEN_BOARD;
                        return state;
                    } else if (lake.getPriorityObject(row - i, col) instanceof ClimbingEquipment) { // This is
                                                                                                    // impossible to
                                                                                                    // encounter in the
                                                                                                    // current set of
                                                                                                    // rules
                        this.menu.setResearcherRow(row - i + 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.CLIMBING_EQUIPMENT;
                        return state;
                    } else if (lake.getPriorityObject(row - i, col) instanceof CliffEdge) { // This is impossible to
                                                                                            // encounter in the current
                                                                                            // set of rules
                        this.menu.setResearcherRow(row - i + 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.CLIFF_EDGE;
                        return state;
                    } else if (lake.getPriorityObject(row - i, col) instanceof HoleInIce) {
                        this.menu.setResearcherRow(row - i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.HOLE_IN_ICE;
                        return state;
                    } else if (lake.getPriorityObject(row - i, col) instanceof IceSpikes) {
                        this.menu.setResearcherRow(row - i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.ICE_SPIKES;
                        return state;
                    } else if (lake.getPriorityObject(row - i, col) instanceof IMapPlaceable) {
                        this.menu.setResearcherRow(row - i + 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.IMAP_PLACEABLE;
                        return state;
                    } else if ((row - i == 1) && (col == (COLUMN_COUNT + 1) / 2)) { // This state can only be reached
                                                                                    // while going up to the entrance
                        state = Menu.State.ENTERANCE;
                        return state;
                    }
                    break;
                case 'D':
                    if (lake.getPriorityObject(row + i, col) instanceof LargeWoodenBoard) {
                        this.menu.setResearcherRow(row + i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.LARGE_WOODEN_BOARD;
                        return state;
                    } else if (lake.getPriorityObject(row + i, col) instanceof ClimbingEquipment) {
                        this.menu.setResearcherRow(row + i - 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.CLIMBING_EQUIPMENT;
                        return state;
                    } else if (lake.getPriorityObject(row + i, col) instanceof CliffEdge) {
                        this.menu.setResearcherRow(row + i - 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.CLIFF_EDGE;
                        return state;
                    } else if (lake.getPriorityObject(row + i, col) instanceof HoleInIce) {
                        this.menu.setResearcherRow(row + i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.HOLE_IN_ICE;
                        return state;
                    } else if (lake.getPriorityObject(row + i, col) instanceof IceSpikes) {
                        this.menu.setResearcherRow(row + i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.ICE_SPIKES;
                        return state;
                    } else if (lake.getPriorityObject(row + i, col) instanceof IMapPlaceable) {
                        this.menu.setResearcherRow(row + i - 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.IMAP_PLACEABLE;
                        return state;
                    }
                    break;
                case 'L':
                    if (lake.getPriorityObject(row, col - i) instanceof LargeWoodenBoard) {
                        this.menu.setResearcherColumn(col - i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.LARGE_WOODEN_BOARD;
                        return state;
                    } else if (lake.getPriorityObject(row, col - i) instanceof ClimbingEquipment) {
                        this.menu.setResearcherColumn(col - i + 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.CLIMBING_EQUIPMENT;
                        return state;
                    } else if (lake.getPriorityObject(row, col - i) instanceof CliffEdge) {
                        this.menu.setResearcherColumn(col - i + 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.CLIFF_EDGE;
                        return state;
                    } else if (lake.getPriorityObject(row, col - i) instanceof HoleInIce) {
                        this.menu.setResearcherColumn(col - i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.HOLE_IN_ICE;
                        return state;
                    } else if (lake.getPriorityObject(row, col - i) instanceof IceSpikes) {
                        this.menu.setResearcherColumn(col - i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.ICE_SPIKES;
                        return state;
                    } else if (lake.getPriorityObject(row, col - i) instanceof IMapPlaceable) {
                        this.menu.setResearcherColumn(col - i + 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.IMAP_PLACEABLE;
                        return state;
                    }
                    break;
                case 'R':
                    if (lake.getPriorityObject(row, col + i) instanceof LargeWoodenBoard) {
                        this.menu.setResearcherColumn(col + i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.LARGE_WOODEN_BOARD;
                        return state;
                    } else if (lake.getPriorityObject(row, col + i) instanceof ClimbingEquipment) {
                        this.menu.setResearcherColumn(col + i - 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.CLIMBING_EQUIPMENT;
                        return state;
                    } else if (lake.getPriorityObject(row, col + i) instanceof CliffEdge) {
                        this.menu.setResearcherColumn(col + i - 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.CLIFF_EDGE;
                        return state;
                    } else if (lake.getPriorityObject(row, col + i) instanceof HoleInIce) {
                        this.menu.setResearcherColumn(col + i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.HOLE_IN_ICE;
                        return state;
                    } else if (lake.getPriorityObject(row, col + i) instanceof IceSpikes) {
                        this.menu.setResearcherColumn(col + i);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.ICE_SPIKES;
                        return state;
                    } else if (lake.getPriorityObject(row, col + i) instanceof IMapPlaceable) {
                        this.menu.setResearcherColumn(col + i - 1);
                        lake.setObject(this.menu.getResearcherRow(), this.menu.getResearcherColumn(), researcher);
                        state = Menu.State.IMAP_PLACEABLE;
                        return state;
                    }
                    break;
                default:
                    break;
            }
            i++; // Increment i to check the next cell
        }

    }

    private Queue<Researcher> createResearchersQueue() {
        int researcherCount = (int) (Math.random() * 3) + 2; // Random number between 2 and 4

        // Create a queue of researchers
        Queue<Researcher> researchers = new LinkedList<Researcher>();
        for (int i = 0; i < researcherCount; i++) {
            researchers.add(new Researcher((i + 1)));
        }
        return researchers;
    }

    private Set<Experiment> createExperimentsSet() {
        int experimentCount = (int) (Math.random() * 4) + 1; // Random number between 1 and 4

        Set<Experiment> experiments = new HashSet<Experiment>();
        // Create given number of experiments
        while (experiments.size() < experimentCount) {
            Experiment experiment = Experiment.values()[(int) (Math.random() * Experiment.values().length)];
            experiments.add(experiment);
        }

        return experiments;
    }

    private Set<Equipment> createEquipmentSet(int equipmentCount) {
        // equipmentCount is number of each equipment
        Set<Equipment> equipment = new HashSet<Equipment>();

        for (int i = 0; i < equipmentCount; i++) {
            equipment.add(new LargeWoodenBoard(i));
            equipment.add(new ClimbingEquipment(i));
            equipment.add(new TemperatureDetector(i));
            equipment.add(new ProtectiveHelmet(i));
            equipment.add(new Camera(i));
            equipment.add(new WindSpeedDetector(i));
            equipment.add(new ChiselingEquipment(i));
        }

        return equipment;
    }

}
