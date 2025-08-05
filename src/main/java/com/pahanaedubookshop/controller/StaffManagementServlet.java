package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.model.Staff;
import com.pahanaedubookshop.service.StaffService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/staff-management")
public class StaffManagementServlet extends HttpServlet {
    private StaffService staffService;

    @Override
    public void init() throws ServletException {
        try {
            staffService = new StaffService();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize StaffService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String role = (String) request.getSession().getAttribute("role");
            if (!"admin".equals(role)) {
                request.setAttribute("error", "Access denied. Admin privileges required.");
            } else {
                List<Staff> staffList = staffService.getAllStaff();
                request.setAttribute("staffList", staffList);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/views/staff-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"admin".equals(request.getSession().getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access required");
            return;
        }

        String action = request.getParameter("action");
        String message = null;
        String error = null;

        try {
            switch (action) {
                case "add":
                    Staff newStaff = new Staff();
                    newStaff.setUsername(request.getParameter("username"));
                    newStaff.setPassword(request.getParameter("password"));
                    newStaff.setFullName(request.getParameter("fullName"));
                    newStaff.setRole(request.getParameter("role"));
                    staffService.addStaff(newStaff);
                    message = "Staff added successfully.";
                    break;
                case "edit":
                    Staff updatedStaff = new Staff();
                    updatedStaff.setUsername(request.getParameter("username"));
                    updatedStaff.setFullName(request.getParameter("fullName"));
                    updatedStaff.setRole(request.getParameter("role"));

                    String password = request.getParameter("password");
                    if (password != null && !password.isEmpty()) {
                        updatedStaff.setPassword(password);
                    }

                    String originalUsername = request.getParameter("originalUsername");
                    staffService.updateStaff(originalUsername, updatedStaff);
                    message = "Staff updated successfully.";
                    break;
                case "delete":
                    String username = request.getParameter("username");
                    staffService.deleteStaff(username);
                    message = "Staff deleted successfully.";
                    break;
                default:
                    error = "Unknown action.";
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        request.setAttribute("message", message);
        request.setAttribute("error", error);
        doGet(request, response);
    }
}
