package com.pahanaedubookshop.service;

import com.pahanaedubookshop.model.Staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffService {
    private static Map<String, Staff> staffDatabase = new HashMap<>();
    private static int nextId = 1;  // For auto-incrementing IDs

    static {
        // Initialize with an admin user using setters
        Staff admin = new Staff();
        admin.setId(nextId++);
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setFullName("System Admin");
        admin.setEmail("admin@example.com");
        admin.setRole("admin");
        staffDatabase.put("admin", admin);
    }

    public List<Staff> getAllStaff() {
        return new ArrayList<>(staffDatabase.values());
    }

    public void addStaff(Staff staff) throws Exception {
        if (staffDatabase.containsKey(staff.getUsername())) {
            throw new Exception("Username already exists");
        }
        // Assign ID before saving
        staff.setId(nextId++);
        staffDatabase.put(staff.getUsername(), staff);
    }

    public void updateStaff(String originalUsername, Staff updatedStaff) throws Exception {
        Staff existingStaff = staffDatabase.get(originalUsername);
        if (existingStaff == null) {
            throw new Exception("Staff member not found");
        }

        // If username changed, check if new username exists
        if (!originalUsername.equals(updatedStaff.getUsername()) &&
                staffDatabase.containsKey(updatedStaff.getUsername())) {
            throw new Exception("New username already exists");
        }

        // Preserve the ID
        updatedStaff.setId(existingStaff.getId());

        // If password not provided, keep the old one
        if (updatedStaff.getPassword() == null || updatedStaff.getPassword().isEmpty()) {
            updatedStaff.setPassword(existingStaff.getPassword());
        }

        // Remove old entry if username changed
        if (!originalUsername.equals(updatedStaff.getUsername())) {
            staffDatabase.remove(originalUsername);
        }

        staffDatabase.put(updatedStaff.getUsername(), updatedStaff);
    }

    public void deleteStaff(String username) throws Exception {
        if (!staffDatabase.containsKey(username)) {
            throw new Exception("Staff member not found");
        }
        staffDatabase.remove(username);
    }
}