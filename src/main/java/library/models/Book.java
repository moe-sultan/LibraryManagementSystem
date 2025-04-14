package library.models;

public interface Book {
    String getISBN();
    String getTitle();
    String getAuthor();
    String getCategory();
    boolean isAvailable();

    void checkout();
    void returnBook();
}