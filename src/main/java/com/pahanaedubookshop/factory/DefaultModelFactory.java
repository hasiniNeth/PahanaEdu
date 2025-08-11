package com.pahanaedubookshop.factory;

import com.pahanaedubookshop.model.Book;
import com.pahanaedubookshop.model.Customer;
import com.pahanaedubookshop.model.Staff;
import com.pahanaedubookshop.model.Bill;
import com.pahanaedubookshop.model.BillItem;

/**
 * Very small singleton factory returning plain model instances.
 * Replace DefaultModelFactory.INSTANCE.createX() where needed.
 */
public final class DefaultModelFactory implements ModelFactory {

    // Singleton instance (thread-safe for this use case)
    public static final DefaultModelFactory INSTANCE = new DefaultModelFactory();

    // private constructor to prevent external instantiation
    private DefaultModelFactory() {}

    @Override
    public Book createBook() {
        return new Book();
    }

    @Override
    public Customer createCustomer() {
        return new Customer();
    }

    @Override
    public Staff createStaff() {
        return new Staff();
    }

    @Override
    public Bill createBill() {
        return new Bill();
    }

    @Override
    public BillItem createBillItem() {
        return new BillItem();
    }
}
