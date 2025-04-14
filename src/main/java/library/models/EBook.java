package library.models;

public class EBook implements Book {
    private final String isbn;
    private String title;
    private String author;
    private String category;
    private boolean available;

    public EBook(String isbn, String title, String author, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true; 
    }

    public String getISBN(){
        return isbn;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getCategory(){
        return category;
    }

    public boolean isAvailable(){
        return available;
    }

    public void checkout(){
        System.out.println(title + " is now available for download.");
    }

    public void returnBook(){
        System.out.println(title + " download session ended.");
    }
}