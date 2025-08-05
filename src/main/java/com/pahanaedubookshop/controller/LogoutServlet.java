package com.pahanaedubookshop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Invalidate the session if it exists
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            // Redirect to login page with absolute path
            String contextPath = request.getContextPath();
            response.sendRedirect(request.getContextPath() + "/login.jsp");

        } catch (Exception e) {
            // Fallback to error page if redirect fails
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Logout failed. Please try again.");
        }
    }
}