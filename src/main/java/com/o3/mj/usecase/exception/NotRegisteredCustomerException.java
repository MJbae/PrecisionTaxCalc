package com.o3.mj.usecase.exception;


public class NotRegisteredCustomerException extends IllegalStateException {
    public NotRegisteredCustomerException(String customerId) {
        super("Not Registered Customer, CustomerId: " + customerId);
    }
}