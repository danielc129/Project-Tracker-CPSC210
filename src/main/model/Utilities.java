package model;

import java.util.ArrayList;
import java.util.List;

// Provides common utility methods not suitable for addition to the Task class
public class Utilities {

    // EFFECTS: creates a new utilities object with no fields
    public Utilities() {
        
    }

    // EFFECTS: returns a new list of the given tasks sorted in order of closest to furthest due date
    public List<Task> sortTasks(List<Task> tasks) {
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

    // EFFECTS: returns true if the given task is of actual type LeafTask
    // ATTRIBUTION: based on Ed Discussion post 184
    public boolean isLeafTask(Task task) {
        return task.getClass().getName().equals(LeafTask.class.getName());
    }

    // EFFECTS: returns the input string shortened to the max length with ... at the end
    //          if input string is longer than max length
    public String shortenString(String inputString, int maxLength) {
        if (inputString.length() > maxLength) {
            inputString = inputString.substring(0, maxLength - 2);
            inputString = inputString + "...";
        }
        return inputString;
    }

    // EFFECTS: returns true if there is a task in the given list with the given name
    public boolean containsTaskWithName(List<Task> tasks, String name) {
        for (Task task : tasks) {
            if (task.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if there is a project in the given list with the given name
    public boolean containsProjectWithName(List<Project> projects, String name) {
        for (Project project : projects) {
            if (project.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
