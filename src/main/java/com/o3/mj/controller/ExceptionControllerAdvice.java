package com.o3.mj.controller;

import com.o3.mj.controller.dto.ErrorResponse;
import com.o3.mj.usecase.exception.NotAllowedRegisterException;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import com.o3.mj.usecase.exception.RedundantCustomerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackageClasses = {ExceptionControllerAdvice.class})
public class ExceptionControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NotAllowedRegisterException.class)
    public ErrorResponse handleNotAllowedRegisterException(NotAllowedRegisterException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NotRegisteredCustomerException.class)
    public ErrorResponse handleNotRegisteredCustomerException(NotRegisteredCustomerException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RedundantCustomerException.class)
    public ErrorResponse handleRedundantCustomerException(RedundantCustomerException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ErrorResponse handleException(Exception e) {
        logger.error("server error", e);
        return new ErrorResponse(e.getMessage());
    }
}
