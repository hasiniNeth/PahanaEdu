package com.pahanaedubookshop.dao;

import com.pahanaedubookshop.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDao {
    private final Connection conn;

    public StaffDao(Connection conn) {
        this.conn = conn;
    }

    public List<Staff> getAllStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT id, username, full_name, email, telephone, role FROM users WHERE role IN ('staff', 'admin')";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setUsername(rs.getString("username"));
                staff.setFullName(rs.getString("full_name"));
                staff.setEmail(rs.getString("email"));
                staff.setTelephone(rs.getString("telephone"));
                staff.setRole(rs.getString("role"));
                staffList.add(staff);
            }
        }
        return staffList;
    }

    public void addStaff(Staff staff, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password, full_name, email, telephone, role) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, staff.getUsername());
            stmt.setString(2, password); // Storing plain password (not recommended)
            stmt.setString(3, staff.getFullName());
            stmt.setString(4, staff.getEmail());
            stmt.setString(5, staff.getTelephone());
            stmt.setString(6, staff.getRole() != null ? staff.getRole() : "staff");
            stmt.executeUpdate();
        }
    }

    public void updateStaff(Staff staff) throws SQLException {
        String sql = "UPDATE users SET full_name=?, email=?, telephone=?, role=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, staff.getFullName());
            stmt.setString(2, staff.getEmail());
            stmt.setString(3, staff.getTelephone());
            stmt.setString(4, staff.getRole());
            stmt.setInt(5, staff.getId());
            stmt.executeUpdate();
        }
    }

    public void updatePassword(int staffId, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, staffId);
            stmt.executeUpdate();
        }
    }

    public void deleteStaff(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id=? AND role IN ('staff', 'admin')";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Staff getStaffById(int id) throws SQLException {
        String sql = "SELECT id, username, full_name, email, telephone, role FROM users WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Staff staff = new Staff();
                    staff.setId(rs.getInt("id"));
                    staff.setUsername(rs.getString("username"));
                    staff.setFullName(rs.getString("full_name"));
                    staff.setEmail(rs.getString("email"));
                    staff.setTelephone(rs.getString("telephone"));
                    staff.setRole(rs.getString("role"));
                    return staff;
                }
            }
        }
        return null;
    }

    public Staff authenticate(String username, String password) throws SQLException {
        String sql = "SELECT id, username, full_name, email, telephone, role FROM users " +
                "WHERE username=? AND password=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Staff staff = new Staff();
                    staff.setId(rs.getInt("id"));
                    staff.setUsername(rs.getString("username"));
                    staff.setFullName(rs.getString("full_name"));
                    staff.setEmail(rs.getString("email"));
                    staff.setTelephone(rs.getString("telephone"));
                    staff.setRole(rs.getString("role"));
                    return staff;
                }
            }
        }
        return null;
    }
}