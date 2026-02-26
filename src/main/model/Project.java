package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import persistence.Writable;

// Represents a project 
public class Project implements Writable {
    private List<Task> tasks;
    private List<ProgressSnapshot> progressHistory;
    private String name;
    private String description;
    private Utilities utilities;

    // EFFECTS: creates a new project with the given name and description, and no tasks 
    public Project(String name, String description) {
        this.tasks = new ArrayList<Task>();
        this.name = name;
        this.description = description;
        this.utilities = new Utilities();
        this.progressHistory = new ArrayList<ProgressSnapshot>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given task to the project
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    // REQUIRES: given task is in the list of tasks
    // MODIFIES: this
    // EFFECTS: removes the given task from the project
    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    // EFFECTS: gets the percentage completion of the project (as an integer from 0 to 100), 
    //          taking into account task weights and completion status
    public int getCompletionPercentage() {
        double completedSubtaskWeight = getCompletedTasksWeight();
        double totalSubtaskWeight = getWeight();
        if (totalSubtaskWeight == 0) {
            return 0;
        }
        double percentage = completedSubtaskWeight / totalSubtaskWeight;
        int roundedPercentage = (int) (percentage * 100);
        return roundedPercentage;
    }

    // MODIFIES: this
    // EFFECTS: adds a new entry to the progress history if the current completion percentage is different from the previous entry
    //          makes a new progress snapshot at the current time and with the current completion percentage
    public void updateProgressHistory() {
        if (!progressHistory.isEmpty()) {
            ProgressSnapshot latestEntry = progressHistory.get(progressHistory.size() - 1);
            if (latestEntry.getCompletionPercentage() == this.getCompletionPercentage()) {
                return;
            }
        }

        ProgressSnapshot newSnapshot = new ProgressSnapshot(this.getCompletionPercentage(), LocalDateTime.now());
        progressHistory.add(newSnapshot);
    }

    // EFFECTS: returns a list of progress snapshots in the progress history
    public List<ProgressSnapshot> getProgressHistory() {
        return this.progressHistory;
    }

    // MODIFIES: this
    // EFFECTS: sets the progress history to the given list of progress snapshots
    public void setProgressHistory(List<ProgressSnapshot> progressHistory) {
        this.progressHistory = progressHistory;
    }

    // EFFECTS: returns the sum of the weight of the tasks 
    private int getWeight() {
        int totalWeight = 0;
        for (Task task : tasks) {
            totalWeight = totalWeight + task.getWeight();
        }
        return totalWeight;
    }

    // EFFECTS: returns the sum of the weights of the tasks and indirect subtasks which are completed
    private int getCompletedTasksWeight() {
        int totalWeight = 0;
        for (Task task : tasks) {
            if (!(utilities.isLeafTask(task))) {
                totalWeight = totalWeight + ((BranchTask) task).getCompletedSubtasksWeight();
            } else {
                if (task.isCompleted()) {
                    totalWeight = totalWeight + task.getWeight();
                }
            }
        }
        return totalWeight;
    }

    // EFFECTS: returns the project name
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the project description
    public String getDescription() {
        return this.description;
    }

    // EFFECTS: returns this project's tasks
    public List<Task> getTasks() {
        return this.tasks;
    }

    // EFFECTS: returns this project's tasks, sorted in order of closest to furthest due date
    public List<Task> getSortedTasks() {
        return utilities.sortTasks(tasks);
    }

    @Override
    public JSONObject toJson() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toJson'");
    }
}
