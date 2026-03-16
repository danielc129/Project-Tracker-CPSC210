package ui.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Project;
import model.Task;
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

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new GridBagLayout());

        JButton addTaskToRootButton = new JButton("Add Project-Level Task");
        addTaskToRootButton.addActionListener(e -> controller.showAddProjectLevelTaskDialog(project));
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 0;
        gbConstraints.gridwidth = 1;
        taskPanel.add(addTaskToRootButton, gbConstraints);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("there should be something after this");
        for (Task task : project.getDescendantTasks()) {
            listModel.addElement(task.getStringFormatNoSubtasks());
            listModel.addElement("test");
        }
        for (String text : List.of("[ ] another task: (Due: March 15, 2026 | Weight: 5)")) {
            listModel.addElement(text);
        }

        System.out.println(listModel);
        JList taskList = new JList<>(listModel);
        JScrollPane listScroller = new JScrollPane(taskList);
        listScroller.setPreferredSize(new Dimension(250, 150));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.CENTER;
        taskPanel.add(listScroller, gbConstraints);
        System.out.println(taskList.getModel().getSize());
        
        taskList.revalidate();
        taskList.repaint();
        listScroller.revalidate();
        listScroller.repaint();

        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        gbConstraints.anchor = GridBagConstraints.CENTER;
        add(taskPanel, gbConstraints);

        taskPanel.revalidate();
        taskPanel.repaint();

    }
}
