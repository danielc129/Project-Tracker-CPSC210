package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.BranchTask;
import model.Date;
import model.LeafTask;
import model.Project;
import model.Task;

// TODO: handle tasks with same names

// Project tracker application
// ATTRIBUTION: Based on Teller project 
public class ProjectTrackerApp {
    private Project currentProject;
    private Task currentTask;
    private Scanner input;
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
            displayList();
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
        // TODO: check if command is valid at current state
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
    }

    // EFFECTS: displays list of tasks to user
    private void displayList() {
        System.out.println("Project: " + currentProject.getName());
        System.out.println();
        List<Task> tasks = currentProject.getSortedTasks();
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks added to this project");
        } else if (currentTask != null) {
            System.out.println("Selected task: " + currentTask.getName());
            System.out.println(currentTask.getStringFormat());
        } else {
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
        }
        System.out.println("\ts -> select task");
        System.out.println("\tq -> exit project");
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
    // ATTRIBUTION: based on Ed Discussion post 184
    private void addTaskToCurrentTask(Task subtask) {
        LeafTask referenceLeafTask = new LeafTask("", "", new Date(1, 1, 1), 1);

        if (currentTask.getClass().getName().equals(referenceLeafTask.getClass().getName())) {
            String existingName = currentTask.getName();
            String existingDescription = currentTask.getDescription();
            removeTask();
            ArrayList<Task> subtaskList = new ArrayList<>();
            subtaskList.add(subtask);
            BranchTask newBranchTask = new BranchTask(existingName, existingDescription, subtaskList);
            addTask(newBranchTask);
            currentTask = newBranchTask;
        } else {
            ((BranchTask) currentTask).addSubtask(subtask);
        }
    }

    // REQUIRES: currentProject != null
    // MODIFIES: this
    // EFECTS: adds the given task to the project root
    private void addTaskToRoot(Task task) {
        currentProject.addTask(task);
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
            // TODO: remove from parent, switch currentTask to parent
        }
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

    private boolean selectTaskFromCurrentTask(String name) {
        return false;
    }

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
    // EFFECTS: edits the currently selected task's details
    private void editTask() {

    }

    // MODIFIES: this
    // EFFECTS: marks the selected task as complete or incomplete
    private void toggleCompletion() {

    }
}
