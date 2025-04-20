package HospitalManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDoctorForm extends JFrame {
    public AddDoctorForm(Connection connection) {
        setTitle("Add Doctor");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel specializationLabel = new JLabel("Specialization:");
        JTextField specializationField = new JTextField(20);

        JButton addButton = new JButton("Add Doctor");
        JButton cancelButton = new JButton("Cancel");

        addButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText().trim();
            String specialization = specializationField.getText().trim();

            if (name.isEmpty() || specialization.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String query = "INSERT INTO doctors (name, specialization) VALUES (?, ?)";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1, name);
                    ps.setString(2, specialization);
                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Doctor added successfully!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add doctor!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(specializationLabel, gbc);
        gbc.gridx = 1;
        panel.add(specializationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        add(panel);
        setVisible(true);
    }
}
