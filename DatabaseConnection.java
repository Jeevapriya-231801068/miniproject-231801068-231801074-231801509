import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/LibraryManagement";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "Jeevapriya@2005"; // Replace with your MySQL password

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Method to establish and return a connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            throw new RuntimeException("Database connection error. Please check your connection settings.", e);
        }
    }
}
