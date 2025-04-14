package library.models;

public class PhysicalBook implements Book {
    private final String isbn;
    private String title;
    private String author;
    private String category;
    private boolean available;

    public PhysicalBook(String isbn, String title, String author, String category) {
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
        if (available){
            available = false;
            System.out.println(title + " has been checked out.");
        } else {
            System.out.println(title + " is already checked out.");
        }
    }

    public void returnBook(){
        if (!available){
            available = true;
            System.out.println(title + " has been checked in.");
        } else {
            System.out.println(title + " is already checked in.");
        }
    }
}