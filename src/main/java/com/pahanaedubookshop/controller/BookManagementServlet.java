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

        // ✅ Read success message from query param
        String message = request.getParameter("message");
        if (message != null) {
            request.setAttribute("message", message);
        }

        try {
            if ("edit".equals(action)) {
                String idParam = request.getParameter("id");
                if (idParam == null) {
                    idParam = request.getParameter("bookId");
                }
                int bookId = Integer.parseInt(idParam);
                Book book = bookDao.getBookById(bookId);
                request.setAttribute("editingBook", book);
                request.setAttribute("books", bookDao.getAllBooks());
                request.getRequestDispatcher("/WEB-INF/views/book-management.jsp").forward(request, response);
                return;
            } else {
                List<Book> books = bookDao.getAllBooks();
                request.setAttribute("books", books);
                request.getRequestDispatcher("/WEB-INF/views/book-management.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            try {
                request.setAttribute("books", bookDao.getAllBooks());
            } catch (SQLException ignored) {}
            request.getRequestDispatcher("/WEB-INF/views/book-management.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action) || "update".equals(action)) {
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String isbn = request.getParameter("isbn");
                String priceStr = request.getParameter("price");
                String stockStr = request.getParameter("stock");

                // === Validation ===
                if (title == null || title.trim().length() < 2) {
                    request.setAttribute("error", "Title must be at least 2 characters long.");
                    doGet(request, response);
                    return;
                }

                if (isbn != null && !isbn.trim().isEmpty()) {
                    if (!isbn.matches("^[0-9\\-]{10,13}$")) {
                        request.setAttribute("error", "ISBN must be 10–13 characters, only digits and dashes.");
                        doGet(request, response);
                        return;
                    }
                }

                double price;
                int stock;
                try {
                    price = Double.parseDouble(priceStr);
                    if (price <= 0) {
                        throw new NumberFormatException("Price must be greater than 0");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid price. Must be a number greater than 0.");
                    doGet(request, response);
                    return;
                }

                try {
                    stock = Integer.parseInt(stockStr);
                    if (stock < 0) {
                        throw new NumberFormatException("Stock cannot be negative");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid stock. Must be a non-negative number.");
                    doGet(request, response);
                    return;
                }

                Book book = modelFactory.createBook();
                book.setTitle(title.trim());
                book.setAuthor(author != null ? author.trim() : "");
                book.setIsbn(isbn != null ? isbn.trim() : "");
                book.setPrice(price);
                book.setStock(stock);

                if ("add".equals(action)) {
                    // Duplicate ISBN check
                    if (book.getIsbn() != null && !book.getIsbn().isEmpty() &&
                            bookDao.isIsbnTaken(book.getIsbn(), null)) {
                        request.setAttribute("error", "A book with this ISBN already exists.");
                        request.setAttribute("editingBook", book);
                        request.setAttribute("books", bookDao.getAllBooks());
                        request.getRequestDispatcher("/WEB-INF/views/book-management.jsp").forward(request, response);
                        return;
                    }

                    bookDao.insertBook(book);
                    response.sendRedirect("book-management?message=Book added successfully");
                } else { // update
                    String bid = request.getParameter("bookId");
                    if (bid == null) bid = request.getParameter("id");
                    book.setBookId(Integer.parseInt(bid));

                    if (book.getIsbn() != null && !book.getIsbn().isEmpty() &&
                            bookDao.isIsbnTaken(book.getIsbn(), book.getBookId())) {
                        request.setAttribute("error", "Another book with this ISBN already exists.");
                        request.setAttribute("editingBook", book);
                        request.setAttribute("books", bookDao.getAllBooks());
                        request.getRequestDispatcher("/WEB-INF/views/book-management.jsp").forward(request, response);
                        return;
                    }

                    bookDao.updateBook(book);
                    response.sendRedirect("book-management?message=Book updated successfully");
                }
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
