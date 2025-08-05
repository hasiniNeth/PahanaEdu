package com.pahanaedubookshop.service;

import com.pahanaedubookshop.dao.StaffDao;
import com.pahanaedubookshop.model.Staff;

import java.sql.SQLException;
import java.util.List;

public class StaffService {

    private final StaffDao staffDao = new StaffDao();

    // Add new staff (admin use only)
    public void addStaff(Staff staff) throws SQLException {
        staffDao.addStaff(staff);
    }

    // Get list of all staff
    public List<Staff> getAllStaff() throws SQLException {
        return staffDao.getAllStaff();
    }

    // Get staff member by ID
    public Staff getStaffById(int id) throws SQLException {
        return staffDao.getStaffById(id);
    }

    // Update existing staff details
    public void updateStaff(Staff staff) throws SQLException {
        staffDao.updateStaff(staff);
    }

    // Delete staff by ID
    public void deleteStaff(int id) throws SQLException {
        staffDao.deleteStaff(id);
    }
}
