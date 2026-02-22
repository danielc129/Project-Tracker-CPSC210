package persistence;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import model.BranchTask;
import model.LeafTask;
import model.ProgressSnapshot;
import model.Project;
import model.ProjectList;

// Represents a reader that reads ProjectList from JSON data stored in a file
// ATTRIBUTION: Based on JsonSerializationDemo 
public class JsonReader {
    
    // EFFECTS: constructsreader to read from the file at the given path
    public JsonReader(String filePath) {

    }

    // EFFECTS: reads ProjectList from file and returns it
    public ProjectList read() throws IOException {
        return null;
    }

    // EFFECTS: reads file as string and returns it
    private String readFile(String filePath) throws IOException {
        return null;
    }

    // EFFECTS: parses ProjectList from the given JSON object and returns it
    private ProjectList parseProjectList(JSONObject jsonObject) {
        return null;
    }

    // MODIFIES: projectList
    // EFFECTS: parses Projects from JSON object and adds them to the project list
    private void addProjects(ProjectList projectList, JSONObject jsonObject) {

    }

    // MODIFIES: projectList
    // EFFECTS: parses Project from JSON object and adds it to the project list
    private void addProject(ProjectList projectList, JSONObject jsonObject) {

    }

    // MODIFIES: project
    // EFFECTS: parses tasks from JSON object and adds it to the project
    private void addRootTasks(Project project, JSONObject jsonObject) {

    }

    // EFFECTS: parses the branch task from the JSON object and returns it
    private BranchTask parseBranchTask(JSONObject jsonObject) {
        return null;
    }

    // EFFECTS: parses the leaf task from the JSON object and returns it
    private LeafTask parseLeafTask(JSONObject jsonObject) {
        return null;
    }

    // EFFECTS: parses the progress history from the JSON object and returns it
    private List<ProgressSnapshot> parseProgressHistory(JSONObject jsonObject) {
        return null;
    }

}
