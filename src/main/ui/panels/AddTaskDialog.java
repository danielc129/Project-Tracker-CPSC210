package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Date;
import model.Project;
import ui.ProjectTrackerUI;

public class AddTaskDialog extends JDialog {
    private ProjectTrackerUI controller;
    private boolean isProjectLevel;
    private Project project;
    
    public AddTaskDialog(ProjectTrackerUI controller, Project project, boolean isProjectLevel) {
        super(controller, "Add Task");
        this.controller = controller;
        this.project = project;
        this.isProjectLevel = isProjectLevel;
        setLocationRelativeTo(null);
        setVisible(true);

        setLayout(new GridBagLayout());
        GridBagConstraints gbConstraints = new GridBagConstraints();

        JLabel nameText = new JLabel("Name");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.weightx = 1;
        add(nameText, gbConstraints);

        JTextField nameField = new JTextField();
        nameField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 0;
        gbConstraints.weightx = 2;
        add(nameField, gbConstraints);

        JLabel descriptionText = new JLabel("Description");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        gbConstraints.weightx = 1;
        add(descriptionText, gbConstraints);

        JTextField descriptionField = new JTextField();
        descriptionField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 1;
        gbConstraints.weightx = 2;
        add(descriptionField, gbConstraints);

        JLabel weightText = new JLabel("Weight");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbConstraints.weightx = 1;
        add(weightText, gbConstraints);

        JTextField weightField = new JTextField();
        weightField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 2;
        gbConstraints.weightx = 2;
        add(weightField, gbConstraints);

        JLabel dueDateDayText = new JLabel("Due Date Day: ");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 3;
        gbConstraints.weightx = 1;
        add(dueDateDayText, gbConstraints);

        JTextField dueDateDayField = new JTextField();
        dueDateDayField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 3;
        gbConstraints.weightx = 2;
        add(dueDateDayField, gbConstraints);

        JLabel dueDateMonthText = new JLabel("Due Day Month: ");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 4;
        gbConstraints.weightx = 1;
        add(dueDateMonthText, gbConstraints);

        JTextField dueDateMonthField = new JTextField();
        dueDateMonthField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 4;
        gbConstraints.weightx = 2;
        add(dueDateMonthField, gbConstraints);

        JLabel dueDateYearText = new JLabel("Due Date Year: ");
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 5;
        gbConstraints.weightx = 1;
        add(dueDateYearText, gbConstraints);
        
        JTextField dueDateYearField = new JTextField();
        dueDateYearField.setColumns(10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 5;
        gbConstraints.weightx = 2;
        add(dueDateYearField, gbConstraints);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> handleAddTask(nameField.getText(), descriptionField.getText(), Integer.parseInt(weightField.getText()), 
                                        new Date(Integer.parseInt(dueDateDayField.getText()), Integer.parseInt(dueDateMonthField.getText()), 
                                        Integer.parseInt(dueDateYearField.getText()))));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 6;
        gbConstraints.gridwidth = 2;
        gbConstraints.anchor = GridBagConstraints.PAGE_END;
        add(addButton, gbConstraints);

        pack();
    }

    // MODIFIES: controller
    // EFFECTS: instructs controller to add the task to the project or selected task
    //          with the information specified in the dialog fields
    private void handleAddTask(String name, String description, int weight, Date dueDate) {
        if (isProjectLevel) {
            controller.addTaskToRoot(project, name, description, weight, dueDate);
        }
        dispose();
    }
}
 