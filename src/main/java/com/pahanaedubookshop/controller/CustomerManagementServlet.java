package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.dao.CustomerDao;
import com.pahanaedubookshop.model.Customer;
import com.pahanaedubookshop.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/customer-management")
public class CustomerManagementServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            CustomerDao customerDao = new CustomerDao(connection);
            List<Customer> customers = customerDao.getAllCustomers();

            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/WEB-INF/views/customer-management.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Error retrieving customer data");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            CustomerDao customerDao = new CustomerDao(connection);

            switch (action) {
                case "add": addCustomer(request, customerDao); break;
                case "edit": editCustomer(request, customerDao); break;
                case "delete":
                    if (!"admin".equals(role)) {
                        request.setAttribute("error", "Only admin can delete customers");
                        break;
                    }
                    deleteCustomer(request, customerDao);
                    break;
            }

            response.sendRedirect(request.getContextPath() + "/customer-management");

        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void addCustomer(HttpServletRequest request, CustomerDao customerDao)
            throws SQLException {
        Customer customer = new Customer(
                request.getParameter("accountNumber"),
                request.getParameter("name"),
                request.getParameter("address"),
                request.getParameter("telephone")
        );

        customerDao.addCustomer(customer);
    }

    private void editCustomer(HttpServletRequest request, CustomerDao customerDao)
            throws SQLException {
        Customer customer = new Customer(
                request.getParameter("accountNumber"),
                request.getParameter("name"),
                request.getParameter("address"),
                request.getParameter("telephone")
        );

        customerDao.updateCustomer(customer);
    }

    private void deleteCustomer(HttpServletRequest request, CustomerDao customerDao)
            throws SQLException {
        String accountNumber = request.getParameter("accountNumber");
        customerDao.deleteCustomer(accountNumber);
    }
}