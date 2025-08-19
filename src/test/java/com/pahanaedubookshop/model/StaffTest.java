package com.pahanaedubookshop.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class StaffTest {

    @Test
    public void testStaffGettersAndSetters() {
        Staff staff = new Staff();

        // --- Set values ---
        staff.setUserId(1001);
        staff.setUsername("jdoe");
        staff.setPassword("securePass123");
        staff.setFullName("John Doe");
        staff.setRole("staff");

        // --- Assertions ---
        assertEquals(1001, staff.getUserId());
        assertEquals("jdoe", staff.getUsername());
        assertEquals("securePass123", staff.getPassword());
        assertEquals("John Doe", staff.getFullName());
        assertEquals("staff", staff.getRole());
    }
}
