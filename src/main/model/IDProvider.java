package model;

// singleton class responsible for providing projects and tasks with a unique identification number
// for logging purposes
public class IDProvider {
    private static IDProvider singleton;
    private int currentID; 

    // EFFECTS: creates the IDProvider singleton instance with current ID 0,
    //          meaning the first ID given out will be 1
    private IDProvider() {
        currentID = 0;
    }

    // EFFECTS: returns the instance of this singleton, creating it if it doesn't exist yet
    public static IDProvider getInstance() {
        if (singleton == null) {
            singleton = new IDProvider();
        }
        return singleton;
    }

    // MODIFIES: this
    // EFFECTS: Increments current ID number and returns it
    public int getUniqueIdentifier() {
        currentID += 1;
        return currentID;
    }
}
