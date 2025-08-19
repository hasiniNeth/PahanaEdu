package com.pahanaedubookshop.service;

import com.pahanaedubookshop.dao.BillDao;
import com.pahanaedubookshop.model.Bill;
import com.pahanaedubookshop.model.BillItem;
import com.pahanaedubookshop.model.Book;
import com.pahanaedubookshop.model.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BillServiceTest {

    private BillService billService;
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        billService = new BillService();
        // You can get connection if needed to clean DB
        // connection = DBConnection.getInstance().getConnection();
    }

    @After
    public void tearDown() throws SQLException {
        // Optional: clean up inserted bills for testing
        // try (Statement stmt = connection.createStatement()) {
        //     stmt.execute("DELETE FROM bill_items");
        //     stmt.execute("DELETE FROM bills");
        // }
        // connection.close();
    }

    @Test
    public void testSaveBill() throws SQLException {
        // Create a test customer
        Customer customer = new Customer();
        customer.setCustomerId(1); // Assume exists in DB
        customer.setFullName("Test Customer");

        // Create a test book
        Book book = new Book();
        book.setBookId(1); // Assume exists in DB
        book.setTitle("Test Book");
        book.setPrice(10.0);

        // Create bill item
        BillItem item = new BillItem();
        item.setBookId(book.getBookId());
        item.setBookTitle(book.getTitle());
        item.setQuantity(2);
        item.setPrice(book.getPrice());
        item.setSubtotal(book.getPrice() * item.getQuantity());

        List<BillItem> items = new ArrayList<>();
        items.add(item);

        // Create bill
        Bill bill = new Bill();
        bill.setCustomerId(customer.getCustomerId());
        bill.setItems(items);
        bill.setTotalAmount(items.stream().mapToDouble(BillItem::getSubtotal).sum());
        bill.setDate(LocalDateTime.now());

        // Save bill
        billService.saveBill(bill);

        // Assert billId is set (assuming DAO sets it after saving)
        assertTrue(bill.getBillId() > 0);
    }
}
