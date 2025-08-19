package com.pahanaedubookshop.service;

import com.pahanaedubookshop.dao.BookDao;
import com.pahanaedubookshop.model.Book;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class BookServiceTest {

    private static BookService bookService;

    @BeforeClass
    public static void setupOnce() {
        // Initialize real BookService (which internally uses BookDao)
        bookService = new BookService();
    }

    @Before
    public void setup() throws SQLException {
        // Clean DB before each test (truncate or delete test data)
        List<Book> books = bookService.getAllBooks();
        for (Book book : books) {
            bookService.deleteBook(book.getBookId());
        }
    }

    @Test
    public void testAddAndGetBook() throws SQLException {
        Book book = new Book();
        book.setTitle("JUnit in Action");
        book.setAuthor("Smith");
        book.setIsbn("123456789");
        book.setPrice(49.99);

        bookService.addBook(book);

        List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());

        Book saved = books.get(0);
        assertEquals("JUnit in Action", saved.getTitle());
        assertEquals("Smith", saved.getAuthor());
        assertEquals("123456789", saved.getIsbn());
        assertEquals(49.99, saved.getPrice(), 0.001);
    }

    @Test
    public void testUpdateBook() throws SQLException {
        Book book = new Book();
        book.setTitle("Old Title");
        book.setAuthor("Author A");
        book.setIsbn("111111111");
        book.setPrice(20.0);
        bookService.addBook(book);

        Book saved = bookService.getAllBooks().get(0);
        saved.setTitle("New Title");
        saved.setPrice(25.0);

        bookService.updateBook(saved);

        Book updated = bookService.getBookById(saved.getBookId());
        assertEquals("New Title", updated.getTitle());
        assertEquals(25.0, updated.getPrice(), 0.001);
    }

    @Test
    public void testDeleteBook() throws SQLException {
        Book book = new Book();
        book.setTitle("Temp Book");
        book.setAuthor("Author B");
        book.setIsbn("222222222");
        book.setPrice(10.0);
        bookService.addBook(book);

        List<Book> beforeDelete = bookService.getAllBooks();
        assertEquals(1, beforeDelete.size());

        bookService.deleteBook(beforeDelete.get(0).getBookId());

        List<Book> afterDelete = bookService.getAllBooks();
        assertTrue(afterDelete.isEmpty());
    }
}
