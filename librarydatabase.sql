CREATE DATABASE LibraryDB;
USE LibraryDB;
CREATE TABLE Users (
    id VARCHAR(20) PRIMARY KEY,  -- User ID
    name VARCHAR(255) NOT NULL,
    borrowedBooks TEXT  -- Stores a list of borrowed book titles
);
CREATE TABLE Books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255),
    year INT,
    quantity INT DEFAULT 1,
    borrowed_by VARCHAR(255) DEFAULT NULL,  -- Stores User ID if borrowed
    FOREIGN KEY (borrowed_by) REFERENCES Users(id) ON DELETE SET NULL  -- Ensures borrowed_by refers to User
);
INSERT INTO Users (id, name, borrowedBooks)
VALUES
('U001', 'John Doe', NULL),
('U002', 'Jane Smith', NULL),
('U003', 'Tom Brown', NULL);
INSERT INTO Books (title, author, publisher, year, quantity)
VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Charles Scribner\'s Sons', 1925, 3),
('1984', 'George Orwell', 'Secker & Warburg', 1949, 2),
('To Kill a Mockingbird', 'Harper Lee', 'J.B. Lippincott & Co.', 1960, 4),
('Moby Dick', 'Herman Melville', 'Harper & Brothers', 1851, 1);







