package model;

import java.util.ArrayList;
import java.util.List;

// Represents a project 
public class Project {
    private List<Task> tasks;
    private String name;
    private String description;

    // EFFECTS: creates a new project with the given name and description 
    public Project(String name, String description) {
        this.tasks = new ArrayList<Task>();
        this.name = name;
        this.description = description;
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

    // EFFECTS: gets the percentage completion of the project (as an int from 0 to 100), taking into account task weights
    public int getCompletionPercentage() {
        double completedSubtaskWeight = getCompletedTasksWeight();
        double totalSubtaskWeight = getWeight();
        double percentage = completedSubtaskWeight / totalSubtaskWeight;
        int roundedPercentage = (int) (percentage * 100);
        return roundedPercentage;
    }

    // EFFECTS: returns the sum of the weight of the tasks 
    private int getWeight() {
        int totalWeight = 0;
        for (Task task : tasks) {
            totalWeight = totalWeight + task.getWeight();
        }
        return totalWeight;
    }

    // EFFECTS: returns the sum of the weights of the tasks which are completed
    private int getCompletedTasksWeight() {
        int totalWeight = 0;
        for (Task task : tasks) {
            if (task.isCompleted()) {
                totalWeight = totalWeight + task.getWeight();
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
        List<Task> sortedList = new ArrayList<>();
        for (Task task : tasks) {
            if (sortedList.isEmpty()) {
                sortedList.add(task);
            } else {
                boolean added = false;
                for (int i = sortedList.size() - 1; i >= 0; i--) {
                    Task comparingTask = sortedList.get(i);
                    if (task.getDueDate().compareTo(comparingTask.getDueDate()) >= 0) {
                        sortedList.add(i + 1, task);
                        added = true;
                        break;
                    } 
                }
                if (!added) {
                    sortedList.add(0, task);
                }      
            }
        }
        return sortedList;
    }
}
