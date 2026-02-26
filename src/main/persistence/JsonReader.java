package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.BranchTask;
import model.Date;
import model.LeafTask;
import model.ProgressSnapshot;
import model.Project;
import model.ProjectList;
import model.Task;

// Represents a reader that reads ProjectList from JSON data stored in a file
// ATTRIBUTION: Based on JsonSerializationDemo 
public class JsonReader {
    private String filePath;
    
    // EFFECTS: constructs reader to read from the file at the given path
    public JsonReader(String filePath) {
        this.filePath = filePath;
    }

    // EFFECTS: reads ProjectList from file and returns it
    public ProjectList read() throws IOException {
        String jsonData = readFile(filePath);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseProjectList(jsonObject);
    }

    // EFFECTS: reads file as string and returns it
    public String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ProjectList from the given JSON object and returns it
    private ProjectList parseProjectList(JSONObject jsonObject) {
        ProjectList projectList = new ProjectList();
        addProjects(projectList, jsonObject);
        return projectList;
    }

    // MODIFIES: projectList
    // EFFECTS: parses Projects from JSON object and adds them to the project list
    private void addProjects(ProjectList projectList, JSONObject jsonObject) {
        JSONArray projectsArray = jsonObject.getJSONArray("projects");
        for (Object json : projectsArray) {
            JSONObject project = (JSONObject) json;
            addProject(projectList, project);
        }
    }

    // MODIFIES: projectList
    // EFFECTS: parses Project from JSON object and adds it to the project list
    private void addProject(ProjectList projectList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        List<ProgressSnapshot> progressHistory = parseProgressHistory(jsonObject.getJSONArray("progress_history"));
        JSONArray tasksArray = jsonObject.getJSONArray("tasks");
        Project project = new Project(name, description);
        for (Object json : tasksArray) {
            JSONObject task = (JSONObject) json;
            addRootTasks(project, task);
        }
        project.setProgressHistory(progressHistory);
        projectList.addProject(project);
    }

    // MODIFIES: project
    // EFFECTS: parses tasks from JSON object and adds it to the project
    private void addRootTasks(Project project, JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        if (type.equals("leaf")) {
            LeafTask task = parseLeafTask(jsonObject);
            project.addTask(task);
        } else {
            BranchTask task = parseBranchTask(jsonObject);
            project.addTask(task);
        }
    }

    // EFFECTS: parses the branch task from the JSON object and returns it
    private BranchTask parseBranchTask(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        ArrayList<Task> subtasks = new ArrayList<>();
        for (Object json : jsonObject.getJSONArray("subtasks")) {
            JSONObject subtaskObject = (JSONObject) json;
            String type = subtaskObject.getString("type");
            if (type.equals("leaf")) {
                LeafTask subtask = parseLeafTask(subtaskObject);
                subtasks.add(subtask);
            } else {
                BranchTask subtask = parseBranchTask(subtaskObject);
                subtasks.add(subtask);
            }
        }
        BranchTask task = new BranchTask(name, description, subtasks);
        return task;
    }

    // EFFECTS: parses the leaf task from the JSON object and returns it
    private LeafTask parseLeafTask(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        int dueDateDay = jsonObject.getInt("due_date_day");
        int dueDateMonth = jsonObject.getInt("due_date_month");
        int dueDateYear = jsonObject.getInt("due_date_year");
        int weight = jsonObject.getInt("weight");
        boolean completionstatus = jsonObject.getBoolean("completion_status");
        Date dueDate = new Date(dueDateDay, dueDateMonth, dueDateYear);
        LeafTask task = new LeafTask(name, description, dueDate, weight);
        task.setCompletion(completionstatus);
        return task;
    }

    // EFFECTS: parses the progress history from the JSON object and returns it
    private List<ProgressSnapshot> parseProgressHistory(JSONArray jsonArray) {
        ArrayList<ProgressSnapshot> progressHistory = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject snapshotObject = (JSONObject) json;
            LocalDateTime time = LocalDateTime.parse(snapshotObject.getString("time"));
            int completionPercentage = snapshotObject.getInt("completion_percentage");
            ProgressSnapshot snapshot = new ProgressSnapshot(completionPercentage, time);
            progressHistory.add(snapshot);
        }
        return progressHistory;
    }

}
