package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.BranchTask;
import model.Date;
import model.LeafTask;
import model.Project;
import model.Task;
import model.Utilities;


// Project tracker application
// ATTRIBUTION: Based on Teller project 
public class ProjectTrackerApp {
    private Project currentProject;
    private Task currentTask;
    private Scanner input;
    private Utilities utilities;
    private List<Task> taskStack;

    // EFFECTS: runs the project tracker application
    public ProjectTrackerApp() {
        runProjectTracker();
    }

    // MODIFIES: this
    // EFECTS: processes user input
    private void runProjectTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayInfo();
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command 
    private void processCommand(String command) {
        if (!(currentTask == null)) {
            processCommandSelected(command);
        } else {
            processCommandUnselected(command);
        }
    }

    // REQUIRES: currentTask != null
    // MODIFIES: this
    // EFFECTS: processes user command when there is a selected task
    private void processCommandSelected(String command) {
        switch (command) {
            case "a":
                addTask();
                break;
            case "r":
                removeTask();
                break;
            case "s":
                selectTask();
                break;
            case "e":
                editTask();
                break;
            case "p":
                selectParentTask();
                break;
            case "c":
                toggleCompletion();
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    // REQUIRES: currentTask is null
    // MODIFIES: this
    // EFFECTS: processes user command when there is no task selected
    private void processCommandUnselected(String command) {
        switch (command) {
            case "a":
                addTask();
                break;
            case "s":
                selectTask();
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the project
    private void init() {
        this.input = new Scanner(System.in);
        System.out.println("Please enter project name: ");
        String name = input.nextLine();
        System.out.println("Please enter project description: ");
        String description = input.nextLine();
        this.currentProject = new Project(name, description);
        this.currentTask = null;
        this.taskStack = new ArrayList<>();
        this.utilities = new Utilities();
    }

    // EFFECTS: displays list of tasks to user
    private void displayInfo() {
        System.out.println("\nProject: " + currentProject.getName() + " (" 
            + currentProject.getCompletionPercentage() + "% completed)");

        List<Task> tasks = currentProject.getSortedTasks();
        if (tasks.isEmpty()) {
            System.out.println("\nThere are no tasks added to this project");
        } else if (currentTask != null) {
            System.out.println("Selected task: " + getSelectedTaskPrintout());
            System.out.println();
            System.out.println(currentTask.getStringFormat());
        } else {
            System.out.println("You are at project root\n");
            for (Task task : tasks) {
                System.out.println(task.getStringFormat());
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect an option: ");
        System.out.println("\ta -> add task");
        if (!(currentTask == null)) {
            System.out.println("\tr -> remove task");
            System.out.println("\te -> edit task");
            System.out.println("\tc -> toggle completion");
            System.out.println("\tp -> return to parent task");
        }
        System.out.println("\ts -> select task");
        System.out.println("\tq -> exit project");
    }

    // REQUIRES: currentTask != null
    // EFFECTS: returns a string showing the selected task and its hierarchy in the project
    private String getSelectedTaskPrintout() {
        String result = "";
        for (Task task : taskStack) {
            result = result + task.getName() + " > ";
        }
        result = result + currentTask.getName();
        return result;
    }

    // MODIFIES: this
    // EFFECTS: adds a task to the currently selected task or project
    private void addTask() {
        System.out.print("Enter name of task: ");
        String name = input.nextLine();
        System.out.print("\nEnter task description: ");
        String description = input.nextLine();
        System.out.print("\nEnter day of due date: ");
        int day = input.nextInt();
        System.out.print("\nEnter month of due date (1-12): ");
        int month = input.nextInt();
        System.out.print("\nEnter year of due date: ");
        int year = input.nextInt();
        System.out.print("\nEnter completion weighting: ");
        int weight = input.nextInt();
        Task newTask = new LeafTask(name, description, new Date(day, month, year), weight);
        addTask(newTask);
        input.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: adds the given task to the currently selected task or project
    private void addTask(Task task) {
        if (currentTask == null) {
            addTaskToRoot(task);
        } else {
            addTaskToCurrentTask(task);
        }
    }

    // REQUIRES: currentTask != null
    // MODIFIES: this
    // EFFECTS: adds the given task to the currently selected task
    private void addTaskToCurrentTask(Task subtask) {
        if (utilities.isLeafTask(currentTask)) {
            addTaskToCurrentLeafTask(subtask);
        } else {
            BranchTask currentBranchTask = ((BranchTask) currentTask);
            if (!utilities.containsTaskWithName(currentBranchTask.getSubtasks(), subtask.getName())) {
                currentBranchTask.addSubtask(subtask);
            } else {
                System.out.println("There is already a task with the given name at the current level");
            }
        }
    }

    // REQUIRES: currentTask != null and currentTask has actual type LeafTask
    // MODIFIES: this
    // EFFECTS: adds the given task to the currently selected task by removing the existing
    //          leaf task and creating a new branch task with the given task as a subtask
    private void addTaskToCurrentLeafTask(Task subtask) {
        // boolean taskStackOriginallyEmpty = taskStack.isEmpty();
        String existingName = currentTask.getName();
        String existingDescription = currentTask.getDescription();
        removeTaskSkipSideEffects();
        ArrayList<Task> subtaskList = new ArrayList<>();
        subtaskList.add(subtask);
        BranchTask newBranchTask = new BranchTask(existingName, existingDescription, subtaskList);
        if (taskStack.isEmpty()) {
            addTaskToRoot(newBranchTask);
        } else {
            BranchTask parentTask = (BranchTask) taskStack.get(taskStack.size() - 1);
            parentTask.addSubtask(newBranchTask);
        }
        currentTask = newBranchTask;
    }

    // REQUIRES: currentProject != null
    // MODIFIES: this
    // EFECTS: adds the given task to the project root
    //         if there is already a task with the same name, do nothing
    private void addTaskToRoot(Task task) {
        if (!utilities.containsTaskWithName(currentProject.getTasks(), task.getName())) {
            currentProject.addTask(task);
        } else {
            System.out.println("There is already a task with that name at the current level");
        }
    }
    
    // REQUIRES: currentTask != null
    // MODIFIES: this
    // EFFECTS: removes the currently selected task and moves currently selected task
    //          to the parent task or project root 
    private void removeTask() {
        if (taskStack.isEmpty()) {
            currentProject.removeTask(currentTask);
            currentTask = null;
        } else {
            BranchTask parentTask = (BranchTask) taskStack.get(taskStack.size() - 1);
            if (parentTask.getSubtasks().size() > 1) {
                parentTask.removeSubtask(currentTask);
                currentTask = parentTask;
                taskStack.remove(taskStack.size() - 1);
            } else {
                convertParentToLeaf();
            }
        }
    }

    // REQUIRES: currentTask != null
    // MODIFIES: this
    // EFFECTS: removes the currently selected task, but does not convert a resulting parent branch task
    //          with no subtasks back into a leaf task, does not change the currently selected task 
    //          to the parent task, and does not remove the parent task from the task stack
    //          intended for use with addTask() only
    private void removeTaskSkipSideEffects() {
        if (taskStack.isEmpty()) {
            currentProject.removeTask(currentTask);
        } else if (((BranchTask) taskStack.get(taskStack.size() - 1)).getSubtasks().size() > 1) {
            ((BranchTask) taskStack.get(taskStack.size() - 1)).removeSubtask(currentTask);
        } else {
            BranchTask parentTask = (BranchTask) taskStack.get(taskStack.size() - 1);
            parentTask.removeSubtask(currentTask);
            currentTask = parentTask;
        }
    }


    // REQUIRES: taskStack.size() > 0 and last element of taskStack has actual type BranchTask
    // MODIFIES: this
    // EFFECTS: converts the parent task of the currently selected class from BranchTask back to 
    //          LeafTask, prompting user for missing details of due date and weight and removing
    //          existing subtasks
    private void convertParentToLeaf() {
        System.out.println("As the parent task will no longer have any subtasks, "
            + "please provide missing details");
        System.out.print("Enter day of due date: ");
        int day = input.nextInt();
        System.out.print("Enter month of due date: ");
        int month = input.nextInt();
        System.out.print("Enter year of due date: ");
        int year = input.nextInt();
        System.out.print("Enter weight: ");
        int weight = input.nextInt();
        Date date = new Date(day, month, year);

        BranchTask parentTask = (BranchTask) taskStack.get(taskStack.size() - 1);
        LeafTask newLeafTask = new LeafTask(parentTask.getName(), parentTask.getDescription(), date, weight);
        if (taskStack.size() == 1) {
            currentProject.removeTask(parentTask);
            currentProject.addTask(newLeafTask);
        } else {
            BranchTask parentParentTask = (BranchTask) taskStack.get(taskStack.size() - 2);
            parentParentTask.removeSubtask(parentTask);
            parentParentTask.addSubtask(newLeafTask);
        }
        currentTask = newLeafTask;
        taskStack.remove(taskStack.size() - 1);
    }

    // MODIFIES: this
    // EFFECTS: selects a task as the currently selected task
    private void selectTask() {
        System.out.print("Enter the name of the task you wish to select: ");
        String name = input.nextLine();
        boolean successful = false;
        if (currentTask != null) {
            successful = selectTaskFromCurrentTask(name);
        } else {
            successful = selectTaskFromRoot(name);
        }
        if (!successful) {
            System.out.println("There was no task with the given name");
        }
    }

    // MODIFIES: this
    // EFFECTS: selects the task with the given name from the task's direct subtasks
    //          returns true if task was found and removed, otherwise false
    private boolean selectTaskFromCurrentTask(String name) {
        for (Task task : ((BranchTask) currentTask).getSubtasks()) {
            if (task.getName().equals(name)) {
                taskStack.add(currentTask);
                currentTask = task;
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: selects the task with the given name from the project's direct subtasks
    //          returns true if task was found and removed, otherwise false
    private boolean selectTaskFromRoot(String name) {
        for (Task task : currentProject.getTasks()) {
            if (task.getName().equals(name)) {
                currentTask = task;
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: selects the parent task of the currently selected task, or
    //          set currently selected task to null if parent is project root
    //          removes the parent task from the task stack
    private void selectParentTask() {
        if (taskStack.isEmpty()) {
            currentTask = null;
        } else {
            currentTask = taskStack.get(taskStack.size() - 1);
            taskStack.remove(taskStack.size() - 1);
        }
    }

    // MODIFIES: this
    // EFFECTS: edits the currently selected task's details
    private void editTask() {
        System.out.print("Enter new name: ");
        String name = input.nextLine();
        System.out.print("Enter new description: ");
        String description = input.nextLine();
        if (utilities.isLeafTask(currentTask)) {
            System.out.print("Enter new day of due date: ");
            int day = input.nextInt();
            System.out.print("Enter new month of due date: ");
            int month = input.nextInt();
            System.out.print("Enter new year of due date: ");
            int year = input.nextInt();
            System.out.print("Enter new weight: ");
            int weight = input.nextInt();
            input.nextLine();
            ((LeafTask) currentTask).setDueDate(new Date(day, month, year));
            ((LeafTask) currentTask).setWeight(weight);
        } 
        currentTask.setName(name);
        currentTask.setDescription(description);
    }

    // REQUIRES: currentTask != null
    // MODIFIES: this
    // EFFECTS: marks the selected task as complete or incomplete
    private void toggleCompletion() {
        if (!currentTask.isCompleted()) {
            currentTask.setCompletion(true);
        } else {
            currentTask.setCompletion(false);
        }
    }
}
