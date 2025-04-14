package library.adapters;

import library.models.Book;
import library.models.LegacyBook;

public class LegacyBookAdapter implements Book {
    private LegacyBook legacyBook;

    public LegacyBookAdapter(LegacyBook legacyBook) {
        this.legacyBook = legacyBook;
    }

    @Override
    public String getISBN() {
        return "0000000000000";
    }

    @Override
    public String getTitle() {
        return legacyBook.fetchTitle();
    }

    @Override
    public String getAuthor() {
        return legacyBook.fetchAuthor();
    }

    @Override
    public String getCategory() {
        return legacyBook.fetchCategory();
    }

    @Override
    public boolean isAvailable() {
        return legacyBook.checkAvailability();
    }

    @Override
    public void checkout() {
        legacyBook.borrow();
    }

    @Override
    public void returnBook() {
        legacyBook.giveBack();
    }
}
