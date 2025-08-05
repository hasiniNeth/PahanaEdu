package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.model.Book;
import com.pahanaedubookshop.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/book-management")
public class BookManagementServlet extends HttpServlet {
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Book book = bookService.getBookById(id);
                request.setAttribute("book", book);
                request.getRequestDispatcher("/WEB-INF/views/edit-book.jsp").forward(request, response);
                return;
            }

            // Default: list books
            List<Book> books = bookService.getAllBooks();
            request.setAttribute("bookList", books);
            request.setAttribute("message", request.getParameter("message"));
            request.setAttribute("error", request.getParameter("error"));
            request.getRequestDispatcher("/WEB-INF/views/book-management.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/book-management.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = (String) request.getSession().getAttribute("role");
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                Book book = new Book();
                book.setTitle(request.getParameter("title"));
                book.setAuthor(request.getParameter("author"));
                book.setIsbn(request.getParameter("isbn"));
                book.setPrice(Double.parseDouble(request.getParameter("price")));

                bookService.addBook(book);
                response.sendRedirect("book-management?message=Book added successfully");

            } else if ("update".equals(action)) {
                Book book = new Book();
                book.setBookId(Integer.parseInt(request.getParameter("id")));
                book.setTitle(request.getParameter("title"));
                book.setAuthor(request.getParameter("author"));
                book.setIsbn(request.getParameter("isbn"));
                book.setPrice(Double.parseDouble(request.getParameter("price")));

                bookService.updateBook(book);
                response.sendRedirect("book-management?message=Book updated successfully");

            } else if ("delete".equals(action) && "admin".equals(role)) {
                int id = Integer.parseInt(request.getParameter("id"));
                bookService.deleteBook(id);
                response.sendRedirect("book-management?message=Book deleted successfully");

            } else {
                response.sendRedirect("book-management?error=Unauthorized or invalid action.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("book-management?error=" + e.getMessage());
        }
    }
}
