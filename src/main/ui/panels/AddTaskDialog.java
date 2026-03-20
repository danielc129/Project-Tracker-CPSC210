package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Date;
import model.Project;
import model.Task;
import ui.ProjectTrackerUI;

// ATTRIBUTION: SmartHome
// ATTRIBUTION: Oracle Java Swing Components Tutorial
// ATTRIBUTION: EdX Project Phase 3 Page
// The dialog to handle adding a task in the project tracker UI 
// Displays fields to let user enter task details 
@ExcludeFromJacocoGeneratedReport
public class AddTaskDialog extends JDialog {
    private ProjectTrackerUI controller;
    private boolean isProjectLevel;
    private Project project;
    private Task selectedTask;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField weightField;
    private JTextField dueDateDayField;
    private JTextField dueDateMonthField;
    private JTextField dueDateYearField;

    // EFFECTS: creates a new add task dialog with the given base UI controller
    //          when user clicks the add button, the given controller will be
    //          instructed to add the new task to the given task if isProjectLevel == false
    //          or add the new task to the given project's root if isProjectLevel == true
    public AddTaskDialog(ProjectTrackerUI controller, Project project, Task task, boolean isProjectLevel) {
        super(controller, "Add Task");
        this.controller = controller;
        this.project = project;
        this.selectedTask = task;
        this.isProjectLevel = isProjectLevel;
        setLocationRelativeTo(null);
        setVisible(true);

        setLayout(new GridBagLayout());
        addNameComponents();
        addDescriptionComponents();
        addWeightComponents();
        addDueDateDayComponents();
        addDueDateMonthComponents();
        addDueDateYearComponents();
        addAddButton();
        pack();
    }

    // MODIFIES: this
    // EFFECTS: adds button component to add a task, to this dialog
    private void addAddButton() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> handleAddTask(nameField.getText(), descriptionField.getText(), 
                                        weightField.getText(), 
                                        dueDateDayField.getText(), 
                                        dueDateMonthField.getText(), 
                                        dueDateYearField.getText()));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 6;
        gbConstraints.gridwidth = 2;
        gbConstraints.anchor = GridBagConstraints.PAGE_END;
        add(addButton, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field components for entering task name to this dialog
    private void addNameComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel nameText = new JLabel("Name");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.weightx = 1;
        add(nameText, gbConstraints);

        nameField = new JTextField();
        nameField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 0;
        gbConstraints.weightx = 2;
        add(nameField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field components for entering for task description to this dialog
    private void addDescriptionComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel descriptionText = new JLabel("Description");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        gbConstraints.weightx = 1;
        add(descriptionText, gbConstraints);

        descriptionField = new JTextField();
        descriptionField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 1;
        gbConstraints.weightx = 2;
        add(descriptionField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field components for entering task weight to this dialog
    private void addWeightComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel weightText = new JLabel("Weight");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbConstraints.weightx = 1;
        add(weightText, gbConstraints);

        weightField = new JTextField();
        weightField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 2;
        gbConstraints.weightx = 2;
        add(weightField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field components for entering task due date day to this dialog
    private void addDueDateDayComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel dueDateDayText = new JLabel("Due Date Day: ");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 3;
        gbConstraints.weightx = 1;
        add(dueDateDayText, gbConstraints);

        dueDateDayField = new JTextField();
        dueDateDayField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 3;
        gbConstraints.weightx = 2;
        add(dueDateDayField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field components for entering task due date month to this dialog
    private void addDueDateMonthComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel dueDateMonthText = new JLabel("Due Day Month: ");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 4;
        gbConstraints.weightx = 1;
        add(dueDateMonthText, gbConstraints);

        dueDateMonthField = new JTextField();
        dueDateMonthField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 4;
        gbConstraints.weightx = 2;
        add(dueDateMonthField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adds label and field components for entering task due date year to this dialog
    private void addDueDateYearComponents() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel dueDateYearText = new JLabel("Due Date Year: ");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 5;
        gbConstraints.weightx = 1;
        add(dueDateYearText, gbConstraints);
        
        dueDateYearField = new JTextField();
        dueDateYearField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 5;
        gbConstraints.weightx = 2;
        add(dueDateYearField, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: instructs controller to add the task to the project (if isProjectLevel == true) 
    //          or selected task (if isProjectLevel == false) with the information specified in the dialog fields
    //          closes this dialog afterwards
    //          if field values are invalid, shows error dialog and does not add task
    private void handleAddTask(String name, String description, String weightString, 
                String dueDateDayString, String dueDateMonthString, String dueDateYearString) {
        int weight;
        Date dueDate = null;
        try {
            weight = Integer.parseInt(weightString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid weight", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dueDate = parseDate(dueDateDayString, dueDateMonthString, dueDateYearString);
        if (dueDate == null) {
            return;
        }

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isProjectLevel) {
            controller.addTaskToRoot(project, name, description, weight, dueDate);
        } else {
            controller.addSubtask(project, selectedTask, name, description, weight, dueDate);
        }
        dispose();
    }

    // EFFECTS: parses the due date from the given strings for day, month, and year
    //          and returns the date object
    //          shows an error dialog if invalid and returns null
    private Date parseDate(String dueDateDayString, String dueDateMonthString, String dueDateYearString) {
        try {
            int dueDateDay = Integer.parseInt(dueDateDayString);
            int dueDateMonth = Integer.parseInt(dueDateMonthString);
            int dueDateYear = Integer.parseInt(dueDateYearString);
            if (!Date.isDateValid(dueDateDay, dueDateMonth, dueDateYear)) {
                throw new Exception();
            }
            return new Date(dueDateDay, dueDateMonth, dueDateYear);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid due date", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
 