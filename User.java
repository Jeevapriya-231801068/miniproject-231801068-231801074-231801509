import java.sql.*;

public class User {
    private String id;
    private String name;

    // Constructor
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for user ID
    public String getId() {
        return id;
    }

    // Getter for user name
    public String getName() {
        return name;
    }

    /**
     * Fetches a User object by user ID from the database.
     *
     * @param userId The user ID to search for.
     * @return A User object if found, null otherwise.
     */
    public static User getUserById(String userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, name FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Create a new User object using the fetched data
                return new User(rs.getString("id"), rs.getString("name"));
            } else {
                System.out.println("User not found with ID: " + userId);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Fetches the list of borrowed books for this user.
     *
     * @return A comma-separated string of borrowed book IDs, or null if none are borrowed.
     */
    public String getBorrowedBooks() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT borrowed_books FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("borrowed_books");
            } else {
                System.out.println("No borrowed books found for user ID: " + this.id);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching borrowed books: " + e.getMessage());
        }
        return null;
    }
}
