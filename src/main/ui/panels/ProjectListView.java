package ui.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Project;
import ui.ProjectTrackerUI;

// ATTRIBUTION: SmartHome
// ATTRIBUTION: Oracle Java Swing Components Tutorial
// ATTRIBUTION: EdX Project Phase 3 Page
// The project list view screen for the project tracker UI
// Displays a list of projects and actions pertaining to projects
@ExcludeFromJacocoGeneratedReport
public class ProjectListView extends JPanel {
    private JList<Object> projectJList;
    private List<Project> projectListObjects;
    private ProjectTrackerUI controller;
    private boolean alreadyDisplayedSelectionOptions;
    private JLabel descriptionText;
    private JLabel completionText;

    // EFFECTS: creates a new project list view with the given base UI controller
    public ProjectListView(ProjectTrackerUI controller) {
        this.controller = controller;
        this.alreadyDisplayedSelectionOptions = false;
        setLayout(new GridBagLayout());
        GridBagConstraints gbConstraints = new GridBagConstraints();

        if (controller.getProjectList().getProjects().isEmpty()) {
            addComponentsNoProjects();
        } else {
            JLabel headerText = new JLabel("Project List");
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 0;
            gbConstraints.anchor = GridBagConstraints.PAGE_START;
            gbConstraints.gridwidth = 3;
            gbConstraints.weighty = 1;
            add(headerText, gbConstraints);

            addTaskList(gbConstraints);

            JButton addProjectButton = new JButton("Add Project");
            gbConstraints.gridy = 2;
            gbConstraints.weighty = 1;
            gbConstraints.gridwidth = 1;
            gbConstraints.anchor = GridBagConstraints.PAGE_END;
            add(addProjectButton, gbConstraints);
            addProjectButton.addActionListener(e -> controller.showAddProjectDialog());
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the select project and remove project buttons when a project is
    // selected in the list
    private void showProjectSelectionOptions(int projectIndex) {
        if (!alreadyDisplayedSelectionOptions) {
            alreadyDisplayedSelectionOptions = true;

            JButton selectProjectButton = new JButton("Select Project");
            selectProjectButton.addActionListener(e -> controller.selectProject(projectListObjects.get(projectIndex)));
            GridBagConstraints gbConstraints = new GridBagConstraints();
            gbConstraints.gridx = 1;
            gbConstraints.gridy = 2;
            gbConstraints.anchor = GridBagConstraints.PAGE_END;
            add(selectProjectButton, gbConstraints);

            JButton removeProjectButton = new JButton("Remove Project");
            removeProjectButton.addActionListener(e -> controller.removeProject(projectListObjects.get(projectIndex)));
            gbConstraints.gridx = 2;
            gbConstraints.gridy = 2;
            add(removeProjectButton, gbConstraints);

            addDescriptionPanel();

            revalidate();
            repaint();
        }

        descriptionText.setText("Project Description: " + projectListObjects.get(projectIndex).getDescription());
        completionText.setText("Progress: " + projectListObjects.get(projectIndex).getCompletionPercentage() + "%");
    }

    // MODIFIES: this
    // EFFECTS: adds the components to show project description and completion percentage
    private void addDescriptionPanel() {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridBagLayout());

        descriptionText = new JLabel();
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        descriptionPanel.add(descriptionText, gbConstraints);

        completionText = new JLabel();
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 3;
        descriptionPanel.add(completionText, gbConstraints);

        gbConstraints.gridx = 3;
        gbConstraints.gridy = 1;
        add(descriptionPanel, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds the components for when there are no projects added
    private void addComponentsNoProjects() {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        gbConstraints.weighty = 5;
        JLabel noProjectsText = new JLabel("There are no projects added");
        add(noProjectsText, gbConstraints);

        ImageIcon icon = new ImageIcon("data/header_image.jpg");
        JLabel imageLabel = new JLabel(icon);
        gbConstraints.gridy = 1;
        gbConstraints.weighty = 1;
        add(imageLabel, gbConstraints);

        JButton addProjectButton = new JButton("Add Project");
        gbConstraints.gridy = 2;
        gbConstraints.weighty = 1;
        gbConstraints.anchor = GridBagConstraints.PAGE_END;
        add(addProjectButton, gbConstraints);
        addProjectButton.addActionListener(e -> controller.showAddProjectDialog());
    }

    // MODIFIES: this
    // EFFECTS: adds the list view to see the tasks in this project
    public void addTaskList(GridBagConstraints gbConstraints) {
        ArrayList<String> projectListContents = new ArrayList<>();
        projectListObjects = controller.getProjectList().getProjects();
        for (Project project : projectListObjects) {
            projectListContents.add(project.getName());
        }
        projectJList = new JList<>(projectListContents.toArray());
        projectJList.addListSelectionListener(e -> showProjectSelectionOptions(e.getFirstIndex()));
        JScrollPane listScroller = new JScrollPane(projectJList);
        listScroller.setPreferredSize(new Dimension(250, 150));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        gbConstraints.anchor = GridBagConstraints.CENTER;
        gbConstraints.weighty = 1;
        add(listScroller, gbConstraints);
    }
}
