package com.pahanaedubookshop.service;

import com.pahanaedubookshop.model.Staff;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class StaffServiceTest {

    private StaffService staffService;
    private Staff testStaff;

    @Before
    public void setUp() throws SQLException {
        staffService = new StaffService();

        // Create a unique username to avoid duplicates
        String uniqueUsername = "testuser_" + System.currentTimeMillis();

        testStaff = new Staff();
        testStaff.setUsername(uniqueUsername);
        testStaff.setPassword("password");
        testStaff.setFullName("Test User");
        testStaff.setRole("staff");

        // Insert the test staff into DB
        staffService.addStaff(testStaff);

        // Retrieve the inserted staff to get the generated userId
        List<Staff> staffList = staffService.getAllStaff();
        for (Staff s : staffList) {
            if (s.getUsername().equals(uniqueUsername)) {
                testStaff.setUserId(s.getUserId());
                break;
            }
        }
    }

    @After
    public void tearDown() throws SQLException {
        if (testStaff.getUserId() != 0) {
            staffService.deleteStaff(testStaff.getUserId());
        }
    }

    @Test
    public void testGetAllStaff() throws SQLException {
        List<Staff> staffList = staffService.getAllStaff();
        assertNotNull(staffList);
        assertTrue(staffList.size() > 0);
    }

    @Test
    public void testGetStaffById() throws SQLException {
        Staff staff = staffService.getStaffById(testStaff.getUserId());
        assertNotNull(staff);
        assertEquals(testStaff.getUsername(), staff.getUsername());
    }

    @Test
    public void testUpdateStaff() throws SQLException {
        testStaff.setFullName("Updated Name");
        staffService.updateStaff(testStaff);

        Staff updated = staffService.getStaffById(testStaff.getUserId());
        assertNotNull(updated);
        assertEquals("Updated Name", updated.getFullName());
    }

    @Test
    public void testDeleteStaff() throws SQLException {
        Staff newStaff = new Staff();
        String uniqueUsername = "deleteuser_" + System.currentTimeMillis();
        newStaff.setUsername(uniqueUsername);
        newStaff.setPassword("pass123");
        newStaff.setFullName("Delete User");
        newStaff.setRole("staff");

        staffService.addStaff(newStaff);

        // Retrieve the inserted staff to get userId
        List<Staff> staffList = staffService.getAllStaff();
        int idToDelete = 0;
        for (Staff s : staffList) {
            if (s.getUsername().equals(uniqueUsername)) {
                idToDelete = s.getUserId();
                break;
            }
        }

        staffService.deleteStaff(idToDelete);
        Staff deleted = staffService.getStaffById(idToDelete);
        assertNull(deleted);
    }
}
