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
    // EFFECTS: processes user input
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

    // REQUIRES: currentProject != null
    // MODIFIES: this
    // EFFECTS: processes user command 
    private void processCommand(String command) {
        if (!(currentTask == null)) {
            processCommandSelected(command);
        } else {
            processCommandUnselected(command);
        }
    }

    // REQUIRES: currentTask != null, currentProject != null
    // MODIFIES: this
    // EFFECTS: processes user command when there is a selected task
    @SuppressWarnings("methodlength")
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
            case "d":
                viewDescription();
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

    // REQUIRES: currentTask is null, currentProject != null
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

    // EFFECTS: displays current project, list of tasks, and currently selected task (if applicable) to user
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
            System.out.println("\td -> view description");
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

    // EFFECTS: prompts the user to create a new Date, ensuring values are valid
    private Date promptForDueDate() {
        int day;
        int month;
        int year;
        boolean retry = false;
        do {
            if (retry) {
                System.out.println("Invalid date. Please try again");
            }
            System.out.print("\nEnter day of due date: ");
            day = input.nextInt();
            System.out.print("\nEnter month of due date (1-12): ");
            month = input.nextInt();
            System.out.print("\nEnter year of due date: ");
            year = input.nextInt();
            retry = true;
        } while (!isDateValid(day, month, year));
        input.nextLine();
        return new Date(day, month, year);
    }

    // EFFECTS: checks if the given day, month, and year values represent a valid date
    private boolean isDateValid(int day, int month, int year) {
        if (month < 1 || month > 12) {
            return false;
        }

        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                if (day < 1 || day > 31) {
                    return false;
                }
                break;
            case 4: case 6: case 9: case 11:
                if (day < 1 || day > 30) {
                    return false;
                }
            case 2:
                if ((isLeapYear(year) && (day < 1 || day > 29)) 
                        || (!isLeapYear(year) && (day < 1 || day > 28))) {
                    return false;
                }
        }
        return true;
    }

    // EFFECTS: returns true if the given year is a leap year
    private boolean isLeapYear(int year) {
        return (year % 4 == 0) && !((year % 100 == 0) && (year % 400 != 0));
    }

    // REQUIRES: currentProject != null
    // MODIFIES: this
    // EFFECTS: adds a task to the currently selected task or project (if no task selected)
    private void addTask() {
        System.out.print("Enter name of task: ");
        String name = input.nextLine();
        System.out.print("\nEnter task description: ");
        String description = input.nextLine();
        Date dueDate = promptForDueDate();
        System.out.print("\nEnter completion weighting: ");
        int weight = input.nextInt();
        input.nextLine();
        Task newTask = new LeafTask(name, description, dueDate, weight);
        addTask(newTask);
    }

    // REQUIRES: currentProject != null
    // MODIFIES: this
    // EFFECTS: adds the given task to the currently selected task or project (if no task selected)
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
    //          if there is already a task with the same name at the current level, do nothing
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

    // REQUIRES: currentTask != null, currentProject != null, and currentTask has actual type LeafTask
    // MODIFIES: this
    // EFFECTS: adds the given task to the currently selected leaf task by removing the existing
    //          leaf task and creating a new branch task with the given task as a subtask
    private void addTaskToCurrentLeafTask(Task subtask) {
        String existingName = currentTask.getName();
        String existingDescription = currentTask.getDescription();
        ArrayList<Task> subtaskList = new ArrayList<>();
        subtaskList.add(subtask);
        BranchTask newBranchTask = new BranchTask(existingName, existingDescription, subtaskList);

        removeTaskSkipSideEffects();

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
    // EFFECTS: adds the given task to the project root
    //         if there is already a task with the same name in the project root level, do nothing
    private void addTaskToRoot(Task task) {
        if (!utilities.containsTaskWithName(currentProject.getTasks(), task.getName())) {
            currentProject.addTask(task);
        } else {
            System.out.println("There is already a task with that name at the current level");
        }
    }
    
    // REQUIRES: currentTask != null, currentProject != null
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

    // REQUIRES: currentTask != null, currentProject != null
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


    // REQUIRES: currentProject != null, taskStack.size() > 0, and last element of taskStack has actual type BranchTask
    // MODIFIES: this
    // EFFECTS: converts the parent task of the currently selected class from BranchTask back to 
    //          LeafTask, prompting user for missing details of due date and weight, and removes
    //          any existing subtasks
    //          selects the parent task (now a leaf task) as the current task and removes last element
    //          of task stack
    private void convertParentToLeaf() {
        System.out.println("As the parent task will no longer have any subtasks, "
                + "please provide missing details");
        Date date = promptForDueDate();
        int weight = input.nextInt();

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

    // REQUIRES: currentProject != null
    // MODIFIES: this
    // EFFECTS: selects a task as the currently selected task
    private void selectTask() {
        System.out.print("Enter the name of the task you wish to select: ");
        String name = input.nextLine();
        boolean successful = false;

        if (currentTask == null) {
            successful = selectTaskFromRoot(name);
        } else {
            if (utilities.isLeafTask(currentTask)) {
                System.out.println("The currently selected task has no subtasks");
                return;
            } else {
                successful = selectTaskFromCurrentTask(name);
            }
        }
        if (!successful) {
            System.out.println("There was no task with the given name");
        }
    }

    // REQUIRES: currentTask != null, actual type of currentTask is BranchTask
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

    // REQUIRES: currentProject != null
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

    // REQUIRES: currentTask != null
    // MODIFIES: this
    // EFFECTS: selects the parent task of the currently selected task, or
    //          sets currently selected task to null if parent is project root
    //          removes the parent task from the task stack
    private void selectParentTask() {
        if (taskStack.isEmpty()) {
            currentTask = null;
        } else {
            currentTask = taskStack.get(taskStack.size() - 1);
            taskStack.remove(taskStack.size() - 1);
        }
    }

    // REQUIRES: currentTask != null
    // MODIFIES: this
    // EFFECTS: edits the currently selected task's details
    private void editTask() {
        System.out.print("Enter new name (blank to leave unchanged): ");
        String name = input.nextLine();
        System.out.print("Enter new description (blank to leave unchanged): ");
        String description = input.nextLine();
        if (utilities.isLeafTask(currentTask)) {
            System.out.print("Do you wish to change the due date? (yes, no): ");
            String changeDateInput = input.nextLine();
            if (changeDateInput.equals("yes")) {
                Date newDate = promptForDueDate();
                ((LeafTask) currentTask).setDueDate(newDate);
            }
            System.out.print("Enter new weight (blank to leave unchanged): ");
            String weightInput = input.nextLine();
            if (!weightInput.isBlank()) { 
                int weight = Integer.parseInt(weightInput);
                ((LeafTask) currentTask).setWeight(weight);
            }
        }
        if (!name.isBlank()) { 
            currentTask.setName(name);
        }
        if (!description.isBlank()) {
            currentTask.setDescription(description);
        }
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

    // REQUIRES: currentTask != null
    // EFFECTS: prints the full description of the currently selected task
    private void viewDescription() {
        System.out.println(currentTask.getDescription());
    }
}
