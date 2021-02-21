import java.util.Objects;

/**
 * A book to be used by the library class. This Class stores and returns information
 * about the book.
 *
 * @author (Espen Otlo)
 * @version (0.5)
 */
public class Book {
    private final String title;
    private final String author;
    private final String publisher;
    private final int yearOfRelease;
    private final int numberOfPages;
    private final String barCode;
    private boolean inStock;
    private String loanedTo;

    /**
     * Constructs a book containing all necessary information.
     * @param title The title of the book
     * @param author The author of the book
     * @param publisher The publisher of the book
     * @param yearOfRelease The book's year of release
     * @param numberOfPages The number of pages in the book
     * @param barCode the book's bar code (EAN-13)
     */
    public Book(String title, String author, String publisher,
                int yearOfRelease, int numberOfPages, String barCode) {
        this.title = Objects.requireNonNullElse(title, "INVALID TITLE");
        if (null == author) {
            this.author = "INVALID AUTHOR";
        } else {
            this.author = author;
        }
        if (null == publisher) {
            this.publisher = "INVALID PUBLISHER";
        } else {
            this.publisher = publisher;
        }
        if (yearOfRelease < 0 || yearOfRelease > 2100) {
            this.yearOfRelease = 1337;
        } else {
            this.yearOfRelease = yearOfRelease;
        }
        if (numberOfPages < 0) {
            this.numberOfPages = 0;
        } else {
            this.numberOfPages = numberOfPages;
        }
        if (null== barCode) {
            this.barCode = "INVALID BARCODE";
        } else if (barCode.matches("[0-9]{13,}")) {
            this.barCode = barCode;
        } else {
            this.barCode = "INVALID BARCODE";
        }
        inStock = true;
    }

    /**
     * Returns the title of the book.
     * @return the title of the book
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Returns the author of the book.
     * @return the author of the book
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * Returns the publisher of the book.
     * @return the publisher of the book
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Returns the book's year of release.
     * @return the book's year of release
     */
    public int getYearOfRelease() {
        return yearOfRelease;
    }

    /**
     * Returns the book's number of pages.
     * @return the book's number of pages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Returns the book's bar code.
     * @return the book's bar code.
     */
    public String getBarCode()
    {
        return barCode;
    }

    /**
     * Returns a boolean of whether the book is in stock or not.
     *
     * @return {@code true} if book is in stock,
     * {@code false} if book is loaned out
     */
    public boolean getInStock() { return inStock; }

    /**
     * Loans out the book to given name and returns true if book was in stock,
     * or returns false if the book was loaned out.
     *
     * @return {@code true} if book was loaned out successfully,
     * {@code false} if the book was already loaned out
     */
    public boolean loanBook(String name) {
        boolean success = true;
        if (!this.inStock) {
            success = false;
        } else {
            this.inStock = false;
            this.loanedTo = name;
        }
        return success;
    }

    /**
     * Hands the book back in to the library and returns {@code true} if it was loaned out,
     * or returns {@code false} if the book was already in stock.
     *
     * @return {@code true} if the book was returned successfully,
     * {@code false} if the book was not loaned out
     */
    public boolean returnBook() {
        boolean success = true;
        if(this.inStock) {
            success = false;
        } else {
            this.inStock = true;
            this.loanedTo = null;
        }
        return success;
    }

    /**
     * Returns name as string of person loaning the book
     *
     * @return {@code String} name of person loaning the book;
     * {@code null} if book is in stock
     */
    public String getLoanedTo() {
        return loanedTo;
    }
}