package com.pahanaedubookshop.service;

import com.pahanaedubookshop.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

public class AuthenticationServiceTest {

    private Connection connection;
    private AuthenticationService authenticationService;
    private String testUsername = "testuser_junit";

    @Before
    public void setUp() throws Exception {
        // Connect to your MySQL database
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pahana_edu",
                "root",
                "" // your MySQL password
        );

        // Ensure the test user does not already exist
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM users WHERE username = ?")) {
            ps.setString(1, testUsername);
            ps.executeUpdate();
        }

        // Insert a known test user
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users (username, password, role, full_name) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, testUsername);
            ps.setString(2, "password123");
            ps.setString(3, "staff");
            ps.setString(4, "Test User");
            ps.executeUpdate();
        }

        authenticationService = new AuthenticationService(connection);
    }

    @After
    public void tearDown() throws Exception {
        // Clean up test user
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM users WHERE username = ?")) {
            ps.setString(1, testUsername);
            ps.executeUpdate();
        }

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAuthenticateSuccess() throws Exception {
        User user = authenticationService.authenticate(testUsername, "password123");
        assertNotNull("User should be authenticated", user);
        assertEquals("Username should match", testUsername, user.getUsername());
        assertEquals("Role should match", "staff", user.getRole());
    }

    @Test
    public void testAuthenticateWrongPassword() throws Exception {
        User user = authenticationService.authenticate(testUsername, "wrongpass");
        assertNull("Authentication should fail with wrong password", user);
    }

    @Test
    public void testAuthenticateUnknownUser() throws Exception {
        User user = authenticationService.authenticate("nosuchuser_junit", "password123");
        assertNull("Authentication should fail for unknown user", user);
    }

    @Test(expected = SQLException.class)
    public void testInvalidConnection() throws Exception {
        Connection badConn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pahana_edu",
                "root",
                ""
        );
        badConn.close(); // close it immediately
        new AuthenticationService(badConn); // should throw SQLException
    }
}
