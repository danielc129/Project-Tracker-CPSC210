package model;

// Represents a task 
// ATTRIBUTION: Inter-class structure based on D4 (Composite Pattern) Lecture Labs
public abstract class Task {
    protected static final int DESCRIPTION_MAX_LENGTH = 20;

    protected String name;
    protected String description;
    protected Utilities utilities;

    // EFFECTS: creates a task with the given name and description
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.utilities = new Utilities();
    }

    // MODIFIES: this
    // EFFECTS: sets the task name to the given name
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: sets the task description to the given description
    public void setDescription(String description) {
        this.description = description;
    }

    // EFFECTS: returns a string representation of the task, suitable for command line viewing
    public abstract String getStringFormat();

    // MODIFIES: this
    // EFFECTS: sets the completion status of the task to the given boolean
    public abstract void setCompletion(boolean completionStatus);

    // EFFECTS: returns the name of the task
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the description of the task
    public String getDescription() {
        return this.description;
    }

    // EFFECTS: returns the due date of the task
    public abstract Date getDueDate();

    // EFFECTS: returns the weight of the task
    public abstract int getWeight();

    // EFFECTS: returns whether the task has been completed
    public abstract boolean isCompleted();

}
