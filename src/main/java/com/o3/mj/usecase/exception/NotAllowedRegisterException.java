package com.o3.mj.usecase.exception;

public class NotAllowedRegisterException extends IllegalStateException {
    public NotAllowedRegisterException(String customerId) {
        super("Not Allowed to register, userId: " + customerId);
    }
}