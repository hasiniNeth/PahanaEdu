package com.pahanaedubookshop.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUserGettersAndSetters() {
        // --- Using no-arg constructor ---
        User user = new User();

        user.setUserId(101);
        user.setUsername("alice");
        user.setPassword("password123");
        user.setRole("admin");
        user.setFullName("Alice Johnson");

        assertEquals(101, user.getUserId());
        assertEquals("alice", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("admin", user.getRole());
        assertEquals("Alice Johnson", user.getFullName());
    }

    @Test
    public void testUserParameterizedConstructor() {
        User user = new User("bob", "pass456", "staff", "Bob Smith");

        assertEquals("bob", user.getUsername());
        assertEquals("pass456", user.getPassword());
        assertEquals("staff", user.getRole());
        assertEquals("Bob Smith", user.getFullName());

        // userId should be default 0
        assertEquals(0, user.getUserId());
    }
}
