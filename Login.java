import javax.swing.*;
import java.sql.*;

public class Login {

    /**
     * Authenticates the user based on the provided username and password.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if authentication is successful, false otherwise.
     */
    public static boolean authenticate(String username, String password) {
        String sql = "SELECT 1 FROM Login WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query and check if a record exists
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                    return false;
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        }
    }
}
