package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.model.Staff;
import com.pahanaedubookshop.service.StaffService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff-management")
public class StaffManagementServlet extends HttpServlet {

    private final StaffService staffService = new StaffService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Staff staff = staffService.getStaffById(id);
                request.setAttribute("staff", staff);
                request.getRequestDispatcher("/WEB-INF/views/edit-staff.jsp").forward(request, response);
                return;
            }

            List<Staff> staffList = staffService.getAllStaff();
            request.setAttribute("staffList", staffList);

        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        // ✅ Pass query parameter errors/messages to the JSP
        String errorParam = request.getParameter("error");
        if (errorParam != null && !errorParam.trim().isEmpty()) {
            request.setAttribute("error", errorParam);
        }

        String messageParam = request.getParameter("message");
        if (messageParam != null && !messageParam.trim().isEmpty()) {
            request.setAttribute("message", messageParam);
        }

        request.getRequestDispatcher("/WEB-INF/views/staff-management.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Role check for admin-only actions
        String role = (String) request.getSession().getAttribute("role");
        if (!"admin".equalsIgnoreCase(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("add".equalsIgnoreCase(action)) {
                Staff staff = new Staff();
                staff.setUsername(request.getParameter("username"));
                staff.setPassword(request.getParameter("password"));
                staff.setFullName(request.getParameter("fullName"));
                staff.setRole("staff"); // Always assign "staff" role

                staffService.addStaff(staff);
                response.sendRedirect("staff-management?message=Staff added successfully");

            } else if ("update".equalsIgnoreCase(action)) {
                Staff staff = new Staff();
                staff.setUserId(Integer.parseInt(request.getParameter("id")));
                staff.setUsername(request.getParameter("username"));
                staff.setFullName(request.getParameter("fullName"));

                String password = request.getParameter("password");
                if (password != null && !password.trim().isEmpty()) {
                    staff.setPassword(password);
                }

                staffService.updateStaff(staff);
                response.sendRedirect("staff-management?message=Staff updated successfully");

            } else if ("delete".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                staffService.deleteStaff(id);
                response.sendRedirect("staff-management?message=Staff deleted successfully");

            } else {
                response.sendRedirect("staff-management?error=Invalid action");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("staff-management?error=Invalid ID format");
        } catch (Exception e) {
            response.sendRedirect("staff-management?error=" + e.getMessage());
        }
    }
}
