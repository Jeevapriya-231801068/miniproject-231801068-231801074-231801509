import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu {

    public static void display() {
        JFrame frame = new JFrame("Library Management System - Admin");

        JButton addBookButton = new JButton("Add Book");
        addBookButton.setBounds(50, 50, 200, 30);

        JButton viewBooksButton = new JButton("View Books");
        viewBooksButton.setBounds(50, 100, 200, 30);

        JButton lendBookButton = new JButton("Lend Book");
        lendBookButton.setBounds(50, 150, 200, 30);

        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.setBounds(50, 200, 200, 30);

        // Add Book button action
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField titleField = new JTextField(20);
                JTextField authorField = new JTextField(20);
                JTextField publisherField = new JTextField(20);
                JTextField yearField = new JTextField(20);
                JTextField quantityField = new JTextField(20);

                JPanel panel = new JPanel();
                panel.add(new JLabel("Title:"));
                panel.add(titleField);
                panel.add(new JLabel("Author:"));
                panel.add(authorField);
                panel.add(new JLabel("Publisher:"));
                panel.add(publisherField);
                panel.add(new JLabel("Year:"));
                panel.add(yearField);
                panel.add(new JLabel("Quantity:"));
                panel.add(quantityField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Book", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String title = titleField.getText();
                        String author = authorField.getText();
                        String publisher = publisherField.getText();
                        int year = Integer.parseInt(yearField.getText());
                        int quantity = Integer.parseInt(quantityField.getText());
                        Book.addBook(title, author, publisher, year, quantity);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numeric values for Year and Quantity.");
                    }
                }
            }
        });

        // View Books button action
        viewBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book.viewBooks();
            }
        });

        // Lend Book button action
        lendBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = JOptionPane.showInputDialog("Enter User ID:");
                if (userId != null && !userId.trim().isEmpty()) {
                    String bookTitle = JOptionPane.showInputDialog("Enter Book Title to Lend:");
                    if (bookTitle != null && !bookTitle.trim().isEmpty()) {
                        Book.lendBook(userId, bookTitle);
                    } else {
                        JOptionPane.showMessageDialog(null, "Book title cannot be empty.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User ID cannot be empty.");
                }
            }
        });

        // Return Book button action
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = JOptionPane.showInputDialog("Enter User ID:");
                if (userId != null && !userId.trim().isEmpty()) {
                    String bookTitle = JOptionPane.showInputDialog("Enter Book Title to Return:");
                    if (bookTitle != null && !bookTitle.trim().isEmpty()) {
                        Book.returnBook(userId, bookTitle);
                    } else {
                        JOptionPane.showMessageDialog(null, "Book title cannot be empty.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User ID cannot be empty.");
                }
            }
        });

        // Add buttons to the frame
        frame.add(addBookButton);
        frame.add(viewBooksButton);
        frame.add(lendBookButton);
        frame.add(returnBookButton);

        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
