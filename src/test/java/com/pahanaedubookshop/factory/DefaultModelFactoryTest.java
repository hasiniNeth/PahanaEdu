package com.pahanaedubookshop.factory;

import com.pahanaedubookshop.model.Book;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultModelFactoryTest {

    @Test
    public void testSingletonInstanceNotNull() {
        assertNotNull("INSTANCE should not be null", DefaultModelFactory.INSTANCE);
    }

    @Test
    public void testSingletonInstanceConsistency() {
        DefaultModelFactory instance1 = DefaultModelFactory.INSTANCE;
        DefaultModelFactory instance2 = DefaultModelFactory.INSTANCE;
        assertSame("Both instances should be the same", instance1, instance2);
    }

    @Test
    public void testCreateBookReturnsNewInstance() {
        Book book1 = DefaultModelFactory.INSTANCE.createBook();
        Book book2 = DefaultModelFactory.INSTANCE.createBook();

        assertNotNull("Book instance should not be null", book1);
        assertNotNull("Book instance should not be null", book2);
        assertNotSame("Each call should return a new Book instance", book1, book2);
    }

    @Test
    public void testCreateBookType() {
        Book book = DefaultModelFactory.INSTANCE.createBook();
        assertTrue("Returned object should be of type Book", book instanceof Book);
    }
}
