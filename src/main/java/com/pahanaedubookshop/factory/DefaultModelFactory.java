package com.pahanaedubookshop.factory;

import com.pahanaedubookshop.model.Book;

/**
 * Very small singleton factory returning plain model instances.
 * Replace DefaultModelFactory.INSTANCE.createX() where needed.
 */
public final class DefaultModelFactory implements ModelFactory {

    // Singleton instance (thread-safe for this use case)
    public static final DefaultModelFactory INSTANCE = new DefaultModelFactory();

    // private constructor to prevent external instantiation
    private DefaultModelFactory() {}

    @Override
    public Book createBook() {
        return new Book();
    }
}
