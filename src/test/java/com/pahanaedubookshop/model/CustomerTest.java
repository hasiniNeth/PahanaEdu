package com.pahanaedubookshop.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerTest {

    @Test
    public void testCustomerGettersAndSetters() {
        Customer customer = new Customer();

        // --- Set values ---
        customer.setCustomerId(101);
        customer.setAccountNumber("CUST001");
        customer.setFullName("John Doe");
        customer.setAddress("123 Main St, City");
        customer.setTelephone("0771234567");

        // --- Assertions ---
        assertEquals(101, customer.getCustomerId());
        assertEquals("CUST001", customer.getAccountNumber());
        assertEquals("John Doe", customer.getFullName());
        assertEquals("123 Main St, City", customer.getAddress());
        assertEquals("0771234567", customer.getTelephone());
    }
}
