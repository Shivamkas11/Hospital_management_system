package HospitalManagementSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Shivam@12";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            // Set up the main frame
            JFrame frame = new JFrame("🏥 Hospital Management System");
            frame.setSize(500, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setResizable(false);

            // Center the window
            frame.setLocationRelativeTo(null);

            // Set background color
            frame.getContentPane().setBackground(new Color(245, 250, 255));

            // Font for buttons
            Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

            // Add Patient Button
            JButton addPatientButton = new JButton("➕ Add Patient");
            addPatientButton.setBounds(50, 60, 180, 50);
            addPatientButton.setBackground(new Color(100, 200, 250));
            addPatientButton.setForeground(Color.WHITE);
            addPatientButton.setFont(buttonFont);
            addPatientButton.setToolTipText("Click to add a new patient");
            frame.add(addPatientButton);

            // Delete Patient Button
            JButton deletePatientButton = new JButton("🗑️ Delete Patient");
            deletePatientButton.setBounds(260, 60, 180, 50);
            deletePatientButton.setBackground(new Color(255, 102, 102));
            deletePatientButton.setForeground(Color.WHITE);
            deletePatientButton.setFont(buttonFont);
            deletePatientButton.setToolTipText("Click to delete an existing patient");
            frame.add(deletePatientButton);

            // View Patients Button
            JButton viewPatientsButton = new JButton("📋 View Patients");
            viewPatientsButton.setBounds(155, 140, 180, 50);
            viewPatientsButton.setBackground(new Color(102, 204, 102));
            viewPatientsButton.setForeground(Color.WHITE);
            viewPatientsButton.setFont(buttonFont);
            viewPatientsButton.setToolTipText("Click to view all patient records");
            frame.add(viewPatientsButton);

            // Add action listeners
            addPatientButton.addActionListener(e -> new AddPatientForm(connection));
            deletePatientButton.addActionListener(e -> new DeletePatientForm(connection));
            viewPatientsButton.addActionListener(e -> new ViewPatientsForm(connection));

            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }
    }
}
