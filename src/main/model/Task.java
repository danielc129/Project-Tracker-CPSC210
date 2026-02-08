package model;

// Represents a task 
// Inter-class structure based on D4 (Composite Pattern) Lecture Lab
public abstract class Task {

    // EFFECTS: creates a task with the given name and description
    public Task(String name, String description) {

    }

    // MODIFIES: this
    // EFFECTS: adds a subtask to this task with the given name, description, due date, and weight
    public abstract void addSubtask(String name, String description, Date dueDate, int weight);


    // MODIFIES: this
    // EFFECTS: sets the task name to the given name
    public void setName(String name) {

    }

    // MODIFIES: this
    // EFFECTS: sets the task description to the given description
    public void setDescription(String description) {

    }

    // EFFECTS: returns the name of the task
    public String getName() {
        return null;
    }

    // EFFECTS: returns the description of the task
    public String getDescription() {
        return null;
    }

    // EFFECTS: returns the due date of the task
    public abstract Date getDueDate();

    // EFFECTS: returns the weight of the task
    public abstract int getWeight();

}
