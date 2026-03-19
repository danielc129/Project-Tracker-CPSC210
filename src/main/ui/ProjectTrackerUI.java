package ui;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import model.BranchTask;
import model.Date;
import model.LeafTask;
import model.Project;
import model.ProjectList;
import model.Task;
import model.Utilities;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.panels.AddProjectDialog;
import ui.panels.AddTaskDialog;
import ui.panels.EditTaskDialog;
import ui.panels.ProjectListView;
import ui.panels.ProjectView;

// ATTRIBUTION: SmartHome
// ATTRIBUTION: Oracle Java Swing Components Tutorial
// ATTRIBUTION: EdX Project Phase 3 Page
// Project tracker GUI application
public class ProjectTrackerUI extends JFrame {
    private static final String JSON_STORE = "./data/projectlist.json";
    private static final String WINDOW_NAME = "Project Tracker";
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    private ProjectList projectList;
    private ProjectListView projectListView;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Utilities utilities;

    public static void main(String[] args) {
        new ProjectTrackerUI();
    }

    // EFFECTS: Creates the project tracker UI application
    private ProjectTrackerUI() {
        super(WINDOW_NAME);
        projectList = new ProjectList();
        utilities = new Utilities();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createMenu();

        projectListView = new ProjectListView(this);
        setContentPane(projectListView);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: returns ProjectList object controlled by this UI
    public ProjectList getProjectList() {
        return projectList;
    }

    // MODIFIES: this
    // EFFECTS: creates the top menu with options to load and save
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Files");
        menuBar.add(menu);

        JMenuItem loadMenuItem = new JMenuItem("Load saved projects");
        loadMenuItem.addActionListener(e -> loadFromFile());
        menu.add(loadMenuItem);
        JMenuItem saveMenuItem = new JMenuItem("Save projects");
        saveMenuItem.addActionListener(e -> saveToFile());
        menu.add(saveMenuItem);

        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: loads project list from file and updates project list view
    private void loadFromFile() {
        try {
            projectList = jsonReader.read();
            projectListView = new ProjectListView(this);
            setContentPane(projectListView);
            revalidate();
            repaint();
        } catch (IOException e) {
            System.out.println("Unable to load from file");
        }
    }

    // EFFECTS: saves the current state of the project list (all of the projects and their tasks)
    //          to JSON_STORE
    private void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(projectList);
            jsonWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to save to file");
        }
    }

    // EFFECTS: opens a dialog allowing user to add a project
    public void showAddProjectDialog() {
        new AddProjectDialog(this);
    }

    // EFFECTS: opens a dialog allowing user to add a root-level task to the given project
    public void showAddProjectLevelTaskDialog(Project project) {
        new AddTaskDialog(this, project, null, true);
    }

    // EFFECTS: opens a dialog allowing user to add a subtask to the given task
    public void showAddSubtaskDialog(Project project, Task task) {
        new AddTaskDialog(this, project, task, false);
    }

    // EFFECTS: opens a dialog allowing user to edit the given task
    public void showEditTaskDialog(Project project, Task task) {
        new EditTaskDialog(this, project, task);
    }

    // MODIFIES: this
    // EFFECTS: adds a project and updates project list view
    public void addProject(String name, String description) {
        Project newProject = new Project(name, description);
        projectList.addProject(newProject);
        updateProjectListView();
    }

    // MODIFIES: this
    // EFFECTS: removes the given project from the project list and updates project list view
    public void removeProject(Project project) {
        projectList.removeProject(project);

        updateProjectListView();
    }

    // MODIFIES: this
    // EFFECTS: selects the given project and switches panel to project view
    public void selectProject(Project project) {
        updateProjectView(project);
    }

    // MODIFIES: this, project 
    // EFFECTS: adds a leaf task with the given name, description, weight, and due date as a 
    //          root-level task to the given project
    //          updates project view and sets it as the content pane
    public void addTaskToRoot(Project project, String name, String description, int weight, Date dueDate) {
        LeafTask newTask = new LeafTask(name, description, dueDate, weight, 0, null);
        project.addTask(newTask);
        
        updateProjectView(project);
    }

    // MODIFIES: this, project, task
    // EFFECTS: adds a leaf task with the given name, description, weight, and due date 
    //          as a subtask to the given task. If given task is a leaf task, converts it
    //          to branch task. 
    //          updates project view 
    public void addSubtask(Project project, Task task, String name, String description, int weight, Date dueDate) {
        LeafTask newTask = new LeafTask(name, description, dueDate, weight, task.getDepth() + 1, task);
        if (utilities.isLeafTask(task)) {
            ArrayList<Task> subtaskList = new ArrayList<>();
            subtaskList.add(newTask);
            BranchTask newBranchTask = new BranchTask(task.getName(), task.getDescription(), 
                    subtaskList, task.getDepth(), task.getParentTask());
            newTask.setParentTask(newBranchTask);
            if (task.getParentTask() == null) {
                project.removeTask(task);
                project.addTask(newBranchTask);
            } else {
                BranchTask parentTask = (BranchTask) (task.getParentTask());
                parentTask.removeSubtask(task);
                parentTask.addSubtask(newBranchTask);
            }
        } else {
            ((BranchTask) task).addSubtask(newTask);
        }

        updateProjectView(project);
    }

    // MODIFIES: this, project
    // EFFECTS: removes the selected task and updates project view
    public void removeTask(Project project, Task task) {
        if (task.getParentTask() == null) {
            project.removeTask(task);
        } else {
            BranchTask parentTask = (BranchTask) task.getParentTask();
            if (parentTask.getSubtasks().size() > 1) {
                parentTask.removeSubtask(task);
            } else {
                if (parentTask.getParentTask() == null) {
                    project.removeTask(parentTask);
                    addTaskToRoot(project, parentTask.getName(), parentTask.getDescription(), 
                            task.getWeight(), task.getDueDate());
                } else {
                    ((BranchTask) parentTask.getParentTask()).removeSubtask(parentTask);
                    addSubtask(project, parentTask.getParentTask(), parentTask.getName(), 
                            parentTask.getDescription(), task.getWeight(), task.getDueDate());
                }
            }
        }
        updateProjectView(project);
    }

    // MODIFIES: this, project, task
    // EFFECTS: toggles the completion status of the given task
    //          updates project view
    public void toggleCompletion(Project project, Task task) {
        if (!task.isCompleted()) {
            task.setCompletion(true);
        } else {
            task.setCompletion(false);
        }
        updateProjectView(project);
    }

    // MODIFIES: this, project, task
    // EFFECTS: updates the given leaf task with the given name, description, weight, and due date
    //          updates project view
    public void editLeafTask(Project project, LeafTask task, String name, 
            String description, int weight, Date dueDate) {
        task.setName(name);
        task.setDescription(description);
        task.setWeight(weight);
        task.setDueDate(dueDate);
        updateProjectView(project);
    }

    // MODIFIES: this, project, task
    // EFFECTS: updates the given branch task with the given name and description
    //          updates project view
    public void editBranchTask(Project project, BranchTask task, String name, String description) {
        task.setName(name);
        task.setDescription(description);
        updateProjectView(project);
    }

    // MODIFIES: this
    // EFFECTS: updates project list view and sets it as the content pane
    public void updateProjectListView() {
        ProjectListView newProjectListView = new ProjectListView(this);
        setContentPane(newProjectListView);
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: updates project view to show the given project and sets it as the content pane
    public void updateProjectView(Project project) {
        ProjectView newProjectView = new ProjectView(this, project);
        setContentPane(newProjectView);
        revalidate();
        repaint();
    }
}
