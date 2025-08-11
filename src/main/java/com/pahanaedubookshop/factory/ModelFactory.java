package com.pahanaedubookshop.factory;

import com.pahanaedubookshop.model.Book;
import com.pahanaedubookshop.model.Customer;
import com.pahanaedubookshop.model.Staff;
import com.pahanaedubookshop.model.Bill;
import com.pahanaedubookshop.model.BillItem;

public interface ModelFactory {
    Book createBook();
    Customer createCustomer();
    Staff createStaff();
    Bill createBill();
    BillItem createBillItem();
}
