import java.sql.*;
import javax.swing.*;

public class Book {

    // Method to add a book
    public static void addBook(String title, String author, String publisher, int year, int quantity) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Books (title, author, publisher, year, quantity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, publisher);
            stmt.setInt(4, year);
            stmt.setInt(5, quantity);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book added successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Method to lend a book to a user
    public static void lendBook(String userId, String bookTitle) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if the book is available
            String availabilityCheckSql = "SELECT id, quantity FROM Books WHERE title = ? AND quantity > 0";
            PreparedStatement checkStmt = conn.prepareStatement(availabilityCheckSql);
            checkStmt.setString(1, bookTitle);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("id");
                int newQuantity = rs.getInt("quantity") - 1;

                // Update book quantity
                String updateBookSql = "UPDATE Books SET quantity = ? WHERE id = ?";
                PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSql);
                updateBookStmt.setInt(1, newQuantity);
                updateBookStmt.setInt(2, bookId);
                updateBookStmt.executeUpdate();

                // Add record to BorrowedBooks table
                String borrowSql = "INSERT INTO BorrowedBooks (user_id, book_id, borrow_date) VALUES (?, ?, CURRENT_DATE)";
                PreparedStatement borrowStmt = conn.prepareStatement(borrowSql);
                borrowStmt.setString(1, userId);
                borrowStmt.setInt(2, bookId);
                borrowStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Book lent successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Book not available.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Method to return a book
    public static void returnBook(String userId, String bookTitle) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Find the book ID from the title
            String bookIdSql = "SELECT id FROM Books WHERE title = ?";
            PreparedStatement bookIdStmt = conn.prepareStatement(bookIdSql);
            bookIdStmt.setString(1, bookTitle);
            ResultSet rs = bookIdStmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("id");

                // Remove the borrow record
                String returnSql = "DELETE FROM BorrowedBooks WHERE user_id = ? AND book_id = ?";
                PreparedStatement returnStmt = conn.prepareStatement(returnSql);
                returnStmt.setString(1, userId);
                returnStmt.setInt(2, bookId);
                int rowsAffected = returnStmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Update book quantity
                    String updateBookSql = "UPDATE Books SET quantity = quantity + 1 WHERE id = ?";
                    PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSql);
                    updateBookStmt.setInt(1, bookId);
                    updateBookStmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Book returned successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "No record of this book being borrowed by the user.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Book not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Method to view all books and their statuses
    public static void viewBooks() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT b.title, b.author, b.year, b.quantity, " +
                         "GROUP_CONCAT(u.name SEPARATOR ', ') AS borrowed_by " +
                         "FROM Books b " +
                         "LEFT JOIN BorrowedBooks bb ON b.id = bb.book_id " +
                         "LEFT JOIN Users u ON bb.user_id = u.id " +
                         "GROUP BY b.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder booksList = new StringBuilder("Books Available:\n");
            while (rs.next()) {
                booksList.append(rs.getString("title"))
                        .append(" - Author: ").append(rs.getString("author"))
                        .append(", Year: ").append(rs.getInt("year"))
                        .append(", Quantity: ").append(rs.getInt("quantity"))
                        .append(", Borrowed by: ").append(rs.getString("borrowed_by") != null ? rs.getString("borrowed_by") : "Available")
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, booksList.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
