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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import model.Project;
import ui.ProjectTrackerUI;

// ATTRIBUTION: Oracle Java Swing Components Tutorial
public class ProjectListView extends JPanel {   
    private JList projectJList;
    private List<Project> projectListObjects;
    private ProjectTrackerUI controller;
    private boolean alreadyDisplayedSelectionOptions;
    private JLabel descriptionText;

    public ProjectListView(ProjectTrackerUI controller) {
        super();    
        this.controller = controller;
        this.alreadyDisplayedSelectionOptions = false;
        setLayout(new GridBagLayout());
        GridBagConstraints gbConstraints = new GridBagConstraints();

        if (controller.getProjectList().getProjects().isEmpty()) {
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
        } else {    
            JLabel headerText = new JLabel("Project List");
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 0;
            gbConstraints.anchor = GridBagConstraints.PAGE_START;
            gbConstraints.gridwidth = 3;
            gbConstraints.weighty = 1;
            add(headerText, gbConstraints);

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
    // EFFECTS: shows the select project, remove project options when a project is selected in the list
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

            descriptionText = new JLabel();
            gbConstraints.gridx = 3;
            gbConstraints.gridy = 1;
            gbConstraints.anchor = GridBagConstraints.PAGE_START;
            add(descriptionText, gbConstraints);

            revalidate();
            repaint();
        }

        descriptionText.setText("Project Description: " + projectListObjects.get(projectIndex).getDescription());
    }   
}
