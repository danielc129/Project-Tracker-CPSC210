package ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.ProjectList;

// ATTRIBUTION: SmartHome
// ATTRIBUTION: Oracle Java Swing Components Tutorial
// ATTRIBUTION: EdX Project Phase 3 Page
public class ProjectTrackerUI extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    private ProjectList projectList;

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

        JLabel testText = new JLabel("Test");
        add(testText);

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
}
