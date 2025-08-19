package com.pahanaedubookshop.dao;

import com.pahanaedubookshop.model.Book;
import com.pahanaedubookshop.util.DatabaseConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class BookDaoTest {

    private BookDao bookDao;

    @Before
    public void setUp() throws Exception {
        bookDao = new BookDao();

        // Clean up table before each test
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM books");
        }
    }

    @Test
    public void testInsertAndGetBookById() throws Exception {
        Book book = new Book();
        book.setTitle("JUnit in Action");
        book.setAuthor("Sam Brannen");
        book.setIsbn("12345");
        book.setPrice(29.99);
        book.setStock(10);

        bookDao.insertBook(book);

        List<Book> allBooks = bookDao.getAllBooks();
        assertEquals(1, allBooks.size());

        Book saved = allBooks.get(0);
        Book fetched = bookDao.getBookById(saved.getBookId());

        assertNotNull(fetched);
        assertEquals("JUnit in Action", fetched.getTitle());
        assertEquals("Sam Brannen", fetched.getAuthor());
    }

    @Test(expected = java.sql.SQLException.class)
    public void testInsertDuplicateIsbnThrowsException() throws Exception {
        Book book1 = new Book();
        book1.setTitle("Book One");
        book1.setAuthor("Author A");
        book1.setIsbn("ISBN-001");
        book1.setPrice(10.0);
        book1.setStock(5);
        bookDao.insertBook(book1);

        Book book2 = new Book();
        book2.setTitle("Book Two");
        book2.setAuthor("Author B");
        book2.setIsbn("ISBN-001"); // same ISBN
        book2.setPrice(12.0);
        book2.setStock(7);
        bookDao.insertBook(book2); // should throw SQLException
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Old Title");
        book.setAuthor("Old Author");
        book.setIsbn("UPDATE-1");
        book.setPrice(20.0);
        book.setStock(3);
        bookDao.insertBook(book);

        Book saved = bookDao.getAllBooks().get(0);
        saved.setTitle("New Title");
        saved.setAuthor("New Author");

        bookDao.updateBook(saved);

        Book updated = bookDao.getBookById(saved.getBookId());
        assertEquals("New Title", updated.getTitle());
        assertEquals("New Author", updated.getAuthor());
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book();
        book.setTitle("Delete Me");
        book.setAuthor("Author X");
        book.setIsbn("DEL-123");
        book.setPrice(15.0);
        book.setStock(4);
        bookDao.insertBook(book);

        Book saved = bookDao.getAllBooks().get(0);
        bookDao.deleteBook(saved.getBookId());

        Book deleted = bookDao.getBookById(saved.getBookId());
        assertNull(deleted);
    }

    @Test
    public void testSearchBooksByKeyword() throws Exception {
        Book b1 = new Book();
        b1.setTitle("Spring Framework");
        b1.setAuthor("Rod Johnson");
        b1.setIsbn("SPR-001");
        b1.setPrice(30.0);
        b1.setStock(8);
        bookDao.insertBook(b1);

        Book b2 = new Book();
        b2.setTitle("Hibernate Basics");
        b2.setAuthor("Gavin King");
        b2.setIsbn("HIB-001");
        b2.setPrice(25.0);
        b2.setStock(6);
        bookDao.insertBook(b2);

        List<Book> result = bookDao.searchBooksByKeyword("Spring");
        assertEquals(1, result.size());
        assertEquals("Spring Framework", result.get(0).getTitle());
    }

    @Test
    public void testIsIsbnTaken() throws Exception {
        Book book = new Book();
        book.setTitle("Unique Book");
        book.setAuthor("Author Y");
        book.setIsbn("UNIQ-123");
        book.setPrice(18.0);
        book.setStock(2);
        bookDao.insertBook(book);

        assertTrue(bookDao.isIsbnTaken("UNIQ-123", null));
        assertFalse(bookDao.isIsbnTaken("NON-EXIST", null));
    }
}
