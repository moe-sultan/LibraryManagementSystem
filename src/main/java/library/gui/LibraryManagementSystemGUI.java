package library.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import library.models.Book;
import library.factories.BookFactory;

public class LibraryManagementSystemGUI extends JFrame {

    private List<Book> bookList = new ArrayList<>();

    public LibraryManagementSystemGUI() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton addButton = new JButton("Add Book");
        JButton viewButton = new JButton("View Books");
        JButton searchButton = new JButton("Search Books");
        JButton checkoutButton = new JButton("Checkout Book");
        JButton returnButton = new JButton("Return Book");

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(checkoutButton);
        panel.add(returnButton);

        add(panel);

        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddBookWindow();
            }
        });
    }

    private void openAddBookWindow() {
        
        JFrame addBookFrame = new JFrame("Add New Book");
        addBookFrame.setSize(400, 300);
        addBookFrame.setLocationRelativeTo(this); 
    
        
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
    
        
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
    
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
    
        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField();
    
        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField();
    
        JLabel typeLabel = new JLabel("Type:");
        String[] bookTypes = {"Physical", "EBook"};
        JComboBox<String> typeComboBox = new JComboBox<>(bookTypes);
    
        JButton submitButton = new JButton("Add Book");
    
        
        panel.add(titleLabel);
        panel.add(titleField);
    
        panel.add(authorLabel);
        panel.add(authorField);
    
        panel.add(categoryLabel);
        panel.add(categoryField);
    
        panel.add(isbnLabel);
        panel.add(isbnField);
    
        panel.add(typeLabel);
        panel.add(typeComboBox);
    
        panel.add(new JLabel()); 
        panel.add(submitButton);
    
        
        addBookFrame.add(panel);
    
        
        addBookFrame.setVisible(true);
    
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String category = categoryField.getText();
                String isbn = isbnField.getText();
                String type = (String) typeComboBox.getSelectedItem();
    
                Book newBook = library.factories.BookFactory.createBook(type, isbn, title, author, category);

                bookList.add(newBook);

                JOptionPane.showMessageDialog(addBookFrame, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                addBookFrame.dispose();
            }
        });
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystemGUI());
    }
}
