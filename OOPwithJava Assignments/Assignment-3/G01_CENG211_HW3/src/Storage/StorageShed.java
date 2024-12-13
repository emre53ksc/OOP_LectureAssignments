package Storage;

import Interfaces.*;
import Objects.Bush;
import Objects.Flower;
import Objects.GardenObject;
import Objects.GardenPlant;
import Objects.LargeLamp;
import Objects.LightSource;
import Objects.SmallLamp;
import Objects.Spotlight;
import Objects.Tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Enums.*;

// StorageShed class is responsible for loading the storage contents from an imported CSV file
public class StorageShed {

    private List<ISearchable> searchableList;

    // Default constructor
    public StorageShed() {
        searchableList = new ArrayList<>();
        loadStorageContents("storage_contents.csv");
    }

    // Copy constructor
    public StorageShed(StorageShed other) {
        this.searchableList = new ArrayList<>();
        for (ISearchable obj : other.searchableList) {
            this.searchableList.add((ISearchable) (((GardenObject)obj).copy()));
        }
    }

    // Load storage contents from a CSV file
    private void loadStorageContents(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                GardenObject obj = createGardenObject(values);
                if (obj != null) {
                    searchableList.add((ISearchable) obj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a GardenObject based on the values from the CSV file
    private GardenObject createGardenObject(String[] values) {
        if (values.length != 4) {
            return null; // Not suitable data to create a GardenObject
        }

        String type = values[0]; // Specific type (e.g., "Flower", "Bush", "SmallLamp", etc.)
        String id = values[1]; // ID (starts with "P" or "L")
        String nameOrColor = values[2]; // NameOrColor
        int areaOfEffect = 0;

        try {
            areaOfEffect = Integer.parseInt(values[3]); // Area of spread or light reach
        } catch (NumberFormatException e) {
            System.out.println("Invalid areOfEffect value in file");
            System.exit(-1);
        }

        if (id.startsWith("P")) {
            // Create a GardenPlant object based on the specific type
            switch (type.toLowerCase(Locale.ENGLISH)) {
                case "flower":
                    return new Flower(id, nameOrColor, areaOfEffect);
                case "bush":
                    return new Bush(id, nameOrColor, areaOfEffect);
                case "tree":
                    return new Tree(id, nameOrColor, areaOfEffect);
                default:
                    throw new IllegalArgumentException("Unknown plant type: " + type);
            }
        } else if (id.startsWith("L")) {
            // Create a LightSource object based on the specific type
            Color color = Color.values()[0];
            try {
                color = Color.valueOf(nameOrColor.toUpperCase(Locale.ENGLISH));
            } catch (IllegalArgumentException e) {
                System.exit(0);
                ; // Invalid color value
            }
            switch (type.toLowerCase(Locale.ENGLISH)) {
                case "small_lamp":
                    return new SmallLamp(id, color, areaOfEffect);
                case "large_lamp":
                    return new LargeLamp(id, color, areaOfEffect);
                case "spotlight":
                    return new Spotlight(id, color, areaOfEffect);
                default:
                    throw new IllegalArgumentException("Unknown light source type: " + type);
            }
        } else {
            return null; // Unknown type
        }
    }

    // Get the list of searchable objects in the storage, based on the attribute and query
    public ArrayList<ISearchable> search(Enum<?> attribute, String query){
        ArrayList<ISearchable> ret = new ArrayList<ISearchable>();
        for (ISearchable element : searchableList) {
            if (element instanceof GardenPlant && attribute instanceof GardenPlantAttributes) {
                if (((GardenPlant) element).match((GardenPlantAttributes) attribute, query)) {
                    ret.add(element);
                }
            } else if (element instanceof LightSource && attribute instanceof LightSourceAttributes) {
                if (((LightSource) element).match((LightSourceAttributes) attribute, query)) {
                    ret.add(element);
                }
            }

        }
        return ret;
    }

    // Get the searchable object in the storage, based on the garden object type and query
    public ISearchable searchById(GardenObjectType gardenObject, String query) {
        switch (gardenObject) {
            case GARDEN_PLANT:
                ArrayList<ISearchable> retPlant = search(GardenPlantAttributes.ID, query);
                return (retPlant.isEmpty()) ? null : retPlant.get(0);
            case LIGHT_SOURCE:
                ArrayList<ISearchable> retLight = search(LightSourceAttributes.ID, query);
                return (retLight.isEmpty()) ? null : retLight.get(0);
            default:
                throw new IllegalArgumentException("Unknown garden object type: " + gardenObject.toString());
        }
    }

    public boolean remove(ISearchable obj){
        return searchableList.remove(obj);
    }
}
