import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {

    Book book1 = new Book("tittel1","forfatter1","forlag1",1234,12,"1234567890321");
    Book book2;
    Book book3;
    @Test
    public void constructorTest() {
        book2 = new Book("tittel", "forfatter", "forlag",1998,137,"1234567890123");
        assertEquals("tittel", book2.getTitle());
        assertEquals("forfatter", book2.getAuthor());
        assertEquals("forlag", book2.getPublisher());
        assertEquals(1998, book2.getYearOfRelease());
        assertEquals(137, book2.getNumberOfPages());
        assertEquals("1234567890123", book2.getBarCode());
        assertTrue(book2.getInStock());
        assertNull(book2.getLoanedTo());
    }
    @Test
    public void createInvalidBook() {
        book3 = new Book(null,null,null,-1234,-12,null);
        assertEquals("INVALID TITLE", book3.getTitle());
        assertEquals("INVALID AUTHOR", book3.getAuthor());
        assertEquals("INVALID PUBLISHER", book3.getPublisher());
        assertEquals(1337,book3.getYearOfRelease());
        assertEquals(0,book3.getNumberOfPages());
        assertEquals("INVALID BARCODE", book3.getBarCode());
    }

    @Test
    public void getTitle() {
        assertEquals("tittel1", book1.getTitle());
    }

    @Test
    public void getAuthor() {
        assertEquals("forfatter1", book1.getAuthor());
    }

    @Test
    public void getPublisher() {
        assertEquals("forlag1", book1.getPublisher());
    }

    @Test
    public void getYearOfRelease() {
        assertEquals(1234, book1.getYearOfRelease());
    }

    @Test
    public void getNumberOfPages() {
        assertEquals(12, book1.getNumberOfPages());
    }

    @Test
    public void getBarCode() {
        assertEquals("1234567890321", book1.getBarCode());
    }

    @Test
    public void getInStock() {
        assertTrue(book1.getInStock());
    }

    @Test
    public void getLoanedTo() {
        assertNull(book1.getLoanedTo());
    }

    @Test
    public void returnBook() {
        book1.returnBook();
        assertTrue(book1.getInStock());
    }
}