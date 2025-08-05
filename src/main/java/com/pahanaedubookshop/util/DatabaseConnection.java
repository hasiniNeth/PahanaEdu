package com.pahanaedubookshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    static {
        try {
            // Directly register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    private DatabaseConnection() throws SQLException {
        // Hardcode your database configuration
        String url = "jdbc:mysql://localhost:3306/pahana_edu?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = ""; // or your actual password

        this.connection = DriverManager.getConnection(url, username, password);

        // Test the connection
        if (!this.connection.isValid(5)) {
            throw new SQLException("Database connection validation failed");
        }
    }

    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }


}