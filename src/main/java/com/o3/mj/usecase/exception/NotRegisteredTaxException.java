package com.o3.mj.usecase.exception;


public class NotRegisteredTaxException extends IllegalStateException {
    public NotRegisteredTaxException(String customerId) {
        super("Not Scraped Tax Data, CustomerId: " + customerId);
    }
}