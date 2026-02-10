package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Represents a task with subtasks of its own
// ATTRIBUTION: Inter-class structure based on D4 (Composite Pattern) Lecture Labs
public class BranchTask extends Task {
    private List<Task> subtasks;

    // REQUIRES: subtasks is not empty
    // EFFECTS: creates a branch task (task with subtasks) with the given name, description, and subtasks
    public BranchTask(String name, String description, List<Task> subtasks) {
        super(name, description);
        this.subtasks = subtasks;
    }

    // MODIFIES: this
    // EFFECTS: adds the given task as a subtask to this task
    public void addSubtask(Task task) {
        this.subtasks.add(task);
    }

    // REQUIRES: getSubtasks().length() > 1
    // MODIFIES: this
    // EFFECTS: removes the given task from the list of subtasks of this task
    public void removeSubtask(Task task) {
        this.subtasks.remove(task);
    }

    // EFFECTS: returns the due date of the task, which is the due date of the subtask with the latest due date
    @Override
    public Date getDueDate() {
        Date latestDueDate = subtasks.get(0).getDueDate();
        for (Task subtask : subtasks) {
            if (latestDueDate.compareTo(subtask.getDueDate()) == -1) {
                latestDueDate = subtask.getDueDate();
            }
        }
        return latestDueDate;
    }

    // EFFECTS: returns the weight of the task, which is the sum of the weights of the subtasks
    @Override
    public int getWeight() {
        int totalWeight = 0;
        for (Task subtask: subtasks) {
            totalWeight = totalWeight + subtask.getWeight();
        }
        return totalWeight;
    }

    // EFFECTS: returns whether the task has been completed, which is when all subtasks have been completed
    @Override
    public boolean isCompleted() {
        boolean result = true;
        for (Task subtask : subtasks) {
            if (!subtask.isCompleted()) {
                result = false;
            }
        }
        return result;
    }

    // EFFECTS: returns the completion percentage of this task (as an integer from 0 to 100),
    //          determined by the weights of the subtasks and whether they are completed or not
    //          percentage is rounded down to the nearest integer
    public int getCompletionPercentage() {
        double completedSubtaskWeight = getCompletedSubtasksWeight();
        double totalSubtaskWeight = getWeight();
        double percentage = completedSubtaskWeight / totalSubtaskWeight;
        int roundedPercentage = (int) (percentage * 100);
        return roundedPercentage;
    }

    // EFFECTS: returns this task's subtasks
    public List<Task> getSubtasks() {
        return this.subtasks;
    }

    // EFFECTS: return list of subtasks sorted in order of closest to furthest due date
    public List<Task> getSortedSubtasks() {
        List<Task> sortedList = new ArrayList<>();
        for (Task subtask : subtasks) {
            if (sortedList.isEmpty()) {
                sortedList.add(subtask);
            } else {
                boolean added = false;
                for (int i = sortedList.size() - 1; i >= 0; i--) {
                    Task comparingTask = sortedList.get(i);
                    if (subtask.getDueDate().compareTo(comparingTask.getDueDate()) >= 0) {
                        sortedList.add(i + 1, subtask);
                        added = true;
                        break;
                    } 
                }
                if (!added) {
                    sortedList.add(0, subtask);
                }      
            }
        }
        return sortedList;
    }

    // EFFECTS: returns the sum of the weights of the subtasks which have been completed
    private int getCompletedSubtasksWeight() {
        int totalWeight = 0;
        for (Task subtask: subtasks) {
            if (subtask.isCompleted()) {
                totalWeight = totalWeight + subtask.getWeight();
            }
        }
        return totalWeight;
    }

    // EFFECTS: returns a string representation of the task and its subtasks, suitable for command line viewing
    //          in the format "[[Completion Status]] [Name]: [Description] (Due: [Due Date] | Progress: [Completion Percentage])"
    //          with subsequent lines being the result of calling getStringFormat() on subtasks ordered by due date, indented by 4 spaces
    @Override
    public String getStringFormat() {
        String result = "";
        if (isCompleted()) {
            result = "[✓] " + this.name + ": " + this.description + " (Due: " + this.getDueDate().getDateAsString() + " | Progress: " + this.getCompletionPercentage() + "%)";
        } else {
            result = "[ ] " + this.name + ": " + this.description + " (Due: " + this.getDueDate().getDateAsString() + " | Progress: " + this.getCompletionPercentage() + "%)";
        }

        List<Task> sortedSubtasks = getSortedSubtasks();
        for (Task subtask : sortedSubtasks) {
            String subtaskResult = subtask.getStringFormat();
            String subtaskResultIndentFixed = "";
            for (int i = 0; i < subtaskResult.length(); i++) {
                if (subtaskResult.charAt(i) == '\n') {
                    subtaskResultIndentFixed = subtaskResultIndentFixed + "\n    ";
                } else {
                    subtaskResultIndentFixed = subtaskResultIndentFixed + subtaskResult.charAt(i);
                }
            }
            result = result + "\n    " + subtaskResultIndentFixed;
        }
        return result;
    }

}
