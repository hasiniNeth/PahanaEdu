package com.pahanaedubookshop.dao;

import com.pahanaedubookshop.model.Customer;
import com.pahanaedubookshop.util.DatabaseConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerDAOTest {

    private CustomerDAO customerDAO;

    @Before
    public void setUp() throws Exception {
        customerDAO = new CustomerDAO();

        // Clean table before each test
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM customers");
        }
    }

    @Test
    public void testAddAndGetCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setAddress("123 Main St");
        customer.setTelephone("123456789");

        customerDAO.addCustomer(customer);

        // Fetch all customers
        List<Customer> customers = customerDAO.getAllCustomers();
        assertEquals(1, customers.size());

        Customer saved = customers.get(0);
        assertEquals("John Doe", saved.getFullName());
        assertEquals("123 Main St", saved.getAddress());
        assertEquals("123456789", saved.getTelephone());
        assertNotNull(saved.getAccountNumber());

        // Get by ID
        Customer byId = customerDAO.getCustomerById(saved.getCustomerId());
        assertNotNull(byId);
        assertEquals("John Doe", byId.getFullName());

        // Get by account number
        Customer byAcc = customerDAO.getCustomerByAccountNumber(saved.getAccountNumber());
        assertNotNull(byAcc);
        assertEquals(saved.getCustomerId(), byAcc.getCustomerId());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFullName("Jane Smith");
        customer.setAddress("456 Oak St");
        customer.setTelephone("987654321");

        customerDAO.addCustomer(customer);

        Customer saved = customerDAO.getAllCustomers().get(0);

        // Update fields
        saved.setFullName("Jane Smith Updated");
        saved.setAddress("789 Pine Ave");
        saved.setTelephone("111222333");
        customerDAO.updateCustomer(saved);

        Customer updated = customerDAO.getCustomerById(saved.getCustomerId());
        assertEquals("Jane Smith Updated", updated.getFullName());
        assertEquals("789 Pine Ave", updated.getAddress());
        assertEquals("111222333", updated.getTelephone());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFullName("Mark Brown");
        customer.setAddress("1010 Elm St");
        customer.setTelephone("444555666");

        customerDAO.addCustomer(customer);

        Customer saved = customerDAO.getAllCustomers().get(0);
        customerDAO.deleteCustomer(saved.getCustomerId());

        Customer deleted = customerDAO.getCustomerById(saved.getCustomerId());
        assertNull(deleted);

        List<Customer> customers = customerDAO.getAllCustomers();
        assertTrue(customers.isEmpty());
    }

    @Test
    public void testGenerateAccountNumber() throws Exception {
        Customer customer = new Customer();
        customer.setFullName("Acc Test");
        customer.setAddress("No Address");
        customer.setTelephone("000000000");
        customerDAO.addCustomer(customer);

        String accNo = customerDAO.generateAccountNumber();
        assertTrue(accNo.startsWith("CUST"));
    }
}
