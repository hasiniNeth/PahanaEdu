package com.pahanaedubookshop.service;

import com.pahanaedubookshop.dao.UserDao;
import com.pahanaedubookshop.model.User;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthenticationService {
    private UserDao userDao;
    private Connection connection;

    public AuthenticationService(Connection connection) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Invalid database connection");
        }
        this.connection = connection;
        this.userDao = new UserDao(connection);
    }

    public User authenticate(String username, String password) throws SQLException {
        try {
            if (!connection.isValid(2)) { // 2 second timeout for validation
                throw new SQLException("Database connection not valid");
            }

            User user = userDao.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException("Authentication failed: " + e.getMessage(), e);
        }
    }
}