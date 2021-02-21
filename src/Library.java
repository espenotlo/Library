import java.util.ArrayList;
import java.util.Iterator;

/**
 * A library to be used by the GUI class. This Class stores books in the library,
 * and manages loaning, returning, creation and deletion of books.
 *
 * @author (Espen Otlo)
 * @version (0.5)
 */
public class Library {
    private final ArrayList<Book> books;
    private boolean demoLoaded;

    /**
     * Constructs a new empty book library.
     */
    public Library()
    {
        books = new ArrayList<>();
        demoLoaded = false;
    }

    /**
     * adds a selection of example books to the library.
     * @return returns an empty string when run for the first time, or error message if the demo has been loaded previously.
     */
    public boolean loadLib() {
        boolean success = false;
        if (!demoLoaded) {
            books.add(new Book("Da Vinci Code, The", "Dan Brown", "Doubleday", 2003, 689, "1234567890123"));
            books.add(new Book("Angels and Demons", "Dan Brown", "Pocket Books", 2003, 616, "3210987654321"));
            books.add(new Book("Lost Symbol, The", "Dan Brown", "Doubleday", 2009, 528, "5432109876098"));
            books.add(new Book("Adventures of Tom Sawyer, The", "Mark Twain", "American Publishing Company", 1876, 813, "6789012345654"));
            demoLoaded = true;
            success = true;
        }
        return success;
    }

    /**
     * Creates a new book and adds it to the library if no book with matching barcode already there.
     * Returns {@code true} if the book was successfully added,
     * or {@code false} if a book with identical barcode exists in the library.
     *
     * @param title the title of the book
     * @param author the author of the book
     * @param publisher the publisher of the book
     * @param yearOfRelease the book's year of release
     * @param numberOfPages the book's number of pages
     * @param barCode the bar code of the book
     *
     * @return {@code true} if book was successfully added,
     * {@code false} if duplicate was found.
     */
    public boolean addBook(String title, String author, String publisher, int yearOfRelease, int numberOfPages, String barCode) {
        boolean success = true;
        for (Book book : books) {
            if (book.getBarCode().equals(barCode) || null == title || null == author
                    || null == publisher || 0 > yearOfRelease || 2100 < yearOfRelease
                    || 0 > numberOfPages || null == barCode) {
                success = false;
                break;
            }
        }
        if (success) {
            books.add(new Book(title, author, publisher, yearOfRelease, numberOfPages, barCode));
        }
        return success;
    }

    /**
     * Searches the library for a book with the given title, and returns it if found.
     * If no books are found, returns null.
     *
     * @param searchString the title of the desired book
     * @return {@code Book} with title matching searchString,
     * {@code null} if no book matched the searchString
     */
    public Book getBookByTitle(String searchString) {
        Book i = null;
        Iterator<Book> it = books.iterator();
        boolean bookFound = false;
        while (it.hasNext() && !bookFound) {
            i = it.next();
            if (i.getTitle().equalsIgnoreCase(searchString)) {
                bookFound = true;
            }
        }
        if (bookFound) {
            return i;
        } else {
            return null;
        }
    }

    /**
     * Searches the library for a book with the given index number, and returns it if found.
     * If no books are found, returns null.
     *
     * @param searchInt the index of the desired book
     * @return {@code Book} at the given index,
     * {@code null} if no book was at given index
     */
    public Book getBookByIndex(int searchInt) {
        if (searchInt >= 0 && searchInt < books.size()) {
            return books.get(searchInt);
        } else {
            return null;
        }
    }

    /**
     * Searches the library for a book with the given title,
     * and removes it if found. Returns {@code true} if deletion is successful,
     * or {@code false} if no book with matching title is found.
     *
     * @param searchString the title to be deleted
     * @return {@code true} if successful,
     * {@code false} if no book matches the searchString.
     */
    public boolean removeByTitle(String searchString) {
        Iterator<Book> it = books.iterator();
        boolean bookFound = false;
        if (null == searchString) {
            return false;
        } else {
            while (it.hasNext() && !bookFound) {
                Book i = it.next();
                if (i.getTitle().equalsIgnoreCase(searchString)) {
                    bookFound = true;
                    it.remove();
                }
            }
        }
        return bookFound;
        }

