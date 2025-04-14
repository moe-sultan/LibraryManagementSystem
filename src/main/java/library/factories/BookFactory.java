package library.factories;

import library.models.Book;
import library.models.PhysicalBook;
import library.models.EBook;

public class BookFactory {
    public static Book createBook(String type, String isbn, String title, String author, String category) {
        if (type.equalsIgnoreCase("physical")) {
            return new PhysicalBook(isbn, title, author, category);
        } else if (type.equalsIgnoreCase("ebook")) {
            return new EBook(isbn, title, author, category);
        }
        // Add more cases later
        return null; // or throw an exception for unsupported types
    }
}
