package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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

    public EditTaskDialog(ProjectTrackerUI controller, Project project, Task task) {
        super(controller, "Edit Task");
        this.controller = controller;
        this.project = project;
        this.utilities = new Utilities();
        this.task = task;
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
        nameField.setText(task.getName());
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
        descriptionField.setText(task.getDescription());
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 1;
        gbConstraints.weightx = 2;
        add(descriptionField, gbConstraints);

        JButton editButton = new JButton("Confirm");

        if (utilities.isLeafTask(task)) {

            JLabel weightText = new JLabel("Weight");
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 2;
            gbConstraints.weightx = 1;
            add(weightText, gbConstraints);

            JTextField weightField = new JTextField();
            weightField.setColumns(10);
            weightField.setText(Integer.toString(task.getWeight()));
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
            dueDateDayField.setText(Integer.toString(task.getDueDate().getDay()));
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
            dueDateMonthField.setText(Integer.toString(task.getDueDate().getMonth()));
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
            dueDateYearField.setText(Integer.toString(task.getDueDate().getYear()));
            gbConstraints.gridx = 1;
            gbConstraints.gridy = 5;
            gbConstraints.weightx = 2;
            add(dueDateYearField, gbConstraints);

            editButton.addActionListener(e -> handleEditLeafTask(nameField.getText(), descriptionField.getText(),
                Integer.parseInt(weightField.getText()),
                new Date(Integer.parseInt(dueDateDayField.getText()), Integer.parseInt(dueDateMonthField.getText()),
                        Integer.parseInt(dueDateYearField.getText()))));
        } else {
            editButton.addActionListener(e -> handleEditBranchTask(nameField.getText(), descriptionField.getText()));
        }

        
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 6;
        gbConstraints.gridwidth = 2;
        gbConstraints.anchor = GridBagConstraints.PAGE_END;
        add(editButton, gbConstraints);

        pack();
    }

    private void handleEditLeafTask(String name, String description, int weight, Date dueDate) {
        controller.editLeafTask(project, (LeafTask) task, name, description, weight, dueDate);
        dispose();
    }

    private void handleEditBranchTask(String name, String description) {
        controller.editBranchTask(project, (BranchTask) task, name, description);
        dispose();
    }
}
