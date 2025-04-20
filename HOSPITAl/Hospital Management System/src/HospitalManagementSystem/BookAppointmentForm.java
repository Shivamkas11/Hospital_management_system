package HospitalManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class BookAppointmentForm extends JFrame {
    private final Connection connection;

    public BookAppointmentForm(Connection connection) {
        this.connection = connection;
        setTitle("Book Appointment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Outer padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and Fields
        JLabel patientIdLabel = new JLabel("Patient ID:");
        JTextField patientIdField = new JTextField(15);

        JLabel doctorIdLabel = new JLabel("Doctor ID:");
        JTextField doctorIdField = new JTextField(15);

        JLabel dateLabel = new JLabel("Appointment Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(15);

        JButton bookButton = new JButton("Book Appointment");

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(patientIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(patientIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(doctorIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(doctorIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(dateLabel, gbc);
        gbc.gridx = 1;
        panel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(bookButton, gbc);

        // Button Action
        bookButton.addActionListener((ActionEvent e) -> {
            try {
                int patientId = Integer.parseInt(patientIdField.getText().trim());
                int doctorId = Integer.parseInt(doctorIdField.getText().trim());
                String appointmentDate = dateField.getText().trim();

                if (getPatientById(patientId) && getDoctorById(doctorId)) {
                    if (checkDoctorAvailability(doctorId, appointmentDate)) {
                        bookAppointment(patientId, doctorId, appointmentDate);
                    } else {
                        JOptionPane.showMessageDialog(this, "Doctor is not available on this date.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Patient or Doctor ID.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric IDs.");
            }
        });

        add(panel);
        setVisible(true);
    }

    private boolean getPatientById(int id) {
        try {
            String query = "SELECT * FROM patients WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean getDoctorById(int id) {
        try {
            String query = "SELECT * FROM doctors WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkDoctorAvailability(int doctorId, String appointmentDate) {
        try {
            String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctorId);
            ps.setString(2, appointmentDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void bookAppointment(int patientId, int doctorId, String date) {
        try {
            String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setString(3, date);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Appointment booked successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to book appointment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while booking appointment.");
        }
    }
}
