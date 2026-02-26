package persistence;

import java.io.FileNotFoundException;

import model.ProjectList;

// Represents a writer that writes JSON representation of ProjectList to a file
// ATTRIBUTION: Based on JsonSerializationDemo 
public class JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {

    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {

    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of project list to file
    public void write(ProjectList projectList) {

    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {

    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToJson(String json) {
        
    }
}
