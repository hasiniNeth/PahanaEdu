package com.pahanaedubookshop.dao;

import com.pahanaedubookshop.model.Bill;
import com.pahanaedubookshop.model.BillItem;
import com.pahanaedubookshop.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class BillDao {

    // Save bill and items as one transaction
    public void saveBill(Bill bill) throws SQLException {
        String billSql = "INSERT INTO bills (customer_id, total_amount) VALUES (?, ?)";
        String itemSql = "INSERT INTO bill_items (bill_id, book_id, quantity, unit_price, total_price) VALUES (?, ?, ?, ?, ?)";
        String updateStockSql = "UPDATE books SET stock = stock - ? WHERE book_id = ?";

        Connection conn = null;
        PreparedStatement billStmt = null;
        PreparedStatement itemStmt = null;
        PreparedStatement stockStmt = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);

            // Insert bill
            billStmt = conn.prepareStatement(billSql, Statement.RETURN_GENERATED_KEYS);
            billStmt.setInt(1, bill.getCustomerId());
            billStmt.setDouble(2, bill.getTotalAmount());
            billStmt.executeUpdate();

            ResultSet rs = billStmt.getGeneratedKeys();
            if (rs.next()) {
                bill.setBillId(rs.getInt(1));
            }

            // Insert each item
            itemStmt = conn.prepareStatement(itemSql);
            stockStmt = conn.prepareStatement(updateStockSql);

            for (BillItem item : bill.getItems()) {
                // Insert item
                itemStmt.setInt(1, bill.getBillId());
                itemStmt.setInt(2, item.getBookId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setDouble(4, item.getPrice());
                itemStmt.setDouble(5, item.getSubtotal());
                itemStmt.executeUpdate();

                // Update stock
                stockStmt.setInt(1, item.getQuantity());
                stockStmt.setInt(2, item.getBookId());
                stockStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (billStmt != null) billStmt.close();
            if (itemStmt != null) itemStmt.close();
            if (stockStmt != null) stockStmt.close();
            if (conn != null) conn.setAutoCommit(true);
        }
    }
}