    /**
     * Deletes book with the given index if present.
     * Returns {@code true} if deletion was successful,
     * or {@code false} if given index is invalid.
     *
     * @param searchInt the index of the book to be deleted.
     * @return {@code true} if successful,
     * {@code false} if no book with given index exists
     */
    public boolean removeByIndex(int searchInt) {
        if (searchInt >= 0 && searchInt < books.size()) {
            books.remove(searchInt);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Searches the library for a book with given title.
     ** Returns matching hits as an {@code ArrayList}.
     *
     * @param searchString Partial or full title of the book to be searched for.
     * @return an {@code ArrayList} containing the books with at least
     * a partial matching title to the search String.
     */
    public ArrayList<Book> searchTitle(String searchString) {
        ArrayList<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchString.toLowerCase())) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    /**
     * Searches the library for books by given author.
     * Returns matching hits as an {@code ArrayList}.
     *
     * @param searchString Partial or full title of the book to be searched for.
     * @return an {@code ArrayList} containing the books with at least
     * a partial matching title to the search String.
     */
    public ArrayList<Book> searchAuthor(String searchString) {
        ArrayList<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(searchString.toLowerCase())) {
                bookList.add(book);
            }
        }
        return bookList;
    }
    /**
     * Searches the library for books with given bar code.
     * Returns matching hits as an {@code ArrayList}.
     *
     * @param searchString Partial or full bar code to be searched for.
     * @return an {@code ArrayList} containing the books with at least
     * a partial matching title to the search String.
     */
    public ArrayList<Book> searchBarCode(String searchString) {
        ArrayList<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getBarCode().toLowerCase().contains(searchString.toLowerCase())) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    /**
     * Searches the library for given title and loans it out to given person if the book is available.
     * Returns 1 if successful, 2 if book was already on loan, or 0 if no books match given title.
     *
     * @param searchString the title of the book to be loaned out
     * @return 1 if book was successfully loaned out,
     * 2 if book was already loaned out,
     * 3 if no book matched the search title.
     */
    public int loanOut(String searchString, String loanedTo) {
        int returnInt= 0; // book has not yet been found.
        if (null == searchString || null == loanedTo) {
            returnInt = 3;
        } else {
            for (Book i : books) {
                if (i.getTitle().toLowerCase().matches(searchString.toLowerCase())) {
                    if (i.getInStock()) {
                        i.loanBook(loanedTo);
                        returnInt = 1; // book was found and successfully loaned out.
                    } else {
                        returnInt = 2; // book was found, but already loaned out.
                    }
                }
            }
        }
        return returnInt;
    }

    /**
     * Searches the library for given title and returns it if it was on loan.
     * Returns 1 if successful, 2 if book was already in stock, or 0 if no books match given title.
     *
     * @param searchString the title of the book to be returned to library
     * @return String containing a confirmation message if successful, or error message if failed or cancelled.
     */
    public int returnBook(String searchString) {
        int returnInt = 0; // book has not yet been found.
        for (Book i : books) {
            if (i.getTitle().equalsIgnoreCase(searchString)) {
                if (!i.getInStock()) {
                    i.returnBook();
                    returnInt = 1; // book was found, and successfully returned.
                } else {
                    returnInt = 2; // book was found, but already in stock.
                }
            }
        }
        return returnInt;
    }

    /**
     * Sorts all the books in the library by author and title,
     * and returns a a String list containing the index and title of all books.
     *
     * @return String listing all the books in the library by index and title.
     */
    public String getBookList() {
        books.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
        books.sort((o1, o2) -> o1.getAuthor().compareToIgnoreCase(o2.getAuthor()));
        int index = 1;
        StringBuilder bookString = new StringBuilder();
        if (index <= books.size()) {
            for (Book book : books) {
                bookString.append(index).append(": ").append(book.getTitle()).append("\n");
                index++;
            }
        }
        return bookString.toString();
    }
}