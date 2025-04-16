package library.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import library.models.Book;
import library.factories.BookFactory;

public class LibraryManagementSystemGUI extends JFrame {

    private JTable viewTable;
    private JFrame viewFrame;
    

    private List<Book> bookList = new ArrayList<>();

    //Temporary book variables for testing
    private Book book1 = BookFactory.createBook("physical","9780141036144", "1984", "George Orwell", "Fiction");
    private Book book2 = BookFactory.createBook("ebook", "9780241341131", "Digital Minimalism", "Cal Newport", "Self-Improvement");

    

    public LibraryManagementSystemGUI() {

        //Adding temporary book variables to book list
        bookList.add(book1);
        bookList.add(book2);

        setTitle("Library Management System");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(new Color(230, 230, 250)); // match the background
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        ImageIcon icon = new ImageIcon("assets/library_logo.png");
        Image raw = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(icon);
        logoLabel.setIcon(new ImageIcon(raw));
        logoLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        titleLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        headerPanel.add(logoLabel);
        headerPanel.add(titleLabel);

        add(headerPanel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JButton addButton = new JButton("Add Book");
        JButton viewButton = new JButton("View Books");
        JButton searchButton = new JButton("Search Books");
        JButton manageButton = new JButton("Checkout / Return Book");
        JButton exportButton = new JButton("Export to CSV");
        JButton statsButton = new JButton("View Stats");

        addButton.setToolTipText("Add a new book to the library");
        viewButton.setToolTipText("See all books currently in the library");
        searchButton.setToolTipText("Search books by title or ISBN");
        manageButton.setToolTipText("Checkout or return selected books");
        exportButton.setToolTipText("Export the book list as a CSV file");
        statsButton.setToolTipText("Show total, available, and checked out book statistics");

        addButton.setIcon(new ImageIcon("assets/add.png"));
        viewButton.setIcon(new ImageIcon("assets/view.png"));
        searchButton.setIcon(new ImageIcon("assets/search.png"));
        manageButton.setIcon(new ImageIcon("assets/manage.png"));
        exportButton.setIcon(new ImageIcon("assets/export.png"));
        statsButton.setIcon(new ImageIcon("assets/stats.png"));

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(manageButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(statsButton);
        
        add(buttonPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("Library Management System v1.0", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 10));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footer, BorderLayout.SOUTH);

        addButton.addActionListener(e -> openAddBookWindow());
        viewButton.addActionListener(e -> openViewBooksWindow());
        searchButton.addActionListener(e -> openSearchBooksWindow());
        manageButton.addActionListener(e -> openCheckoutWindow());
        exportButton.addActionListener(e -> exportBooksToCSV());
        statsButton.addActionListener(e -> openStatsWindow());

        buttonPanel.setBackground(new Color(245, 245, 245));
        getContentPane().setBackground(new Color(230, 230, 250));

        setVisible(true);
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

                refreshViewTable();
                addBookFrame.dispose();
            }
        });
    }


    private void openViewBooksWindow() {
        viewFrame = new JFrame("View All Books");
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
    
        viewTable = new JTable(data, columns);    
        viewFrame.add(new JScrollPane(viewTable));
        viewFrame.setVisible(true);
    }
    
    
    private void refreshViewTable() {
        if (viewTable == null) return;
    
        String[][] data = new String[bookList.size()][5];
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            data[i][0] = book.getTitle();
            data[i][1] = book.getAuthor();
            data[i][2] = book.getCategory();
            data[i][3] = book.getISBN();
            data[i][4] = book.isAvailable() ? "Yes" : "No";
        }
    
        viewTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {
            "Title", "Author", "Category", "ISBN", "Available"
        }));
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
                    showSearchResults(results, query);
                }
    
                searchFrame.dispose();
            }
        });
    
        searchFrame.setVisible(true);
    }

    
    private void showSearchResults(List<Book> results, String highlightQuery) {
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
    
        JTable table = new JTable(data, columns) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                                                boolean isSelected, boolean hasFocus,
                                                                int row, int col) {
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                        // Get search query (we pass it in as lowercase)
                        String cellText = value.toString().toLowerCase();

                        if (!highlightQuery.isEmpty() &&
                            (col == 0 || col == 3) &&  // Highlight only Title (col 0) and ISBN (col 3)
                            cellText.contains(highlightQuery)) {

                            c.setBackground(Color.YELLOW);
                        } else {
                            c.setBackground(Color.WHITE);
                        }

                        return c;
                    }
                };
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        resultsFrame.add(scrollPane);
        resultsFrame.setVisible(true);
    }
    

    private void openCheckoutWindow() {
        JFrame checkoutFrame = new JFrame("Checkout / Return Book");
        checkoutFrame.setSize(700, 400);
        checkoutFrame.setLocationRelativeTo(this);
    
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
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
    
        JButton checkoutBtn = new JButton("Checkout Selected");
        JButton returnBtn = new JButton("Return Selected");
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkoutBtn);
        buttonPanel.add(returnBtn);
    
        checkoutFrame.add(scrollPane, BorderLayout.CENTER);
        checkoutFrame.add(buttonPanel, BorderLayout.SOUTH);
        checkoutFrame.setVisible(true);
    
        // Checkout button logic
        checkoutBtn.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            if (selectedRows.length == 0) {
                JOptionPane.showMessageDialog(checkoutFrame, "Please select at least one book to checkout.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            int successCount = 0;
            List<String> failedBooks = new ArrayList<>();
        
            for (int row : selectedRows) {
                Book book = bookList.get(row);
                if (book.isAvailable()) {
                    book.checkout();
                    successCount++;
                } else {
                    failedBooks.add(book.getTitle());
                }
            }
        
            refreshViewTable();
        
            StringBuilder message = new StringBuilder();
            message.append(successCount).append(" book(s) checked out successfully.\n");
            if (!failedBooks.isEmpty()) {
                message.append("Skipped (already checked out):\n");
                for (String title : failedBooks) {
                    message.append("- ").append(title).append("\n");
                }
            }
        
            JOptionPane.showMessageDialog(checkoutFrame, message.toString(), "Checkout Summary", JOptionPane.INFORMATION_MESSAGE);
            checkoutFrame.dispose();
        });
    
        // Return button logic
        returnBtn.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            if (selectedRows.length == 0) {
                JOptionPane.showMessageDialog(checkoutFrame, "Please select at least one book to return.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            int successCount = 0;
            List<String> skippedBooks = new ArrayList<>();
        
            for (int row : selectedRows) {
                Book book = bookList.get(row);
                if (!book.isAvailable()) {
                    book.returnBook();
                    successCount++;
                } else {
                    skippedBooks.add(book.getTitle());
                }
            }
        
            refreshViewTable();
        
            StringBuilder message = new StringBuilder();
            message.append(successCount).append(" book(s) returned successfully.\n");
            if (!skippedBooks.isEmpty()) {
                message.append("Skipped (already available):\n");
                for (String title : skippedBooks) {
                    message.append("- ").append(title).append("\n");
                }
            }
        
            JOptionPane.showMessageDialog(checkoutFrame, message.toString(), "Return Summary", JOptionPane.INFORMATION_MESSAGE);
            checkoutFrame.dispose();
        });
    }
    

    private void exportBooksToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Library as CSV");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }

            try (PrintWriter writer = new PrintWriter(fileToSave)) {
                writer.println("Title,Author,Category,ISBN,Available");
                for (Book book : bookList) {
                    writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                            book.getTitle(),
                            book.getAuthor(),
                            book.getCategory(),
                            book.getISBN(),
                            book.isAvailable() ? "Yes" : "No");
                }
                JOptionPane.showMessageDialog(this, "Books exported successfully to:\n" + fileToSave.getAbsolutePath(), "Export Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file:\n" + ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void openStatsWindow() {
        long total = bookList.size();
        long available = bookList.stream().filter(Book::isAvailable).count();
        long checkedOut = total - available;
        Map<String, Long> categoryCounts = bookList.stream().collect(Collectors.groupingBy(Book::getCategory, Collectors.counting()));

    
        JFrame statsFrame = new JFrame("Library Statistics");
        statsFrame.setSize(500, 300);
        statsFrame.setLocationRelativeTo(this);
    
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel totalLabel = new JLabel("Total books: " + total);
        JLabel availableLabel = new JLabel("Available books: " + available);
        JLabel checkedOutLabel = new JLabel("Checked out books: " + checkedOut);

        JPanel categoryPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Books by Category"));

        for (Map.Entry<String, Long> entry : categoryCounts.entrySet()) {
            JLabel catLabel = new JLabel(entry.getKey() + ": " + entry.getValue());
            catLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            categoryPanel.add(catLabel);
        }

        JScrollPane scrollPane = new JScrollPane(categoryPanel);
    
        totalLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        availableLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        checkedOutLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
    
        panel.add(totalLabel);
        panel.add(availableLabel);
        panel.add(checkedOutLabel);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        statsFrame.add(mainPanel);
        statsFrame.setVisible(true);

    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystemGUI());
    }
}
