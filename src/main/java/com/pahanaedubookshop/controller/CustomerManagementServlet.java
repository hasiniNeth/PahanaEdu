package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.dao.CustomerDAO;
import com.pahanaedubookshop.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/customer-management")
public class CustomerManagementServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        try {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Customer customer = customerDAO.getCustomerById(id);
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("/WEB-INF/views/edit-customer.jsp").forward(request, response);

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                customerDAO.deleteCustomer(id);
                response.sendRedirect("customer-management?success=Customer deleted successfully");

            } else {
                List<Customer> customers = customerDAO.getAllCustomers();
                request.setAttribute("customerList", customers);
                request.setAttribute("success", request.getParameter("success"));
                request.getRequestDispatcher("/WEB-INF/views/customer-management.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        try {
            if ("add".equals(action)) {
                Customer customer = new Customer();
                customer.setAccountNumber(customerDAO.generateAccountNumber()); // Auto-generate
                customer.setFullName(request.getParameter("fullName"));
                customer.setAddress(request.getParameter("address"));
                customer.setTelephone(request.getParameter("telephone"));

                customerDAO.addCustomer(customer);
                response.sendRedirect("customer-management?success=Customer added successfully");

            } else if ("update".equals(action)) {
                Customer customer = new Customer();
                customer.setCustomerId(Integer.parseInt(request.getParameter("id")));
                customer.setFullName(request.getParameter("fullName"));
                customer.setAddress(request.getParameter("address"));
                customer.setTelephone(request.getParameter("telephone"));

                customerDAO.updateCustomer(customer);
                response.sendRedirect("customer-management?success=Customer updated successfully");

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                customerDAO.deleteCustomer(id);
                response.sendRedirect("customer-management?success=Customer deleted successfully");
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
