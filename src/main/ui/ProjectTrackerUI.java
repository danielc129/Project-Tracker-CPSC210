package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.ProjectList;
import ui.panels.AddProjectDialog;
import ui.panels.ProjectListView;

// ATTRIBUTION: SmartHome
// ATTRIBUTION: Oracle Java Swing Components Tutorial
// ATTRIBUTION: EdX Project Phase 3 Page
public class ProjectTrackerUI extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    private ProjectList projectList;
    private ProjectListView projectListView;

    public static void main(String[] args) {
        new ProjectTrackerUI();
    }

    // MODIFIES: this
    // EFFECTS: Creates the project tracker UI application
    private ProjectTrackerUI() {
        super("Project Tracker");
        projectList = new ProjectList();

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
        menu.add(loadMenuItem);
        JMenuItem saveMenuItem = new JMenuItem("Save projects");
        menu.add(saveMenuItem);

        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: opens a dialog allowing user to add a project
    public void showAddProjectDialog() {
        new AddProjectDialog(this);
    }

    // MODIFIES: this
    // EFFECTS: adds a project
    public void addProject(String name, String description) {
        System.out.println(name + description);
    }

}
