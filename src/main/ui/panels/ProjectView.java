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
    private JPanel taskPanel;
    private JPanel buttonPanel;
    private JLabel descriptionText;
    private JList taskList;
    private boolean alreadyShownSelectionOptions;

    public ProjectView(ProjectTrackerUI controller, Project project) {
        super();
        this.controller = controller;
        this.project = project;
        setLayout(new GridBagLayout());
        alreadyShownSelectionOptions = false;
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JButton exitProjectButton = new JButton("Exit Project");
        exitProjectButton.addActionListener(e -> controller.switchToProjectListView());
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.LINE_START;
        add(exitProjectButton, gbConstraints);

        JLabel headerText = new JLabel("Selected project: " + project.getName());
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        gbConstraints.gridwidth = 2;
        gbConstraints.weighty = 1;
        add(headerText, gbConstraints);

        taskPanel = new JPanel();
        taskPanel.setLayout(new GridBagLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        JButton addTaskToRootButton = new JButton("Add Project-Level Task");
        addTaskToRootButton.addActionListener(e -> controller.showAddProjectLevelTaskDialog(project));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.gridwidth = 1;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(addTaskToRootButton, gbConstraints);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Task task : project.getDescendantTasks()) {
            listModel.addElement(task.getStringFormatNoSubtasksNoDescription());
        }

        taskList = new JList<>(listModel);
        taskList.addListSelectionListener(e -> showSelectedTaskActions());
        JScrollPane listScroller = new JScrollPane(taskList);
        listScroller.setPreferredSize(new Dimension(400, 200));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.weightx = 5;
        gbConstraints.anchor = GridBagConstraints.CENTER;
        taskPanel.add(listScroller, gbConstraints);

        gbConstraints.gridx = 1;
        gbConstraints.gridy = 0;
        gbConstraints.weightx = 1;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        taskPanel.add(buttonPanel, gbConstraints);
        
        taskList.revalidate();
        taskList.repaint();
        listScroller.revalidate();
        listScroller.repaint();

        gbConstraints.weightx = 1;
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        add(taskPanel, gbConstraints);

        taskPanel.revalidate();
        taskPanel.repaint();

    }

    private Task getSelectedTask() {
        int index = taskList.getSelectedIndex();
        return project.getDescendantTasks().get(index);
    }

    // MODIFIES: this
    // EFFECTS: shows the actions available when a task is selected
    private void showSelectedTaskActions() {
        if (!alreadyShownSelectionOptions) {
            alreadyShownSelectionOptions = true;
            GridBagConstraints gbConstraints = new GridBagConstraints();
            JButton addSubtaskButton = new JButton("Add Subtask");
            addSubtaskButton.addActionListener(e -> controller.showAddSubtaskDialog(project, getSelectedTask()));
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 1;
            gbConstraints.anchor = GridBagConstraints.PAGE_START;
            gbConstraints.fill = GridBagConstraints.HORIZONTAL;
            buttonPanel.add(addSubtaskButton, gbConstraints);


            JButton removeTaskButton = new JButton("Remove Task");
            removeTaskButton.addActionListener(e -> controller.removeTask(project, getSelectedTask()));
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 2;
            buttonPanel.add(removeTaskButton, gbConstraints);


            JButton toggleCompletionButton = new JButton("Toggle Completion");
            toggleCompletionButton.addActionListener(e -> controller.toggleCompletion(project, getSelectedTask()));
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 3;
            buttonPanel.add(toggleCompletionButton, gbConstraints);

            JButton editTaskButton = new JButton("Edit Task");
            editTaskButton.addActionListener(e -> controller.showEditTaskDialog(project, getSelectedTask()));
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 4;
            buttonPanel.add(editTaskButton, gbConstraints);

            buttonPanel.revalidate();
            buttonPanel.repaint();
            taskPanel.revalidate();
            taskPanel.repaint();

            descriptionText = new JLabel();
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 3;
            gbConstraints.anchor = GridBagConstraints.CENTER;
            gbConstraints.fill = GridBagConstraints.BOTH;
            add(descriptionText, gbConstraints);
        }
        
        descriptionText.setText("            Task Description: " + getSelectedTask().getDescription());
    }
}
