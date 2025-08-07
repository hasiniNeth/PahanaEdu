package com.pahanaedubookshop.service;

import com.pahanaedubookshop.dao.BillDao;
import com.pahanaedubookshop.model.Bill;

import java.sql.SQLException;

public class BillService {
    private final BillDao billDao = new BillDao();

    public void saveBill(Bill bill) throws SQLException {
        billDao.saveBill(bill);
    }
}
