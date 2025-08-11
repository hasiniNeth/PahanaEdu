package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.dao.BookDao;
import com.pahanaedubookshop.factory.DefaultModelFactory;
import com.pahanaedubookshop.factory.ModelFactory;
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
    private final ModelFactory modelFactory = DefaultModelFactory.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                // note: we expect param name "id" or "bookId" based on your HTML - be consistent
                String idParam = request.getParameter("id");
                if (idParam == null) {
                    idParam = request.getParameter("bookId");
                }
                int bookId = Integer.parseInt(idParam);
                Book book = bookDao.getBookById(bookId);
                request.setAttribute("book", book);
                request.getRequestDispatcher("/WEB-INF/views/edit-book.jsp").forward(request, response);
                return;
            } else {
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
                Book book = modelFactory.createBook();
                book.setTitle(request.getParameter("title"));
                book.setAuthor(request.getParameter("author"));
                book.setIsbn(request.getParameter("isbn"));
                book.setPrice(Double.parseDouble(request.getParameter("price")));
                book.setStock(Integer.parseInt(request.getParameter("stock")));

                bookDao.insertBook(book);
                response.sendRedirect("book-management?message=Book added successfully");

            } else if ("update".equals(action)) {
                Book book = modelFactory.createBook();
                // ensure the form passes "bookId" or "id" consistently
                String bid = request.getParameter("bookId");
                if (bid == null) bid = request.getParameter("id");
                book.setBookId(Integer.parseInt(bid));
                book.setTitle(request.getParameter("title"));
                book.setAuthor(request.getParameter("author"));
                book.setIsbn(request.getParameter("isbn"));
                book.setPrice(Double.parseDouble(request.getParameter("price")));
                book.setStock(Integer.parseInt(request.getParameter("stock")));

                bookDao.updateBook(book);
                response.sendRedirect("book-management?message=Book updated successfully");

            } else if ("delete".equals(action)) {
                String bid = request.getParameter("bookId");
                if (bid == null) bid = request.getParameter("id");
                int bookId = Integer.parseInt(bid);
                bookDao.deleteBook(bookId);
                response.sendRedirect("book-management?message=Book deleted successfully");
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}
