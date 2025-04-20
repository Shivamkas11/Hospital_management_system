package HospitalManagementSystem;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;

public class AddPatientForm extends JFrame {
    private JTextField nameField, ageField, genderField, addressField;
    private JButton addButton;
    private Connection connection;
    private patient patientHandler;

    public AddPatientForm(Connection connection) {
        this.connection = connection;
        this.patientHandler = new patient(connection, null); // null scanner, since GUI doesn't need it

        setTitle("Add Patient");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes only this window
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(140, 30, 150, 25);
        add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(30, 70, 100, 25);
        add(ageLabel);

        ageField = new JTextField();
        ageField.setBounds(140, 70, 150, 25);
        add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(30, 110, 100, 25);
        add(genderLabel);

        genderField = new JTextField();
        genderField.setBounds(140, 110, 150, 25);
        add(genderField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(30, 150, 100, 25);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(140, 150, 150, 25);
        add(addressField);

        addButton = new JButton("Add Patient");
        addButton.setBounds(90, 200, 150, 30);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPatientToDB();
            }
        });

        setVisible(true);
    }

    private void addPatientToDB() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getText();
            String address = addressField.getText();

            boolean success = patientHandler.addPatientFromGUI(name, age, gender, address);
            if (success) {
                JOptionPane.showMessageDialog(this, "Patient added successfully!");
                dispose(); // close the form after success
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
