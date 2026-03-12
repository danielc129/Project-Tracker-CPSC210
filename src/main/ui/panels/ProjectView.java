package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Project;
import ui.ProjectTrackerUI;

public class ProjectView extends JPanel{
    private ProjectTrackerUI controller;
    private Project project;

    public ProjectView(ProjectTrackerUI controller, Project project) {
        super();
        this.controller = controller;
        setLayout(new GridBagLayout());
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel headerText = new JLabel("Selected project: " + project.getName());
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        gbConstraints.gridwidth = 2;
        gbConstraints.weighty = 1;
        add(headerText, gbConstraints);

    }
}
