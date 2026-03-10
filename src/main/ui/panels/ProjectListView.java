package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.ProjectTrackerUI;

// ATTRIBUTION: Oracle Java Swing Components Tutorial
public class ProjectListView extends JPanel {

    public ProjectListView(ProjectTrackerUI controller) {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints gbConstraints = new GridBagConstraints();

        if (controller.getProjectList().getProjects().isEmpty()) {
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 0;
            gbConstraints.anchor = GridBagConstraints.PAGE_START;
            gbConstraints.weighty = 5;
            JLabel noProjectsText = new JLabel("There are no projects added");
            add(noProjectsText, gbConstraints);

            JButton addProjectButton = new JButton("Add Project");
            gbConstraints.gridy = 1;
            gbConstraints.weighty = 1;
            gbConstraints.anchor = GridBagConstraints.PAGE_END;
            add(addProjectButton, gbConstraints);
            addProjectButton.addActionListener(e -> controller.showAddProjectDialog());
        }
    }
}
