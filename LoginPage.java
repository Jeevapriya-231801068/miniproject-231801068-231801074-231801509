import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {

    /**
     * Displays the login page for the Library Management System.
     */
    public static void display() {
        JFrame frame = new JFrame("Library Management System - Login");

        // Username Label and TextField
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 30);
        JTextField userField = new JTextField();
        userField.setBounds(150, 50, 150, 30);

        // Password Label and PasswordField
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 100, 150, 30);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 30);

        // Action Listener for Login Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                // Authenticate user
                if (Login.authenticate(username, password)) {
                    frame.dispose(); // Close the login frame
                    AdminMenu.display(); // Open Admin menu
                } else {
                    JOptionPane.showMessageDialog(frame, 
                        "Invalid Username or Password", 
                        "Login Failed", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add components to the frame
        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginButton);

        // Frame properties
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
