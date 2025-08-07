package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.dao.BookDao;
import com.pahanaedubookshop.dao.CustomerDAO;
import com.pahanaedubookshop.model.Bill;
import com.pahanaedubookshop.model.BillItem;
import com.pahanaedubookshop.model.Book;
import com.pahanaedubookshop.model.Customer;
import com.pahanaedubookshop.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/bill-create")
public class BillControllerServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final BookDao bookDao = new BookDao();
    private final BillService billService = new BillService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("searchCustomer".equals(action)) {
                String account = request.getParameter("account");
                Customer customer = customerDAO.getCustomerByAccountNumber(account);
                if (customer != null) {
                    request.getSession().setAttribute("selectedCustomer", customer);
                } else {
                    request.setAttribute("error", "Customer not found");
                }

            } else if ("searchBooks".equals(action)) {
                String keyword = request.getParameter("keyword");
                List<Book> books = bookDao.searchBooksByKeyword(keyword);
                request.setAttribute("searchResults", books);
            }
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/bill-create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<BillItem> cart = (List<BillItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");

        try {
            if ("addToBill".equals(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                Book book = bookDao.getBookById(bookId);

                if (book != null && quantity > 0 && book.getStock() >= quantity) {
                    BillItem item = new BillItem();
                    item.setBookId(bookId);
                    item.setBookTitle(book.getTitle());
                    item.setQuantity(quantity);
                    item.setPrice(book.getPrice());
                    item.setSubtotal(book.getPrice() * quantity);

                    cart.add(item);
                } else {
                    request.setAttribute("error", "Invalid quantity or out of stock.");
                }

            } else if ("removeItem".equals(action)) {
                int index = Integer.parseInt(request.getParameter("index"));
                if (index >= 0 && index < cart.size()) {
                    cart.remove(index);
                }

            } else if ("printBill".equals(action)) {
                Customer customer = (Customer) request.getAttribute("customer");
                if (customer != null && !cart.isEmpty()) {
                    Bill bill = new Bill();
                    bill.setCustomerId(customer.getCustomerId());
                    bill.setItems(cart);
                    double total = cart.stream().mapToDouble(BillItem::getSubtotal).sum();
                    bill.setTotalAmount(total);

                    billService.saveBill(bill);

                    session.removeAttribute("cart");
                    session.removeAttribute("selectedCustomer");
                    request.setAttribute("message", "Bill generated successfully!");
                } else {
                    request.setAttribute("error", "No customer or bill items found");
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/bill-create.jsp").forward(request, response);
    }
}
