package com.pahanaedubookshop.service;

import com.pahanaedubookshop.dao.StaffDao;
import com.pahanaedubookshop.model.Staff;
import com.pahanaedubookshop.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StaffService {
    private final StaffDao staffDao;

    public StaffService() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        this.staffDao = new StaffDao(conn);
    }

    public List<Staff> getAllStaff() throws SQLException {
        return staffDao.getAllStaff();
    }

    public void addStaff(Staff staff) throws SQLException {
        staffDao.addStaff(staff);
    }

    public void updateStaff(String originalUsername, Staff staff) throws SQLException {
        staffDao.updateStaff(originalUsername, staff);
    }

    public void deleteStaff(String username) throws SQLException {
        staffDao.deleteStaff(username);
    }
}
