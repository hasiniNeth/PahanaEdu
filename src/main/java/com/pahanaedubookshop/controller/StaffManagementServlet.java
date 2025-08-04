package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.model.Staff;
import com.pahanaedubookshop.service.StaffService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff-management")
public class StaffManagementServlet extends HttpServlet {
    private StaffService staffService = new StaffService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is admin
        if (!"admin".equals(request.getSession().getAttribute("role"))) {
            request.setAttribute("error", "Access denied. Admin privileges required.");
        } else {
            List<Staff> staffList = staffService.getAllStaff();
            request.setAttribute("staffList", staffList);
        }

        request.getRequestDispatcher("/staff-management.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verify admin role
        if (!"admin".equals(request.getSession().getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access required");
            return;
        }

        String action = request.getParameter("action");
        String message = "";
        String error = "";

        try {
            switch (action) {
                case "add":
                    Staff newStaff = new Staff();
                    newStaff.setUsername(request.getParameter("username"));
                    newStaff.setPassword(request.getParameter("password"));
                    newStaff.setFullName(request.getParameter("fullName"));
                    newStaff.setEmail(request.getParameter("email"));
                    newStaff.setRole(request.getParameter("role"));
                    staffService.addStaff(newStaff);
                    message = "Staff member added successfully";
                    break;

                case "edit":
                    Staff updatedStaff = new Staff();
                    updatedStaff.setUsername(request.getParameter("username"));
                    // Only update password if provided
                    String newPassword = request.getParameter("password");
                    if (newPassword != null && !newPassword.isEmpty()) {
                        updatedStaff.setPassword(newPassword);
                    }
                    updatedStaff.setFullName(request.getParameter("fullName"));
                    updatedStaff.setEmail(request.getParameter("email"));
                    updatedStaff.setRole(request.getParameter("role"));

                    String originalUsername = request.getParameter("originalUsername");
                    staffService.updateStaff(originalUsername, updatedStaff);
                    message = "Staff member updated successfully";
                    break;

                case "delete":
                    String usernameToDelete = request.getParameter("username");
                    staffService.deleteStaff(usernameToDelete);
                    message = "Staff member deleted successfully";
                    break;

                default:
                    error = "Invalid action";
            }
        } catch (Exception e) {
            error = "Error processing request: " + e.getMessage();
        }

        request.setAttribute("message", message);
        request.setAttribute("error", error);
        doGet(request, response);
    }
}