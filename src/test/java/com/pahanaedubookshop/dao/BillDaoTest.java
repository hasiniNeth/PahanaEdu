package com.pahanaedubookshop.dao;

import com.pahanaedubookshop.model.Bill;
import com.pahanaedubookshop.model.BillItem;
import com.pahanaedubookshop.model.Book;
import com.pahanaedubookshop.util.DatabaseConnection;
import org.junit.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BillDaoTest {

    private BillDao billDao;
    private BookDao bookDao;

    @Before
    public void setUp() throws Exception {
        billDao = new BillDao();
        bookDao = new BookDao();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {
            // Clean up tables in the right order (child tables first)
            stmt.execute("DELETE FROM bill_items");
            stmt.execute("DELETE FROM bills");
            stmt.execute("DELETE FROM books");
        }
    }

    @Test
    public void testSaveBillInsertsBillItemsAndUpdatesStock() throws Exception {
        // Insert a book first
        Book book = new Book();
        book.setTitle("Test Driven Development");
        book.setAuthor("Kent Beck");
        book.setIsbn("TDD-001");
        book.setPrice(50.0);
        book.setStock(20);
        bookDao.insertBook(book);

        // Fetch book with generated ID
        Book savedBook = bookDao.getAllBooks().get(0);

        // Create bill
        Bill bill = new Bill();
        bill.setCustomerId(1);
        bill.setTotalAmount(100.0);

        // Add bill item
        BillItem item = new BillItem();
        item.setBookId(savedBook.getBookId());
        item.setQuantity(2);
        item.setPrice(savedBook.getPrice());
        item.setSubtotal(item.getPrice() * item.getQuantity());

        List<BillItem> items = new ArrayList<>();
        items.add(item);
        bill.setItems(items);

        // Save bill
        billDao.saveBill(bill);

        // ✅ Verify bill got an ID
        assertTrue(bill.getBillId() > 0);

        // ✅ Verify bill exists in DB
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bills WHERE bill_id = ?")) {
            stmt.setInt(1, bill.getBillId());
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("customer_id"));
            assertEquals(100.0, rs.getDouble("total_amount"), 0.001);
        }

        // ✅ Verify bill_items
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bill_items WHERE bill_id = ?")) {
            stmt.setInt(1, bill.getBillId());
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(savedBook.getBookId(), rs.getInt("book_id"));
            assertEquals(2, rs.getInt("quantity"));
            assertEquals(50.0, rs.getDouble("unit_price"), 0.001);
            assertEquals(100.0, rs.getDouble("total_price"), 0.001);
        }

        // ✅ Verify stock decreased
        Book updatedBook = bookDao.getBookById(savedBook.getBookId());
        assertEquals(18, updatedBook.getStock()); // 20 - 2 = 18
    }
}
