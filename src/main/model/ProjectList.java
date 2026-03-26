package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a list of projects
// ATTRIBUTION: Based on JsonSerializationDemo 
public class ProjectList implements Writable {
    private List<Project> projects;

    // EFFECTS: creates an empty project list
    public ProjectList() {
        projects = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("New project list instantiated"));
    }

    // MODIFIES: this
    // EFFECTS: adds the given project to the project list
    public void addProject(Project project) {
        projects.add(project);
        EventLog.getInstance().logEvent(new Event("Added project(" + project.getUniqueIdentifier() + ") to project list : " + project.getName()));
    }
    
    // EFFECTS: returns the projects added to the list
    public List<Project> getProjects() {
        return projects;    
    }

    // REQUIRES: the given project is in the list
    // MODIFIES: this
    // EFFECTS: removes the given project from the project list
    public void removeProject(Project project) {
        projects.remove(project);
        EventLog.getInstance().logEvent(new Event("Removed project(" + project.getUniqueIdentifier() + ") : " + project.getName()));
    }

    // EFFECTS: returns this as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray projectsArray = new JSONArray();
        for (Project project : projects) {
            projectsArray.put(project.toJson());
        }
        json.put("projects", projectsArray);
        return json;
    }
    
}
