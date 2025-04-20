package HospitalManagementSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HospitalManagementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Shivam@12";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            SwingUtilities.invokeLater(() -> showMainMenu(connection));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Connection Failed!");
        }
    }

    private static void showMainMenu(Connection connection) {
        JFrame frame = new JFrame("Hospital Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 450);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Padding
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton addPatientButton = createStyledButton("Add Patient");
        JButton viewDoctorsButton = createStyledButton("View Doctors");
        JButton bookAppointmentButton = createStyledButton("Book Appointment");
        JButton addDoctorButton = createStyledButton("Add Doctors"); // ✅ New Button
        JButton exitButton = createStyledButton("Exit");

        addPatientButton.addActionListener(e -> new AddPatientForm(connection));
        viewDoctorsButton.addActionListener(e -> new ViewDoctorsForm(connection));
        bookAppointmentButton.addActionListener(e -> new BookAppointmentForm(connection));
        addDoctorButton.addActionListener(e -> new AddDoctorForm(connection)); // ✅ Action
        exitButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
            JOptionPane.showMessageDialog(null, "Thank you for using HMS!");
        });

        mainPanel.add(titleLabel);
        mainPanel.add(addPatientButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(viewDoctorsButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(bookAppointmentButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(addDoctorButton); // ✅ Added
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(exitButton);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(250, 40));
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}
