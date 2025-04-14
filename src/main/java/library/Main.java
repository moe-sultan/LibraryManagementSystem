package library;

import library.factories.BookFactory;
import library.models.Book;

public class Main {
    public static void main(String[] args) {
        Book book1 = BookFactory.createBook("physical","9780141036144", "1984", "George Orwell", "Fiction");
        Book book2 = BookFactory.createBook("ebook", "9780241341131", "Digital Minimalism", "Cal Newport", "Self-Improvement");

        System.out.println(book1.getTitle());
        System.out.println(book2.getTitle());
    }
}
