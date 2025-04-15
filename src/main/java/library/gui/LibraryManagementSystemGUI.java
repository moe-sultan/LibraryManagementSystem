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

    //Temporary book variables for testing
    private Book book1 = BookFactory.createBook("physical","9780141036144", "1984", "George Orwell", "Fiction");
    private Book book2 = BookFactory.createBook("ebook", "9780241341131", "Digital Minimalism", "Cal Newport", "Self-Improvement");

    

    public LibraryManagementSystemGUI() {

        //Adding temporary book variables to book list
        bookList.add(book1);
        bookList.add(book2);

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

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openViewBooksWindow();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSearchBooksWindow();
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


    private void openViewBooksWindow() {
        JFrame viewFrame = new JFrame("View All Books");
        viewFrame.setSize(700, 400);
        viewFrame.setLocationRelativeTo(this);
    
        String[] columns = {"Title", "Author", "Category", "ISBN", "Available"};
    
        String[][] data = new String[bookList.size()][5];
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            data[i][0] = book.getTitle();
            data[i][1] = book.getAuthor();
            data[i][2] = book.getCategory();
            data[i][3] = book.getISBN();
            data[i][4] = book.isAvailable() ? "Yes" : "No";
        }
    
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
    
        viewFrame.add(scrollPane);
        viewFrame.setVisible(true);
    }    


    private void openSearchBooksWindow() {
        JFrame searchFrame = new JFrame("Search Books");
        searchFrame.setSize(500, 150);
        searchFrame.setLocationRelativeTo(this);
        searchFrame.setLayout(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
    
        JLabel searchLabel = new JLabel("Enter Title or ISBN:");
        JTextField searchField = new JTextField();
        
        JLabel categoryLabel = new JLabel("Category:");
        String[] categories = {"All", "Fiction", "Science", "History", "Self-Improvement", "Fantasy"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);

        JCheckBox availableOnlyCheck = new JCheckBox("Only show available");
        JCheckBox exactMatchCheck = new JCheckBox("Exact match");

        JButton searchButton = new JButton("Search");
    
        inputPanel.add(searchLabel);
        inputPanel.add(searchField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryBox);
        inputPanel.add(availableOnlyCheck);
        inputPanel.add(exactMatchCheck);
        inputPanel.add(new JLabel());
        inputPanel.add(searchButton);
    
        searchFrame.add(inputPanel, BorderLayout.CENTER);
    
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().trim().toLowerCase();
                if (query.isEmpty()) {
                    JOptionPane.showMessageDialog(searchFrame, "Please enter a search query.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                String selectedCategory = categoryBox.getSelectedItem().toString();
                boolean onlyAvailable = availableOnlyCheck.isSelected();
                boolean exactMatch = exactMatchCheck.isSelected();

                List<Book> results = new ArrayList<>();
                for (Book book : bookList) {
                    boolean matchesQuery = exactMatch
                        ? book.getTitle().equalsIgnoreCase(query) || book.getISBN().equalsIgnoreCase(query)
                        : book.getTitle().toLowerCase().contains(query) || book.getISBN().toLowerCase().contains(query);

                    boolean matchesCategory = selectedCategory.equals("All") || book.getCategory().equalsIgnoreCase(selectedCategory);
                    boolean matchesAvailability = !onlyAvailable || book.isAvailable();

                    if (matchesQuery && matchesCategory && matchesAvailability) {
                        results.add(book);
                    }
                }
    
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(searchFrame, "No books found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showSearchResults(results);
                }
    
                searchFrame.dispose();
            }
        });
    
        searchFrame.setVisible(true);
    }

    
    private void showSearchResults(List<Book> results) {
        JFrame resultsFrame = new JFrame("Search Results");
        resultsFrame.setSize(700, 400);
        resultsFrame.setLocationRelativeTo(this);
    
        String[] columns = {"Title", "Author", "Category", "ISBN", "Available"};
        String[][] data = new String[results.size()][5];
    
        for (int i = 0; i < results.size(); i++) {
            Book book = results.get(i);
            data[i][0] = book.getTitle();
            data[i][1] = book.getAuthor();
            data[i][2] = book.getCategory();
            data[i][3] = book.getISBN();
            data[i][4] = book.isAvailable() ? "Yes" : "No";
        }
    
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        resultsFrame.add(scrollPane);
        resultsFrame.setVisible(true);
    }
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystemGUI());
    }
}
