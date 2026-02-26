package model;

import org.json.JSONObject;

// Represents a task with no subtasks of its own
// ATTRIBUTION: Inter-class structure based on D4 (Composite Pattern) Lecture Labs
public class LeafTask extends Task {
    private Date dueDate;
    private int weight;
    private boolean isCompleted;

    // EFFECTS: creates a leaf task (task with no subtasks) with the given name, description, due date, and weight
    public LeafTask(String name, String description, Date dueDate, int weight) {
        super(name, description);
        this.dueDate = dueDate;
        this.weight = weight;
        this.isCompleted = false;
    }

    // MODIFIES: this
    // EFFECTS: sets the due date to the given date
    public void setDueDate(Date newDate) {
        this.dueDate = newDate;
    }

    // REQUIRES: weight > 0
    // MODIFIES: this
    // EFFECTS: sets the task weight to the given weight
    public void setWeight(int newWeight) {
        this.weight = newWeight;
    }

    // MODIFIES: this
    // EFFECTS: sets the completion status of the task to the given boolean value
    public void setCompletion(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    // EFFECTS: returns the due date of this task
    @Override
    public Date getDueDate() {
        return this.dueDate;
    }

    // EFFECTS: returns the weight of this task
    @Override
    public int getWeight() {
        return this.weight;
    }

    // EFFECTS: returns whether this task has been marked as completed
    @Override
    public boolean isCompleted() {
        return this.isCompleted;
    }

    // EFFECTS: returns a string representation of the task, suitable for command line viewing
    //          in the format "[[Completion Status]] [Name]: [Description] (Due: [Due Date] | Weight: [Weight])"
    @Override
    public String getStringFormat() {
        String result = "";
        if (isCompleted()) {
            result = result + "[X] ";
        } else {
            result = result + "[ ] ";
        }
        result = result + this.name + ": " + utilities.shortenString(this.description, DESCRIPTION_MAX_LENGTH) 
            + " (Due: " + this.dueDate.getDateAsString() + " | Weight: " + this.weight + ")";
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", "leaf");
        json.put("description", description);
        json.put("due_date_day", dueDate.getDay());
        json.put("due_date_month", dueDate.getMonth());
        json.put("due_date_year", dueDate.getYear());
        json.put("weight", weight);
        json.put("completion_status", isCompleted);
        return json;
    }
    
}
