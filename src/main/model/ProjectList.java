package model;

import java.util.ArrayList;
import java.util.List;

public class ProjectList {
    private List<Project> projects;

    // EFFECTS: creates an empty project list
    public ProjectList() {
        projects = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given project to the project list
    public void addProject(Project project) {
        projects.add(project);
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
    }
    
}
