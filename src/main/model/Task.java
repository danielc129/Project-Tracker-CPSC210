package model;

import java.util.List;

import persistence.Writable;

// Represents a task 
// ATTRIBUTION: Inter-class structure based on D4 (Composite Pattern) Lecture Labs
public abstract class Task implements Writable {
    protected static final int DESCRIPTION_MAX_LENGTH = 20;
    protected static final String INDENT = "    ";

    protected String name;
    protected String description;
    protected Utilities utilities;
    protected int depth;

    // REQUIRES: depth >= 0
    // EFFECTS: creates a task with the given name, description, and depth
    public Task(String name, String description, int depth) {
        this.name = name;
        this.description = description;
        this.depth = depth;
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

    // EFFECTS: returns a string representation of only this task, not including any subtasks and not
    //          including the description. Adds indent based on the depth of this task. 
    public abstract String getStringFormatNoSubtasksNoDescription();

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

    // EFFECTS: returns a list of all of this task's descendants, including itself
    public abstract List<Task> getDescendants();

    // EFFECTS: returns the depth of the task
    public int getDepth() {
        return depth;
    }

    // REQUIRES: depth >= 0
    // MODIFIES: this
    // EFFECTS: sets the depth of the task to the given depth
    public abstract void setDepth(int depth);

}
