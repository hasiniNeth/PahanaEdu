package com.pahanaedubookshop.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void testBookGettersAndSetters() {
        Book book = new Book();

        // --- Set values ---
        book.setBookId(1);
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setIsbn("978-0134685991");
        book.setPrice(450.0);
        book.setStock(10);

        // --- Assertions ---
        assertEquals(1, book.getBookId());
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals("978-0134685991", book.getIsbn());
        assertEquals(450.0, book.getPrice(), 0.001);
        assertEquals(10, book.getStock());
    }
}
