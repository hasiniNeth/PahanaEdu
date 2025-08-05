package com.pahanaedubookshop.dao;

import com.pahanaedubookshop.model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDao {
    private final Connection conn;

    public StaffDao(Connection conn) {
        this.conn = conn;
    }

    public List<Staff> getAllStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT user_id, username, full_name, role FROM users WHERE role IN ('staff', 'admin')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setUserId(rs.getInt("user_id"));
                staff.setUsername(rs.getString("username"));
                staff.setFullName(rs.getString("full_name"));
                staff.setRole(rs.getString("role"));
                staffList.add(staff);
            }
        }
        return staffList;
    }

    public void addStaff(Staff staff) throws SQLException {
        String sql = "INSERT INTO users (username, password, full_name, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, staff.getUsername());
            stmt.setString(2, staff.getPassword());
            stmt.setString(3, staff.getFullName());
            stmt.setString(4, staff.getRole());
            stmt.executeUpdate();
        }
    }

    public void updateStaff(String originalUsername, Staff updatedStaff) throws SQLException {
        String sql = "UPDATE users SET username=?, full_name=?, role=? WHERE username=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, updatedStaff.getUsername());
            stmt.setString(2, updatedStaff.getFullName());
            stmt.setString(4, updatedStaff.getRole());
            stmt.setString(5, originalUsername);
            stmt.executeUpdate();
        }

        if (updatedStaff.getPassword() != null && !updatedStaff.getPassword().isEmpty()) {
            String pwdSql = "UPDATE users SET password=? WHERE username=?";
            try (PreparedStatement stmt = conn.prepareStatement(pwdSql)) {
                stmt.setString(1, updatedStaff.getPassword());
                stmt.setString(2, updatedStaff.getUsername());
                stmt.executeUpdate();
            }
        }
    }

    public void deleteStaff(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username=? AND role IN ('staff', 'admin')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}
