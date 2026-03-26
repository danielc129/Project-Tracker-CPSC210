package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

// Represents a task with no subtasks of its own
// ATTRIBUTION: Inter-class structure based on D4 (Composite Pattern) Lecture Labs
public class LeafTask extends Task {
    private Date dueDate;
    private int weight;
    private boolean isCompleted;

    // EFFECTS: creates a leaf task (task with no subtasks) with the given name, description, due date, weight, depth,
    //          and parent task (parent task is null if at root level)
    public LeafTask(String name, String description, Date dueDate, int weight, int depth, Task parentTask) {
        super(name, description, depth, parentTask);
        this.dueDate = dueDate;
        this.weight = weight;
        this.isCompleted = false;
        EventLog.getInstance().logEvent(new Event("Created leaf task(" + getUniqueIdentifier() + ") with name " 
                + name + ", and description " + description));
    }

    // MODIFIES: this
    // EFFECTS: sets the due date to the given date
    public void setDueDate(Date newDate) {
        this.dueDate = newDate;
        EventLog.getInstance().logEvent(new Event("Set leaf task " + name + "(" + getUniqueIdentifier() 
                + ") due date to " + newDate.getDateAsString()));
    }

    // REQUIRES: weight > 0
    // MODIFIES: this
    // EFFECTS: sets the task weight to the given weight
    public void setWeight(int newWeight) {
        this.weight = newWeight;
        EventLog.getInstance().logEvent(new Event("Set leaf task " + name + "(" + getUniqueIdentifier() 
                + ") weight to " + newWeight));
    }

    // MODIFIES: this
    // EFFECTS: sets the completion status of the task to the given boolean value
    public void setCompletion(boolean isCompleted) {
        this.isCompleted = isCompleted;
        EventLog.getInstance().logEvent(new Event("Set leaf task " + name + "(" + getUniqueIdentifier() 
                + ") completion status to " + isCompleted));
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

    // EFFECTS: returns a string representation of this task, not including description
    //          and indented based on the depth of this task
    @Override
    public String getStringFormatNoSubtasksNoDescription() {
        String result = INDENT.repeat(depth);
        if (isCompleted()) {
            result += "[X] ";
        } else {
            result += "[ ] ";
        }
        result = result + this.name + " " + "(Due: " 
                + this.dueDate.getDateAsString() + " | Weight: " + this.weight + ")";
        return result;
    }

    // EFFECTS: returns this as a JSON object
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
        json.put("depth", depth);
        json.put("completion_status", isCompleted);
        return json;
    }

    // REQUIRES: depth >= 0
    // MODIFIES: this
    // EFFECTS: sets the depth to the given depth
    @Override
    public void setDepth(int depth) {
        this.depth = depth;
        EventLog.getInstance().logEvent(new Event("Set leaf task " + name + "(" + getUniqueIdentifier() 
                + ") depth field to " + depth));
    }

    // EFFECTS: returns this task as the only element in a list
    @Override
    public List<Task> getDescendants() {
        ArrayList<Task> result = new ArrayList<>();
        result.add(this);
        return result;
    }
    
}
