package com.pahanaedubookshop.dao;

import com.pahanaedubookshop.model.Staff;
import com.pahanaedubookshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDao {

    public void addStaff(Staff staff) throws SQLException {
        String sql = "INSERT INTO users (username, password, role, full_name) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, staff.getUsername());
            stmt.setString(2, staff.getPassword());
            stmt.setString(3, "staff"); // Always staff
            stmt.setString(4, staff.getFullName());
            stmt.executeUpdate();
        }
    }

    public List<Staff> getAllStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'staff'";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Staff staff = new Staff();
                staff.setUserId(rs.getInt("user_id"));
                staff.setUsername(rs.getString("username"));
                staff.setPassword(rs.getString("password")); // Optional
                staff.setFullName(rs.getString("full_name"));
                staff.setRole(rs.getString("role"));
                staffList.add(staff);
            }
        }
        return staffList;
    }

    public void updateStaff(Staff staff) throws SQLException {
        String sql;
        boolean updatePassword = staff.getPassword() != null && !staff.getPassword().isEmpty();

        if (updatePassword) {
            sql = "UPDATE users SET username = ?, password = ?, full_name = ? WHERE user_id = ?";
        } else {
            sql = "UPDATE users SET username = ?, full_name = ? WHERE user_id = ?";
        }

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, staff.getUsername());

            int index = 2;
            if (updatePassword) {
                stmt.setString(index++, staff.getPassword());
            }

            stmt.setString(index++, staff.getFullName());
            stmt.setInt(index, staff.getUserId());

            stmt.executeUpdate();
        }
    }


    public void deleteStaff(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ? AND role = 'staff'";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public Staff getStaffById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ? AND role = 'staff'";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Staff staff = new Staff();
                    staff.setUserId(rs.getInt("user_id"));
                    staff.setUsername(rs.getString("username"));
                    staff.setPassword(rs.getString("password"));
                    staff.setFullName(rs.getString("full_name"));
                    staff.setRole(rs.getString("role"));
                    return staff;
                }
            }
        }
        return null;
    }
}
