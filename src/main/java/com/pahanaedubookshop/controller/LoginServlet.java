package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.model.User;
import com.pahanaedubookshop.service.AuthenticationService;
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
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            AuthenticationService authService = new AuthenticationService(connection);
            User user = authService.authenticate(username, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                String redirectPage = "admin".equalsIgnoreCase(user.getRole())
                        ? "adminDashboard.jsp" : "staffDashboard.jsp";
                response.sendRedirect(redirectPage);
            } else {
                handleFailedLogin(request, response, "Invalid username or password");
            }

        } catch (SQLException e) {
            logger.severe("Database error during login: " + e.getMessage());
            handleFailedLogin(request, response,
                    "System temporarily unavailable. Please try again later.");
        } catch (Exception e) {
            logger.severe("Unexpected error during login: " + e.getMessage());
            handleFailedLogin(request, response,
                    "An unexpected error occurred. Our team has been notified.");
        }
    }

    private void handleFailedLogin(HttpServletRequest request,
                                   HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}