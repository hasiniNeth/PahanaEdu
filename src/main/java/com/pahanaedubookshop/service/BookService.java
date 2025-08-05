package com.pahanaedubookshop.service;

import com.pahanaedubookshop.dao.BookDao;
import com.pahanaedubookshop.model.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final BookDao bookDao = new BookDao();

    public List<Book> getAllBooks() {
        try {
            return bookDao.getAllBooks();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void addBook(Book book) throws SQLException {
        bookDao.addBook(book);
    }

    public void updateBook(Book book) throws SQLException {
        bookDao.updateBook(book);
    }

    public void deleteBook(int id) throws SQLException {
        bookDao.deleteBook(id);
    }

    public Book getBookById(int id) throws SQLException {
        return bookDao.getBookById(id);
    }
}
