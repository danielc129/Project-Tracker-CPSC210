package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ui.ProjectTrackerUI;

public class AddProjectDialog extends JDialog {
    private ProjectTrackerUI controller;

    public AddProjectDialog(ProjectTrackerUI controller) {
        super(controller, "Add Project");
        this.controller = controller;
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

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> this.addProject(nameField.getText(), descriptionField.getText()));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbConstraints.gridwidth = 2;
        gbConstraints.anchor = GridBagConstraints.PAGE_END;
        add(addButton, gbConstraints);

        pack();
    }

    // MODIFIES: controller
    // EFFECTS: instructs controller to add a new project with given name and description
    //          in the text fields. Closes this dialog.
    private void addProject(String name, String description) {
        controller.addProject(name, description);
        dispose();
    }
}
