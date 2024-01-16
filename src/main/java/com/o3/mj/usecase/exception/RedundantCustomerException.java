package com.o3.mj.usecase.exception;

import com.o3.mj.domain.Customer;

public class RedundantCustomerException extends IllegalStateException {
    public RedundantCustomerException(Customer customer) {
        super("Redundant Customer: " + customer.toString());
    }
}