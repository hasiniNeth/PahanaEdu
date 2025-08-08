package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.dao.BookDao;
import com.pahanaedubookshop.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/book-management")
public class BookManagementServlet extends HttpServlet {
    private final BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                int bookId = Integer.parseInt(request.getParameter("id"));
                Book book = bookDao.getBookById(bookId);
                request.setAttribute("book", book);
                request.getRequestDispatcher("/WEB-INF/views/edit-book.jsp").forward(request, response);
                return;

            } else {
                // Default action: show list
                List<Book> books = bookDao.getAllBooks();
                request.setAttribute("books", books);
                request.getRequestDispatcher("/WEB-INF/views/book-management.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/book-management.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                Book book = new Book();
                book.setTitle(request.getParameter("title"));
                book.setAuthor(request.getParameter("author"));
                book.setIsbn(request.getParameter("isbn"));
                book.setPrice(Double.parseDouble(request.getParameter("price")));
                book.setStock(Integer.parseInt(request.getParameter("stock")));

                bookDao.insertBook(book);
                response.sendRedirect("book-management?message=Book added successfully");

            } else if ("update".equals(action)) {
                Book book = new Book();
                book.setBookId(Integer.parseInt(request.getParameter("bookId")));
                book.setTitle(request.getParameter("title"));
                book.setAuthor(request.getParameter("author"));
                book.setIsbn(request.getParameter("isbn"));
                book.setPrice(Double.parseDouble(request.getParameter("price")));
                book.setStock(Integer.parseInt(request.getParameter("stock")));

                bookDao.updateBook(book);
                response.sendRedirect("book-management?message=Book updated successfully");

            } else if ("delete".equals(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                bookDao.deleteBook(bookId);
                response.sendRedirect("book-management?message=Book deleted successfully");
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}