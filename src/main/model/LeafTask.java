package model;

// Represents a task with no subtasks of its own
// Inter-class structure based on D4 (Composite Pattern) Lecture Labs
public class LeafTask extends Task {

    // EFFECTS: creates a leaf task (task with no subtasks) with the given name, description, due date, and weight
    public LeafTask(String name, String description, Date dueDate, int weight) {
        super(name, description);
        //TODO Auto-generated constructor stub
    }

    // MODIFIES: this
    // EFFECTS: sets the due date to the given date
    public void setDueDate(Date newDate) {

    }

    // REQUIRES: weight > 0
    // MODIFIES: this
    // EFFECTS: sets the task weight to the given weight
    public void setWeight(int newWeight) {

    }

    // MODIFIES: this
    // EFFECTS: sets the completion status of the task to the given boolean value
    public void setCompletion(boolean isCompleted) {
        
    }

    // EFFECTS: returns the due date of this task
    @Override
    public Date getDueDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDueDate'");
    }

    // EFFECTS: returns the weight of this task
    @Override
    public int getWeight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWeight'");
    }

    // EFFECTS: returns whether this task has been marked as completed
    @Override
    public boolean isCompleted() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCompleted'");
    }
    
}
