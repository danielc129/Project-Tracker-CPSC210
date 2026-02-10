package model;

// Represents a project 
public class Project {

    // EFFECTS: creates a new project with the given name and description 
    public Project(String name, String description) {

    }

    // MODIFIES: this
    // EFFECTS: adds the given task to the project
    public void addTask(Task task) {

    }

    // MODIFIES: this
    // EFFECTS: removes the given task from the project
    public void removeTask(Task task) {

    }

    // EFFECTS: gets the percentage completion of the project (as an int from 0 to 100), taking into account task weights
    public int getCompletionPercentage() {
        return -1;
    }
}
