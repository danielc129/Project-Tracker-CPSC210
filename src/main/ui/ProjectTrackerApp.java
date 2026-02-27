package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.BranchTask;
import model.Date;
import model.LeafTask;
import model.ProgressSnapshot;
import model.Project;
import model.ProjectList;
import model.Task;
import model.Utilities;
import persistence.JsonReader;
import persistence.JsonWriter;


// Project tracker application
// ATTRIBUTION: Based on Teller project 
@ExcludeFromJacocoGeneratedReport
public class ProjectTrackerApp {
    private static final String JSON_STORE = "./data/projectlist.json";
    private ProjectList projectList;
    private Project currentProject;
    private Task currentTask;
    private Scanner input;
    private Utilities utilities;
    private List<Task> taskStack;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the project tracker application
    public ProjectTrackerApp() {
        runProjectTracker();
    }

    // MODIFIES: this
    // EFFECTS: initializes the application
    private void init() {
        this.input = new Scanner(System.in);
        this.projectList = new ProjectList();
        this.currentProject = null;
        this.currentTask = null;
        this.utilities = new Utilities();
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: runs the project tracker application
    private void runProjectTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            if (currentProject != null) {
                promptWithinProject();
            } else {
                promptOutsideProject();
            }

            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                if (currentProject != null) {
                    currentProject = null;
                    currentTask = null;
                } else {
                    keepGoing = false;
                }
            } else {
                processCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays information and menu options for when a project is selected
    private void promptWithinProject() {
        displayInfo();
        displayMenuWithinProject();
    }

    // MODIFIES: this
    // EFFECTS: displays information and menu options for when a project is not selected
    private void promptOutsideProject() {
        System.out.println("Welcome to Project Tracker \n");
        List<Project> projects = projectList.getProjects();
        if (projects.isEmpty()) {
            System.out.println("There are no projects added");
        } else {
            for (Project project : projects) {
                System.out.println("\t" + project.getName() + " (" 
                        + project.getCompletionPercentage() + "% completed)");
            }
            System.out.println("\nUp next:\n");
            showUpNextTasks();
        }

        System.out.println("\nSelect an option: ");
        System.out.println("\ta -> add project");
        System.out.println("\ts -> select project");
        System.out.println("\tr -> remove project");
        System.out.println("\tf -> save projects to file");
        System.out.println("\tl -> load projects from file");
        System.out.println("\tq -> exit program");
    }

    // EFFECTS: lists up to three of the leaf tasks in any project that have the closest due dates
    private void showUpNextTasks() {
        List<Project> projects = projectList.getProjects();
        List<Task> allLeafTasks = new ArrayList<>();
        for (Project project : projects) {
            allLeafTasks.addAll(project.getFlattenedTasks());
        }
        List<Task> sortedTasks = utilities.sortTasks(allLeafTasks);
        int numTasksPrinted = 0;
        for (Task currentTask : sortedTasks) {
            if (numTasksPrinted < 3 && !currentTask.isCompleted()) {
                System.out.println("\t" + currentTask.getName() 
                        + " (due " + currentTask.getDueDate().getDateAsString() + ")");
                numTasksPrinted = numTasksPrinted + 1;
            }
        }
    }

    // REQUIRES: currentProject != null
    // MODIFIES: this
    // EFFECTS: processes user command 
    private void processCommand(String command) {
        if (currentProject == null) {
            processCommandOutsideProject(command);
        } else if (!(currentTask == null)) {
            processCommandSelected(command);
        } else {
            processCommandUnselected(command);
        }
    }

    // REQUIRES: currentProject == null
    // MODIFIES: this
    // EFFECTS: processes user command when there is no project selected
    private void processCommandOutsideProject(String command) {
        switch (command) {
            case "a":
                addProject();
                break;
            case "r":
                removeProject();
                break;
            case "s":
                selectProject();
                break;
            case "f":
                saveProjectList();
                break;
            case "l":
                loadProjectList();
                break;
            default:
                System.out.println("Invalid command");
                break;
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
                break;
            case "h":
                viewProgressHistory();
                break;
            case "f":
                saveProjectList();
                break;
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
            case "h":
                viewProgressHistory();
                break;
            case "f":
                saveProjectList();
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    // REQUIRES: currentProject != null
    // EFFECTS: prints the progress history as a list of snapshots
    private void viewProgressHistory() {
        for (ProgressSnapshot snapshot : currentProject.getProgressHistory()) {
            String line = "";
            line = line + snapshot.getTime().toString();
            for (int i = line.length(); i < 40; i++) {
                line = line + " ";
            }
            line = line + snapshot.getCompletionPercentage() + "%";
            System.out.println(line);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the project
    private void addProject() {
        System.out.println("Please enter project name: ");
        String name = input.nextLine();
        System.out.println("Please enter project description: ");
        String description = input.nextLine();
        this.currentProject = new Project(name, description);
        this.currentTask = null;
        this.taskStack = new ArrayList<>();
        this.projectList.addProject(currentProject);
    }

    // MODIFIES: this
    // EFFECTS: removes a project, prompting user for name
    private void removeProject() {
        List<Project> projects = projectList.getProjects();
        boolean retry = false;
        String projectName;
        do {
            if (retry) {
                System.out.println("Invalid project name");
            }
            System.out.println("Enter name of project to remove: ");
            projectName = input.nextLine();
            retry = true;
        } while (!utilities.containsProjectWithName(projects, projectName));
        
        for (Project project : projects) {
            if (project.getName().equals(projectName)) {
                projectList.removeProject(project);
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: selects a project, prompting user for name
    private void selectProject() {
        List<Project> projects = projectList.getProjects();
        boolean retry = false;
        String projectName;
        do {
            if (retry) {
                System.out.println("Invalid project name");
            }
            System.out.println("Enter name of project to select: ");
            projectName = input.nextLine();
            retry = true;
        } while (!utilities.containsProjectWithName(projects, projectName));

        for (Project project : projects) {
            if (project.getName().equals(projectName)) {
                currentProject = project;
                break;
            }
        }

        this.taskStack = new ArrayList<>();
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

    // EFFECTS: displays menu of options when a project is selected
    private void displayMenuWithinProject() {
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
        System.out.println("\th -> view progress history");
        System.out.println("\tf -> save changes to file");
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
            try {
                System.out.print("\nEnter day of due date: ");
                day = input.nextInt();
                System.out.print("\nEnter month of due date (1-12): ");
                month = input.nextInt();
                System.out.print("\nEnter year of due date: ");
                year = input.nextInt();
            } catch (InputMismatchException e) {
                day = -1;
                month = -1;
                year = -1;
                input.nextLine();
            }
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
    //          this method updates progress history
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
        currentProject.updateProgressHistory();
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
    //          this method updates progress history
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
        currentProject.updateProgressHistory();
    }

    // REQUIRES: currentTask != null, currentProject != null
    // MODIFIES: this
    // EFFECTS: removes the currently selected task, but does not convert a resulting parent branch task
    //          with no subtasks back into a leaf task, does not change the currently selected task 
    //          to the parent task, and does not remove the parent task from the task stack
    //          intended for use with addTask() only, which will replace the task being removed
    //          with a new branch task of the same name
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

    // REQUIRES: currentTask != null, currentProject != null
    // MODIFIES: this
    // EFFECTS: edits the currently selected task's details
    //          this method updates progress history
    private void editTask() {
        System.out.print("Enter new name (blank to leave unchanged): ");
        String name = input.nextLine();
        System.out.print("Enter new description (blank to leave unchanged): ");
        String description = input.nextLine();
        editTaskDueDate();
        if (!name.isBlank()) { 
            currentTask.setName(name);
        }
        if (!description.isBlank()) {
            currentTask.setDescription(description);
        }
        currentProject.updateProgressHistory();
    }

    // REQUIRES: currentTask != null, currentProject != null
    // EFFECTS: edits the currently selected task's due date
    private void editTaskDueDate() {
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
    }

    // REQUIRES: currentTask != null, currentProject != null
    // MODIFIES: this
    // EFFECTS: marks the selected task as complete or incomplete
    //          this method updates progress history
    private void toggleCompletion() {
        if (!currentTask.isCompleted()) {
            currentTask.setCompletion(true);
        } else {
            currentTask.setCompletion(false);
        }
        currentProject.updateProgressHistory();
    }

    // REQUIRES: currentTask != null
    // EFFECTS: prints the full description of the currently selected task
    private void viewDescription() {
        System.out.println(currentTask.getDescription());
    }

    
    // EFFECTS: saves the project list to file
    private void saveProjectList() {
        try {
            jsonWriter.open();
            jsonWriter.write(projectList);
            jsonWriter.close();
            System.out.println("Saved projects to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to save to file");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadProjectList() {
        try {
            projectList = jsonReader.read();
            System.out.println("Loaded projects from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load from file");
        }
    }
}
