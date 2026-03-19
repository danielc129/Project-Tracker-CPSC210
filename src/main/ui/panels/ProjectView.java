package ui.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Project;
import model.Task;
import ui.ProjectTrackerUI;

// ATTRIBUTION: Oracle Java Swing Components Tutorial
// The project view screen for the project tracker UI
// Displays a project and its tasks, along with related actions
public class ProjectView extends JPanel {
    private ProjectTrackerUI controller;
    private Project project;
    private JPanel taskPanel;
    private JPanel buttonPanel;
    private JLabel descriptionText;
    private JList<String> taskList;
    private boolean alreadyShownSelectionOptions;

    // EFFECTS: Creates a project view for the given project, with 
    //          the given base UI controller 
    public ProjectView(ProjectTrackerUI controller, Project project) {
        super();
        this.controller = controller;
        this.project = project;
        setLayout(new GridBagLayout());
        alreadyShownSelectionOptions = false;

        addHeaderComponents();
        addTaskPanel();

    }

    // MODIFIES: this
    // EFFECTS: adds exit project button and selected project label to this JPanel
    private void addHeaderComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JButton exitProjectButton = new JButton("Exit Project");
        exitProjectButton.addActionListener(e -> controller.updateProjectListView());
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
    }

    // MODIFIES: this
    // EFFECTS: adds task panel components (task list, task-related buttons)
    private void addTaskPanel() {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        taskPanel = new JPanel();
        taskPanel.setLayout(new GridBagLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        addAddProjectLevelTaskButton();

        addTaskList(gbConstraints);

        gbConstraints.weightx = 1;
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        add(taskPanel, gbConstraints);

        taskPanel.revalidate();
        taskPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: adds the button to add a project-level task 
    private void addAddProjectLevelTaskButton() {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        JButton addTaskToRootButton = new JButton("Add Project-Level Task");
        addTaskToRootButton.addActionListener(e -> controller.showAddProjectLevelTaskDialog(project));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.gridwidth = 1;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(addTaskToRootButton, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds the list view that shows the tasks in this project
    private void addTaskList(GridBagConstraints gbConstraints) {
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
    }


    // EFFECTS: returns the currently selected task in the task list view
    private Task getSelectedTask() {
        int index = taskList.getSelectedIndex();
        return project.getDescendantTasks().get(index);
    }

    // REQUIRES: task is within project
    // MODIFIES: this
    // EFFECTS: selects the given task in the list of tasks
    public void selectTaskInList(Task task) {
        int index = project.getDescendantTasks().indexOf(task);
        taskList.setSelectedIndex(index);
    }

    // MODIFIES: this
    // EFFECTS: shows the actions available when a task is selected
    private void showSelectedTaskActions() {
        if (!alreadyShownSelectionOptions) {
            GridBagConstraints gbConstraints = new GridBagConstraints();
            alreadyShownSelectionOptions = true;
            addAddSubtaskButton();
            addRemoveTaskButton();
            addToggleCompletionButton();
            addEditTaskButton();

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

    // MODIFIES: this
    // EFFECTS: adds the button to add a subtask 
    private void addAddSubtaskButton() {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        JButton addSubtaskButton = new JButton("Add Subtask");
        addSubtaskButton.addActionListener(e -> controller.showAddSubtaskDialog(project, getSelectedTask()));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(addSubtaskButton, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds the button to remove a task
    private void addRemoveTaskButton() {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        JButton removeTaskButton = new JButton("Remove Task");
        removeTaskButton.addActionListener(e -> controller.removeTask(project, getSelectedTask()));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(removeTaskButton, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds the button to toggle completion of a task
    private void addToggleCompletionButton() {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        JButton toggleCompletionButton = new JButton("Toggle Completion");
        toggleCompletionButton.addActionListener(e -> controller.toggleCompletion(project, getSelectedTask()));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 3;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(toggleCompletionButton, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds the button to edit a task
    private void addEditTaskButton() {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        JButton editTaskButton = new JButton("Edit Task");
        editTaskButton.addActionListener(e -> controller.showEditTaskDialog(project, getSelectedTask()));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 4;
        gbConstraints.anchor = GridBagConstraints.PAGE_START;
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(editTaskButton, gbConstraints);
    }
}
