package model;

import java.util.List;

// Represents a task with subtasks of its own
// Inter-class structure based on D4 (Composite Pattern) Lecture Labs
public class BranchTask extends Task {
    private List<Task> subtasks;

    // // EFFECTS: creates a branch task (task with subtasks) with the given name, description, and no subtasks
    // public BranchTask(String name, String description) {
    //     super(name, description);
    //     //TODO Auto-generated constructor stub
    // }

    // REQUIRES: subtasks is not empty
    // EFFECTS: creates a branch task (task with subtasks) with the given name, description, and subtasks
    public BranchTask(String name, String description, List<Task> subtasks) {
        super(name, description);
        //TODO Auto-generated constructor stub
    }

    // MODIFIES: this
    // EFFECTS: adds the given task as a subtask to this task
    public void addSubtask(Task task) {

    }

    // REQUIRES: getSubtasks().length() > 1
    // MODIFIES: this
    // EFFECTS: removes the given task from the list of subtasks of this task
    public void removeSubtask(Task task) {

    }

    // EFFECTS: returns the due date of the task, which is the due date of the subtask with the latest due date
    @Override
    public Date getDueDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDueDate'");
    }

    // EFFECTS: returns the weight of the task, which is the sum of the weights of the subtasks
    @Override
    public int getWeight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWeight'");
    }

    // EFFECTS: returns whether the task has been completed, which is when all subtasks have been completed
    @Override
    public boolean isCompleted() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCompleted'");
    }

    // EFFECTS: returns the completion percentage of this task (as an integer from 0 to 100),
    //          determined by the weights of the subtasks and whether they are completed or not
    //          percentage is rounded down to the nearest integer
    public int getCompletionPercentage() {
        return -1;
    }

    // EFFECTS: returns this task's subtasks
    public List<Task> getSubtasks() {
        return null;
    }

    // EFFECTS: returns the number of subtasks 
    private int getNumSubtasks() {
        return -1;
    }

    // EFFECTS: returns the sum of the weights of the subtasks which have been completed
    private int getCompletedSubtasksWeight() {
        return -1;
    }

    // EFFECTS: returns a string representation of the task and its subtasks, suitable for command line viewing
    //          in the format "[[Completion Status]] [Name]: [Description] (Due: [Due Date] | Progress: [Completion Percentage])"
    //          with subsequent lines being the result of calling getStringFormat() on subtasks ordered by due date, indented by 4 spaces
    @Override
    public String getStringFormat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStringFormat'");
    }

}
