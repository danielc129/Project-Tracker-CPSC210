package model;

import java.util.List;

// Represents a project 
public class Project {

    // EFFECTS: creates a new project with the given name and description 
    public Project(String name, String description) {

    }

    // MODIFIES: this
    // EFFECTS: adds the given task to the project
    public void addTask(Task task) {

    }

    // REQUIRES: given task is in the list of tasks
    // MODIFIES: this
    // EFFECTS: removes the given task from the project
    public void removeTask(Task task) {

    }

    // EFFECTS: gets the percentage completion of the project (as an int from 0 to 100), taking into account task weights
    public int getCompletionPercentage() {
        return -1;
    }

    // EFFECTS: returns the project name
    public String getName() {
        return null;
    }

    // EFFECTS: returns the project description
    public String getDescription() {
        return null;
    }

    // EFFECTS: returns this project's tasks
    public List<Task> getTasks() {
        return null;
    }

    // EFFECTS: returns this project's tasks, sorted in order of closest to furthest due date
    public List<Task> getSortedSubtasks() {
        return null;
    }
}
