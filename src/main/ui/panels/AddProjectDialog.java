package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ui.ProjectTrackerUI;

// ATTRIBUTION: SmartHome
// ATTRIBUTION: Oracle Java Swing Components Tutorial
// ATTRIBUTION: EdX Project Phase 3 Page
// The dialog to handle adding a project in the project tracker UI
// Displays fields to let user enter project details
public class AddProjectDialog extends JDialog {
    private ProjectTrackerUI controller;
    private JTextField nameField;
    private JTextField descriptionField;

    // EFFECTS: creates a new add project dialog with the given base UI controller
    public AddProjectDialog(ProjectTrackerUI controller) {
        super(controller, "Add Project");
        this.controller = controller;
        setLocationRelativeTo(null);
        setVisible(true);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbConstraints = new GridBagConstraints();

        addNameComponents();

        addDescriptionComponents();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> this.addProject(nameField.getText(), descriptionField.getText()));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbConstraints.gridwidth = 2;
        gbConstraints.anchor = GridBagConstraints.PAGE_END;
        add(addButton, gbConstraints);

        pack();
    }

    // MODIFIES: this
    // EFFECTS: adds label and field components for entering project name to this dialog
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
    // EFFECTS: adds label and field components for entering project description to this dialog
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
    // EFFECTS: instructs controller to add a new project with the given name and description
    //          from the text fields
    //          closes this dialog
    private void addProject(String name, String description) {
        controller.addProject(name, description);
        dispose();
    }
}
