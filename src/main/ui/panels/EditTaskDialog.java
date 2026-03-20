package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.BranchTask;
import model.Date;
import model.LeafTask;
import model.Project;
import model.Task;
import model.Utilities;
import ui.ProjectTrackerUI;

public class EditTaskDialog extends JDialog {
    private ProjectTrackerUI controller;
    private Task task;
    private Project project;
    private Utilities utilities;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField weightField;
    private JTextField dueDateDayField;
    private JTextField dueDateMonthField;
    private JTextField dueDateYearField;
    private JButton editButton;

    // EFFECTS: creates a new edit task dialog
    //          when the user clicks confirm, the given controller will be 
    //          instructed to update the given task's details 
    public EditTaskDialog(ProjectTrackerUI controller, Project project, Task task) {
        super(controller, "Edit Task");
        this.controller = controller;
        this.project = project;
        this.utilities = new Utilities();
        this.task = task;
        setLocationRelativeTo(null);
        setVisible(true);

        setLayout(new GridBagLayout());

        addNameComponents();
        addDescriptionComponents();

        editButton = new JButton("Confirm");

        if (utilities.isLeafTask(task)) {
            addWeightComponents();
            addDueDateDayComponents();
            addDueDateMonthComponents();
            addDueDateYearComponents();
            setEditButtonActionListenerForLeafTask();
        } else {
            editButton.addActionListener(e -> handleEditBranchTask(nameField.getText(), descriptionField.getText()));
        }

        addEditButton();

        pack();
    }

    // MODIFIES: this
    // EFFECTS: adds the action listener to the edit button for when a leaf task is being edited
    private void setEditButtonActionListenerForLeafTask() {
        editButton.addActionListener(e -> handleEditLeafTask(nameField.getText(), descriptionField.getText(),
                            weightField.getText(),
                            dueDateDayField.getText(), 
                            dueDateMonthField.getText(),
                            dueDateYearField.getText()));
    }

    // MODIFIES: this
    // EFFECTS: adds the edit button to the dialog
    private void addEditButton() {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 6;
        gbConstraints.gridwidth = 2;
        gbConstraints.anchor = GridBagConstraints.PAGE_END;
        add(editButton, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field for task name to this dialog
    private void addNameComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel nameText = new JLabel("Name");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.weightx = 1;
        add(nameText, gbConstraints);

        nameField = new JTextField();
        nameField.setColumns(10);
        nameField.setText(task.getName());
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 0;
        gbConstraints.weightx = 2;
        add(nameField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field for task description to this dialog
    private void addDescriptionComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel descriptionText = new JLabel("Description");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        gbConstraints.weightx = 1;
        add(descriptionText, gbConstraints);

        descriptionField = new JTextField();
        descriptionField.setColumns(10);
        descriptionField.setText(task.getDescription());
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 1;
        gbConstraints.weightx = 2;
        add(descriptionField, gbConstraints);
    }
    
    // MODIFIES: this
    // EFFECTS: adds label and field for task weight to this dialog
    private void addWeightComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel weightText = new JLabel("Weight");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbConstraints.weightx = 1;
        add(weightText, gbConstraints);

        weightField = new JTextField();
        weightField.setColumns(10);
        weightField.setText(Integer.toString(task.getWeight()));
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 2;
        gbConstraints.weightx = 2;
        add(weightField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field for task due date day to this dialog
    private void addDueDateDayComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel dueDateDayText = new JLabel("Due Date Day: ");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 3;
        gbConstraints.weightx = 1;
        add(dueDateDayText, gbConstraints);

        dueDateDayField = new JTextField();
        dueDateDayField.setColumns(10);
        dueDateDayField.setText(Integer.toString(task.getDueDate().getDay()));
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 3;
        gbConstraints.weightx = 2;
        add(dueDateDayField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field for task due date month to this dialog
    private void addDueDateMonthComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel dueDateMonthText = new JLabel("Due Day Month: ");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 4;
        gbConstraints.weightx = 1;
        add(dueDateMonthText, gbConstraints);

        dueDateMonthField = new JTextField();
        dueDateMonthField.setColumns(10);
        dueDateMonthField.setText(Integer.toString(task.getDueDate().getMonth()));
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 4;
        gbConstraints.weightx = 2;
        add(dueDateMonthField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field for task due date year to this dialog
    private void addDueDateYearComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel dueDateYearText = new JLabel("Due Date Year: ");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 5;
        gbConstraints.weightx = 1;
        add(dueDateYearText, gbConstraints);

        dueDateYearField = new JTextField();
        dueDateYearField.setColumns(10);
        dueDateYearField.setText(Integer.toString(task.getDueDate().getYear()));
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 5;
        gbConstraints.weightx = 2;
        add(dueDateYearField, gbConstraints);
    }

    // REQUIRES: task is of actual type LeafTask
    // MODIFIES: this
    // EFFECTS: updates this dialog's associated leaf task with the given name, description,
    //          weight, and due date
    private void handleEditLeafTask(String name, String description, String weightString,
            String dueDateDayString, String dueDateMonthString, String dueDateYearString) 
    {   
        int weight;
        Date dueDate;
        try {
            weight = Integer.parseInt(weightString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid weight", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int dueDateDay = Integer.parseInt(dueDateDayString);
            int dueDateMonth = Integer.parseInt(dueDateMonthString);
            int dueDateYear = Integer.parseInt(dueDateYearString);
            if (!Date.isDateValid(dueDateDay, dueDateMonth, dueDateYear)) {
                throw new Exception();
            }
            dueDate = new Date(dueDateDay, dueDateMonth, dueDateYear);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid due date", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.editLeafTask(project, (LeafTask) task, name, description, weight, dueDate);
        dispose();
    }

    // REQUIRES: task is of actual type BranchTask
    // MODIFIES: this
    // EFFECTS: updates this dialog's associated branch task with the given name and description
    private void handleEditBranchTask(String name, String description) {
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        controller.editBranchTask(project, (BranchTask) task, name, description);
        dispose();
    }
}
