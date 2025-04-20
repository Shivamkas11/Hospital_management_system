package HospitalManagementSystem;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;

public class DeletePatientForm extends JFrame {
    private JTextField idField;
    private JButton deleteButton;
    private patient patientHandler;

    public DeletePatientForm(Connection connection) {
        this.patientHandler = new patient(connection, null);

        setTitle("Delete Patient");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel idLabel = new JLabel("Enter Patient ID:");
        idLabel.setBounds(30, 40, 120, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 40, 100, 25);
        add(idField);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(80, 100, 120, 30);
        add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    boolean success = patientHandler.deletePatientById(id);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Patient deleted successfully!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Patient not found.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }
}
