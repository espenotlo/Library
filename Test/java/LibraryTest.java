import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LibraryTest {


    @Test
    public void loadLib() {
    }

    @Test
    public void addBook() {
        Library testLib = new Library();

        assertTrue(testLib.addBook("title","author","publisher",1789,123,"1234567890123"));
        assertFalse(testLib.addBook("title","author","publisher",1789,123,"1234567890123"));
        assertFalse(testLib.addBook(null, null, "publisher", 1234,123,"9876543210123"));
        assertFalse(testLib.addBook(null, "author", "publisher", 1234,123,"9876543210123"));
        assertFalse(testLib.addBook("title", null, "publisher", 1234,123,"9876543210123"));
        assertFalse(testLib.addBook("title", "author", null, 1234,123,"9876543210123"));
        assertFalse(testLib.addBook("title", "author", "publisher", -1234,123,"9876543210123"));
        assertFalse(testLib.addBook("title", "author", "publisher", 1234,-123,"9876543210123"));
        assertFalse(testLib.addBook("title", "author", "publisher", 1234,123,null));


    }

    @Test
    public void removeByTitle() {
        Library testLib = new Library();
        testLib.loadLib();

        assertTrue(testLib.removeByTitle("angels and demons"));
        assertFalse(testLib.removeByTitle("INVALID"));
        assertFalse(testLib.removeByTitle(null));
    }

    @Test
    public void removeByIndex() {
        Library testLib = new Library();
        testLib.loadLib();

        assertTrue(testLib.removeByIndex(0));
        assertFalse(testLib.removeByIndex(5));
    }

    @Test
    public void searchTitle() {
        Library testLib = new Library();
        testLib.loadLib();
        ArrayList<Book> books = new ArrayList<>();
        Book book = testLib.getBookByTitle("Da Vinci Code, The");
        books.add(book);
        assertEquals(books,testLib.searchTitle("da vinci"));

        assertEquals(new ArrayList<>(),testLib.searchTitle("null"));
        }

    @Test
    public void searchAuthor() {
        Library testLib = new Library();
        testLib.loadLib();
        ArrayList<Book> books = new ArrayList<>();
        books.add(testLib.getBookByTitle("Adventures of Tom Sawyer, The"));

        assertEquals(books,testLib.searchAuthor("Mark T"));

        assertEquals(new ArrayList<>(),testLib.searchTitle("null"));
        }

    @Test
    public void searchBarCode() {
        Library testLib = new Library();
        testLib.loadLib();
        ArrayList<Book> books = new ArrayList<>();
        books.add(testLib.getBookByTitle("Da Vinci Code, The"));
        assertEquals(books,testLib.searchBarCode("123456789"));

        assertEquals(new ArrayList<>(),testLib.searchTitle("null"));
        }

    @Test
    public void loanOut() {
        Library testLib = new Library();
        testLib.loadLib();

        assertEquals(1, testLib.loanOut("angels and demons", "Erik"));
        assertEquals(0, testLib.loanOut("INVALID", "INVALID"));
        assertEquals(2, testLib.loanOut("angels and demons", "John"));
        assertEquals (3, testLib.loanOut(null,"feil"));
        assertEquals(3, testLib.loanOut("angels and demons", null));
    }

    @Test
    public void returnBook() {
        Library testLib = new Library();
        testLib.loadLib();
        testLib.loanOut("angels and demons", "John");
        assertEquals(1,testLib.returnBook("angels and demons"));
        assertEquals(2,testLib.returnBook("angels and demons"));
        assertEquals(0,testLib.returnBook("INVALID"));
    }

    @Test
    public void getBooks() {
        Library testLib = new Library();
        testLib.loadLib();

        assertEquals("" +
                "1: Angels and Demons\n" +
                "2: Da Vinci Code, The\n" +
                "3: Lost Symbol, The\n" +
                "4: Adventures of Tom Sawyer, The\n",testLib.getBookList());
    }
}