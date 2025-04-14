package library.models;

public class LegacyBook {
    private String name;
    private String writer;
    private String type;
    private boolean isAvailable;

    public LegacyBook(String name, String writer, String type) {
        this.name = name;
        this.writer = writer;
        this.type = type;
        this.isAvailable = true; 
    }

    public String fetchTitle() {
        return name;
    }

    public String fetchAuthor() {
        return writer;
    }

    public String fetchCategory() {
        return type;
    }

    public boolean checkAvailability() {
        return isAvailable;
    }

    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println(name + " has been borrowed.");
        } else {
            System.out.println(name + " is already borrowed.");
        }
    }

    public void giveBack() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println(name + " has been returned.");
        } else {
            System.out.println(name + " was not borrowed.");
        }
    }
}
