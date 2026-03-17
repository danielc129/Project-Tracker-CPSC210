package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a task with subtasks of its own
// ATTRIBUTION: Inter-class structure based on D4 (Composite Pattern) Lecture Labs
public class BranchTask extends Task {
    private List<Task> subtasks;

    // REQUIRES: subtasks is not empty
    // EFFECTS: creates a branch task (task with subtasks) with the given name, description, subtasks, and depth
    public BranchTask(String name, String description, List<Task> subtasks, int depth) {
        super(name, description, depth);
        this.subtasks = subtasks;
    }

    // MODIFIES: this
    // EFFECTS: adds the given task as a subtask to this task
    public void addSubtask(Task task) {
        this.subtasks.add(task);
        task.setDepth(this.depth + 1);
    }

    // REQUIRES: getSubtasks().length() > 1, given task is in the list of subtasks
    // MODIFIES: this
    // EFFECTS: removes the given task from the list of subtasks of this task
    public void removeSubtask(Task task) {
        this.subtasks.remove(task);
    }

    // MODIFIES: this
    // EFFECTS: sets the completion status of all subtasks
    //          to the given boolean
    @Override
    public void setCompletion(boolean completionStatus) {
        for (Task subtask : subtasks) {
            subtask.setCompletion(completionStatus);
        }
    }

    // REQUIRES: getSubtasks() is not empty
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

    // REQUIRES: getSubtasks() is not empty
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
        return utilities.sortTasks(subtasks);
    }

    // EFFECTS: returns the sum of the weights of all subtasks (including indirect subtasks) 
    //          which have been completed
    public int getCompletedSubtasksWeight() {
        int totalWeight = 0;
        for (Task subtask: subtasks) {
            if (subtask.getClass().getName().equals(this.getClass().getName())) {
                totalWeight = totalWeight + ((BranchTask) subtask).getCompletedSubtasksWeight();
            } else {
                if (subtask.isCompleted()) {
                    totalWeight = totalWeight + subtask.getWeight();
                }
            }
        }
        return totalWeight;
    }

    // EFFECTS: returns a string representation of the task and its subtasks, suitable for command line viewing
    //          in the format 
    //          "[[Completion Status]] [Name]: [Description] (Due: [Due Date] | Progress: [Completion Percentage])"
    //          with subsequent lines being the result of calling getStringFormat() on subtasks ordered by due date, 
    //          indented by 4 spaces
    @Override
    public String getStringFormat() {
        String result = "";
        if (isCompleted()) {
            result = "[X] "; 
        } else {
            result = "[ ] ";
        }
        result = result + this.name + ": " + utilities.shortenString(this.description, DESCRIPTION_MAX_LENGTH) 
            + " (Due: " + this.getDueDate().getDateAsString() + " | Progress: " 
            + this.getCompletionPercentage() + "%)";
        result = result + getSubtasksStringRepresentation();
        return result;
    }

    // EFFECTS: returns a string representation of this task, not including subtasks and not including description
    //          indented
    //          Adds indent based on the depth of this task
    @Override
    public String getStringFormatNoSubtasksNoDescription() {
        String result = INDENT.repeat(depth);
        if (isCompleted()) {
            result += "[X] "; 
        } else {
            result += "[ ] ";
        }
        result = result + this.name + " " + "(Due: " + this.getDueDate().getDateAsString() + " | Progress: " 
            + this.getCompletionPercentage() + "%)";
        return result;
    }

    // EFFECTS: returns all of the leaf tasks reachable from this branch task as a
    //          single list of leaf tasks
    public List<LeafTask> getFlattenedSubtasks() {
        ArrayList<LeafTask> result = new ArrayList<>();
        for (Task subtask : subtasks) {
            if (utilities.isLeafTask(subtask)) {
                result.add((LeafTask) subtask);
            } else {
                result.addAll(((BranchTask) subtask).getFlattenedSubtasks());
            }
        }
        return result;
    }

    // EFFECTS: for each subtask ordered by due date, concatenates the result of calling getStringFormat(),
    //          indenting each line by 4 spaces and preserving indentation of string format of subtasks
    private String getSubtasksStringRepresentation() {
        String result = "";
        List<Task> sortedSubtasks = getSortedSubtasks();
        for (Task subtask : sortedSubtasks) {
            String subtaskResult = subtask.getStringFormat();
            String subtaskResultIndentFixed = "";
            for (int i = 0; i < subtaskResult.length(); i++) {
                if (subtaskResult.charAt(i) == '\n') {
                    subtaskResultIndentFixed = subtaskResultIndentFixed + "\n" + INDENT;
                } else {
                    subtaskResultIndentFixed = subtaskResultIndentFixed + subtaskResult.charAt(i);
                }
            }
            result = result + "\n    " + subtaskResultIndentFixed;
        }
        return result;
    }

    // EFFECTS: returns this as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", "branch");
        json.put("description", description);
        json.put("depth", depth);
        JSONArray subtasksArray = new JSONArray();
        for (Task subtask : subtasks) {
            subtasksArray.put(subtask.toJson());
        }
        json.put("subtasks", subtasksArray);
        return json;
    }
    
    // REQUIRES: depth >= 0
    // MODIFIES: this
    // EFFECTS: sets the depth of this task to the given depth, ensuring that
    //          subtask depths are updated to maintain correctness
    @Override
    public void setDepth(int depth) {
        this.depth = depth;
        for (Task subtask : subtasks) {
            subtask.setDepth(depth + 1);
        }
    }

    // EFFECTS: returns a list of all of this task's descendants, including itself
    //          this task is the first element of the list, direct subtasks are 
    //          ordered by due date
    public List<Task> getDescendants() {
        ArrayList<Task> result = new ArrayList<>();
        result.add(this);
        List<Task> sortedSubtasks = getSortedSubtasks();
        for (Task subtask : sortedSubtasks) {
            result.addAll(subtask.getDescendants());
        }
        return result;
    }

}
