package com.pahanaedubookshop.model;

import org.junit.Test;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BillTest {

    @Test
    public void testBillAndBillItem() {
        // --- Create bill items ---
        BillItem item1 = new BillItem();
        item1.setBookId(101);
        item1.setBookTitle("Java Programming");
        item1.setQuantity(2);
        item1.setPrice(500.0);
        item1.setSubtotal(item1.getQuantity() * item1.getPrice());

        BillItem item2 = new BillItem();
        item2.setBookId(102);
        item2.setBookTitle("Data Structures");
        item2.setQuantity(1);
        item2.setPrice(300.0);
        item2.setSubtotal(item2.getQuantity() * item2.getPrice());

        // --- Create bill ---
        Bill bill = new Bill();
        bill.setBillId(1);
        bill.setCustomerId(1001);
        bill.setItems(Arrays.asList(item1, item2));
        double totalAmount = bill.getItems().stream().mapToDouble(BillItem::getSubtotal).sum();
        bill.setTotalAmount(totalAmount);
        bill.setDate(LocalDateTime.now());

        // --- Assertions ---
        assertEquals(1, bill.getBillId());
        assertEquals(1001, bill.getCustomerId());
        assertEquals(2, bill.getItems().size());

        BillItem firstItem = bill.getItems().get(0);
        assertEquals(101, firstItem.getBookId());
        assertEquals("Java Programming", firstItem.getBookTitle());
        assertEquals(2, firstItem.getQuantity());
        assertEquals(500.0, firstItem.getPrice(), 0.001);
        assertEquals(1000.0, firstItem.getSubtotal(), 0.001);

        BillItem secondItem = bill.getItems().get(1);
        assertEquals(102, secondItem.getBookId());
        assertEquals("Data Structures", secondItem.getBookTitle());
        assertEquals(1, secondItem.getQuantity());
        assertEquals(300.0, secondItem.getPrice(), 0.001);
        assertEquals(300.0, secondItem.getSubtotal(), 0.001);

        assertEquals(1300.0, bill.getTotalAmount(), 0.001);
        assertNotNull(bill.getDate());
    }
}
