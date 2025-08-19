package com.pahanaedubookshop.dao;

import com.pahanaedubookshop.model.Staff;
import com.pahanaedubookshop.util.DatabaseConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class StaffDaoTest {

    private StaffDao staffDao;

    @Before
    public void setUp() throws Exception {
        staffDao = new StaffDao();

        // Clean staff rows before each test
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users WHERE role = 'staff'");
        }
    }

    @Test
    public void testAddAndGetStaff() throws Exception {
        Staff staff = new Staff();
        staff.setUsername("staff1");
        staff.setPassword("pass123");
        staff.setFullName("John Staff");

        staffDao.addStaff(staff);

        List<Staff> allStaff = staffDao.getAllStaff();
        assertEquals(1, allStaff.size());

        Staff saved = allStaff.get(0);
        assertEquals("staff1", saved.getUsername());
        assertEquals("John Staff", saved.getFullName());
        assertEquals("staff", saved.getRole());

        // Get by ID
        Staff byId = staffDao.getStaffById(saved.getUserId());
        assertNotNull(byId);
        assertEquals("staff1", byId.getUsername());
    }

    @Test
    public void testUpdateStaffWithoutPassword() throws Exception {
        Staff staff = new Staff();
        staff.setUsername("staff2");
        staff.setPassword("secret");
        staff.setFullName("Jane Staff");
        staffDao.addStaff(staff);

        Staff saved = staffDao.getAllStaff().get(0);

        // Update without password
        saved.setUsername("staff2_updated");
        saved.setPassword(""); // no password update
        saved.setFullName("Jane Updated");
        staffDao.updateStaff(saved);

        Staff updated = staffDao.getStaffById(saved.getUserId());
        assertEquals("staff2_updated", updated.getUsername());
        assertEquals("Jane Updated", updated.getFullName());
        assertEquals("secret", updated.getPassword()); // password unchanged
    }

    @Test
    public void testUpdateStaffWithPassword() throws Exception {
        Staff staff = new Staff();
        staff.setUsername("staff3");
        staff.setPassword("oldpass");
        staff.setFullName("Mark Staff");
        staffDao.addStaff(staff);

        Staff saved = staffDao.getAllStaff().get(0);

        // Update with new password
        saved.setUsername("staff3_new");
        saved.setPassword("newpass");
        saved.setFullName("Mark Updated");
        staffDao.updateStaff(saved);

        Staff updated = staffDao.getStaffById(saved.getUserId());
        assertEquals("staff3_new", updated.getUsername());
        assertEquals("newpass", updated.getPassword());
        assertEquals("Mark Updated", updated.getFullName());
    }

    @Test
    public void testDeleteStaff() throws Exception {
        Staff staff = new Staff();
        staff.setUsername("staff4");
        staff.setPassword("delpass");
        staff.setFullName("Delete Me");
        staffDao.addStaff(staff);

        Staff saved = staffDao.getAllStaff().get(0);
        staffDao.deleteStaff(saved.getUserId());

        Staff deleted = staffDao.getStaffById(saved.getUserId());
        assertNull(deleted);

        List<Staff> staffList = staffDao.getAllStaff();
        assertTrue(staffList.isEmpty());
    }
}
